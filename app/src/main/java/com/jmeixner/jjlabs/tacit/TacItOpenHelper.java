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
    //TODO: change the name of this table!!
    public static final String NOTES_TABLE = "ITEMS";

    TacItOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + NOTES_TABLE + "(" +
                    TacItContract.Note.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TacItContract.Note.REMOTE_ID + " INTEGER UNIQUE, " +
                    TacItContract.Note.CONTENT + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
