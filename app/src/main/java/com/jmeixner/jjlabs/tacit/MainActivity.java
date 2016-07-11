package com.jmeixner.jjlabs.tacit;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener{

    private static final String MY_LOG_TAG = "MainActivity";
    private CursorAdapter mAdapter;
    private ServerConnection serverConnection;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefresh.setOnRefreshListener(this);

        ListView noteList = (ListView)findViewById(R.id.noteList);

        MatrixCursor fakeCursor = new MatrixCursor(new String[]{TacItContract.Note.ID,TacItContract.Note.CONTENT});
        fakeCursor.addRow(new Object[]{1,"some fake message in my fake cursor"});

        mAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1, null,new String[]{TacItContract.Note.CONTENT},new int[]{android.R.id.text1});


        noteList.setAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);

        serverConnection = new ServerConnection();
        serverConnection.getNotes(this, new DataManager.UiCallback() {
            @Override
            public void whenGood() {
                //DO NOTHING NOT IMPORTANT
            }
        });
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, TacItContract.Note.CONTENT_URI, new String[]{TacItContract.Note.ID, TacItContract.Note.CONTENT},null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.setNotificationUri(getContentResolver(),TacItContract.Note.CONTENT_URI);
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onRefresh(){
        Log.d(MY_LOG_TAG, "refresh me!");
        serverConnection.getNotes(this, new DataManager.UiCallback() {
            @Override
            public void whenGood() {
                swipeRefresh.setRefreshing(false);
            }
        });
    }
}
