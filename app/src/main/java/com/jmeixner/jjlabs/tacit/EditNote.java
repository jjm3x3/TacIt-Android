package com.jmeixner.jjlabs.tacit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class EditNote extends AppCompatActivity {
    private static final String MY_LOG_TAG = "EditNote";
    public static String NOTE_ID = "NoteId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Intent intent = getIntent();
        Long noteId = intent.getLongExtra(NOTE_ID, 0);
        Log.d(MY_LOG_TAG, "lets edit note: " + noteId);
    }
}
