package com.jmeixner.jjlabs.tacit;

import android.net.Uri;

/**
 * Created by jmeixner on 7/10/2016.
 */
public class TacItContract {
    public static String AUTHORITY = "com.jmeixner.jjlabs.tacit.TacItContentProvider";
    public static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static class Note {
        public static Uri CONTENT_URI = Uri.withAppendedPath(TacItContract.CONTENT_URI,"notes");
        public static String ID = "_id";
        public static String REMOTE_ID = "REMOTE_ID";
        public static String CONTENT = "CONTENT";
    }
}
