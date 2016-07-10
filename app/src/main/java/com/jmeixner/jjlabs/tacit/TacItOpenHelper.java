package com.jmeixner.jjlabs.tacit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jmeixner on 7/10/2016.
 */
public class TacItOpenHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "TACIT_DB";
    private static final int DB_VERSION = 1;
    private static final String ITEMS_TABLE = "ITEMS";

    TacItOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ITEMS_TABLE + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "REMOTE_ID INTEGER UNIQUE" +
                    "THING TEXT;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
