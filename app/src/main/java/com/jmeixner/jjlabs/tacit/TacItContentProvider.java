package com.jmeixner.jjlabs.tacit;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by jmeixner on 7/10/2016.
 */
public class TacItContentProvider extends ContentProvider{
    private static final String MY_LOG_TAG = "TacITContentProvider";
    private TacItOpenHelper mHelper;

    private static final int NOTES = 1;

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(TacItContract.AUTHORITY,"notes",NOTES);
    }

    @Override
    public boolean onCreate() {
        Log.d(MY_LOG_TAG, "DB created");
        mHelper = new TacItOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String groupBy = null;
        String having = null;
        Cursor cur = null;
        switch (URI_MATCHER.match(uri)){
            case NOTES:
                cur =  db.query(TacItOpenHelper.NOTES_TABLE,projection,selection,selectionArgs, groupBy, having, sortOrder);
                break;
            default:
                Log.e(MY_LOG_TAG, "Could not find uri match to query for: " + uri.toString());
        }

        return cur;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(MY_LOG_TAG, "Inserting into: " + uri.toString());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        long affectedRowId = -1;
        switch (URI_MATCHER.match(uri)){
            case NOTES:
                affectedRowId = db.insert(TacItOpenHelper.NOTES_TABLE, "_id", values);
                break;
            default:
                Log.e(MY_LOG_TAG,"Could not find uri match to insert for: " + uri.toString());
        }
        return notifyUriOfChanges(uri, affectedRowId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(MY_LOG_TAG,"Updating record in: " + uri);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int rowsAffected = 0;
        switch (URI_MATCHER.match(uri)){
            case NOTES:
                rowsAffected = db.update(TacItOpenHelper.NOTES_TABLE, values, selection, selectionArgs);
                break;
            default:
                Log.e(MY_LOG_TAG, "Could not find uri match to update for: " + uri.toString());
        }
        return rowsAffected;
    }

    private Uri notifyUriOfChanges(Uri uri, long id) {
        //TODO:: possible need for switch to handle special cases

        Uri affectedRowUri = Uri.withAppendedPath(uri, id + "");
        getContext().getContentResolver().notifyChange(affectedRowUri,null);
        return affectedRowUri;
    }
}
