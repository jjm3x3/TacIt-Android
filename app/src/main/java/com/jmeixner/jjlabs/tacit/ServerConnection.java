package com.jmeixner.jjlabs.tacit;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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
        @PUT("item/{noteId}")
        Call<Item> put(@Path("noteId") int noteId, @Body Item payload);
    }

    ServerConnection() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.108:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        noteService = retrofit.create(NoteService.class);
    }

    public Call<Items> getNotes(){
        return noteService.get();
    }


    public Call<Item> putNote(Item note){
        return noteService.put(note.id, note);
    }

}
