package com.jmeixner.jjlabs.tacit;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);

//        Button updateButton = (Button) findViewById(R.id.updateButton);
//        assert updateButton != null;
//        updateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("IN ON CLICK", "can't belive this worked!!");
//            }
//        });

        EditText ed = (EditText) findViewById(R.id.editText);
        Cursor cur = getContentResolver().query(TacItContract.Note.CONTENT_URI,null, TacItContract.Note.ID + " = ?", new String[]{noteId + ""}, null);
        String noteContent = "";
        if (cur != null) {
            if (cur.getCount() != 1) Log.e(MY_LOG_TAG, "There is more than one note with the id: " + noteId);
            cur.moveToNext();
            noteContent = cur.getString(cur.getColumnIndex(TacItContract.Note.CONTENT));
            cur.close();
        } else {
            Log.e(MY_LOG_TAG, "could not find note content on note: " + noteId);
        }
        ed.setText(noteContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_note , menu);
        return true;
    }
}
