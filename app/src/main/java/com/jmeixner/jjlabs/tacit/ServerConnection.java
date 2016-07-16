package com.jmeixner.jjlabs.tacit;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by jmeixner on 7/8/2016.
 */
public class ServerConnection {

    private static final String MY_LOG_TAG = "ServerConnection";
    private final Retrofit retrofit;
    private final NoteService noteService;

    public interface NoteService {
        @GET("item")
        Call<Items> get();
    }

    ServerConnection() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.108:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        noteService = retrofit.create(NoteService.class);
    }

    public Call<Items> getNotes(final Context context, final DataManager.UiCallback uiCallback){
        Call<Items> callback = noteService.get();
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


}
