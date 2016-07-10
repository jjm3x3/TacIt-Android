package com.jmeixner.jjlabs.tacit;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
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
        return null;
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
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
