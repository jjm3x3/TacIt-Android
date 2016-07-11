package com.jmeixner.jjlabs.tacit;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmeixner on 7/10/2016.
 */
public class DataManager {

    private static final String MY_LOG_TAG = "DataManager";


    public interface UiCallback {
        void whenGood();
    }
    public static void syncNotes(Context context, List<Items.Item> notes, UiCallback callback) {

        List<Integer> existingIds = getRemoteIds(context, TacItContract.Note.CONTENT_URI);

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        for (Items.Item i : notes) {
            String thing = i.thing.replaceAll("\r", "");
//                        Log.d(MY_LOG_TAG, "Thing " + i.id + ": " + thing );
            if (existingIds.contains(i.id)) {
                ops.add(createNoteUpdate(i, thing));
                existingIds.remove((Integer)i.id);
            }
            else
                ops.add(createNoteInsert(i, thing));
        }

        for(Integer i: existingIds){
            ops.add(createNoteDelete(i));
        }

        try {
            context.getContentResolver().applyBatch(TacItContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            Log.e(MY_LOG_TAG, e.toString());
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            Log.e(MY_LOG_TAG, e.toString());
            e.printStackTrace();
        }


        Cursor cur = context.getContentResolver().query(TacItContract.Note.CONTENT_URI,null, null,null, null);
        cur.moveToFirst();
        Log.d(MY_LOG_TAG, "Read from the db");
        while (cur.moveToNext()){
            Log.d(MY_LOG_TAG, cur.getInt(cur.getColumnIndex(TacItContract.Note.ID)) + ":" +
                    cur.getInt(cur.getColumnIndex(TacItContract.Note.REMOTE_ID)) + " Content: " +
                    cur.getString(cur.getColumnIndex(TacItContract.Note.CONTENT)));
        }

        callback.whenGood();
    }

    private static ContentProviderOperation createNoteDelete(Integer i) {
        return ContentProviderOperation.newDelete(TacItContract.Note.CONTENT_URI)
                .withSelection(TacItContract.Note.REMOTE_ID + " = ?", new String[]{i + ""})
                .build();
    }

    private static ContentProviderOperation createNoteUpdate(Items.Item i, String thing) {
        return ContentProviderOperation.newUpdate(TacItContract.Note.CONTENT_URI)
                .withValue(TacItContract.Note.REMOTE_ID, i.id)
                .withValue(TacItContract.Note.CONTENT, thing)
                .withSelection(TacItContract.Note.REMOTE_ID + " = ?", new String[]{i.id + ""})
                .build();
    }

    private static List<Integer> getRemoteIds(Context context, Uri contentUri) {
        Cursor cur = context.getContentResolver().query(contentUri,new String[]{TacItContract.Note.REMOTE_ID},null, null, null);
        List<Integer> result = new ArrayList<>();
        if (cur != null) {
            while(cur.moveToNext()){
                result.add(cur.getInt(cur.getColumnIndex(TacItContract.Note.REMOTE_ID)));
            }
        }
        return result;
    }

    private static ContentProviderOperation createNoteInsert(Items.Item i, String thing) {
        return ContentProviderOperation.newInsert(TacItContract.Note.CONTENT_URI)
                .withValue(TacItContract.Note.REMOTE_ID, i.id)
                .withValue(TacItContract.Note.CONTENT, thing)
                .build();
    }
}
