package com.jmeixner.jjlabs.tacit;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jmeixner on 7/16/2016.
 */
public class RestRequestService {

    private static final String MY_LOG_TAG = "RestRequestService";
    private static ServerConnection serverConnection;

    public static ServerConnection getServerConnetion() {
        if (serverConnection == null)
            serverConnection = new ServerConnection();
        return serverConnection;
    }

    public static Call<Items> getNotes(final Context context, final DataManager.UiCallback uiCallback){
        ServerConnection server = getServerConnetion();
        Call<Items> callback = server.getNotes();
        callback.enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                Log.d(MY_LOG_TAG, response.toString());
                if (response.body() != null){
                    DataManager.syncNotes(context, response.body().items, uiCallback);
                } else {
                    Log.e(MY_LOG_TAG, "response is null: in getNotes: status: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {
                Log.e(MY_LOG_TAG, "Failure in getNotes: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return callback;
    }

    public static Call<Item> syncNote(final Context context, Item payload){
        ServerConnection server = getServerConnetion();
        Call<Item> callback = server.putNote(payload);
        callback.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Log.d(MY_LOG_TAG, response.toString());
                if(response.body() != null){
                    List<Item> updateItems = new ArrayList<>();
                    updateItems.add(response.body());
                    DataManager.syncNotes(context, updateItems,
                            new DataManager.UiCallback() { @Override public void whenGood() { /* DO NOTHING HERE */} });
                } else {
                    Log.e(MY_LOG_TAG, "response is null: in updateNotes: status: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.e(MY_LOG_TAG, "Failure in updateNotes: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return callback;
    }
}
