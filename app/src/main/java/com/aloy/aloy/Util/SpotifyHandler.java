package com.aloy.aloy.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.aloy.aloy.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.client.Response;

/**
 * Created by tldonne on 30/10/2017.
 */

public class SpotifyHandler {

    private String username;
    private SpotifyService service;
    private DataHandler dataHandler;
    private SharedPreferenceHelper msharedPreferenceHelper;

    public SpotifyHandler(String token, Context context){
        SpotifyApi api = new SpotifyApi();
        api.setAccessToken(token);
        this.service = api.getService();
        msharedPreferenceHelper=new SharedPreferenceHelper(context);
    }

    public SpotifyHandler(SpotifyService service) {
        this.service = service;
    }

    public void getMyUsername() {
        service.getMe(new SpotifyCallback<UserPrivate>() {
            @Override
            public void success(UserPrivate u, Response response) {
                msharedPreferenceHelper.saveCurrentUserId(u.id);
                dataHandler = new DataHandler();
                dataHandler.saveUser(u.id);
            }
            @Override
            public void failure(SpotifyError error) {
                Log.i("Error", error.toString());
            }


        });

    }

    public void setUsername(String arg) {
        username = arg;
    }

    public String getUsername() {
        return username;
    }


}
