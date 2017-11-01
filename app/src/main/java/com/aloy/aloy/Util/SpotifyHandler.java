package com.aloy.aloy.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.aloy.aloy.Models.MainUser;
import com.aloy.aloy.Models.Question;
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
    private MainUser mainUser;
    SharedPreferenceHelper sharedPreferenceHelper;


    public SpotifyHandler(String token, Context context){
        sharedPreferenceHelper = new SharedPreferenceHelper(context);
        SpotifyApi api = new SpotifyApi();
        api.setAccessToken(token);
        this.service = api.getService();
        dataHandler = new DataHandler();
    }

    public SpotifyHandler(SpotifyService service) {
        this.service = service;
        dataHandler = new DataHandler();
    }

    public void createMainUser() {
        service.getMe(new SpotifyCallback<UserPrivate>() {
            @Override
            public void success(UserPrivate u, Response response) {
                sharedPreferenceHelper.saveCurrentUserId(u.id);
                sharedPreferenceHelper.saveProfilePicture(u.images.get(0).url);
                mainUser = new MainUser(u.id, u.images.get(0).url);
                dataHandler.saveUser(mainUser);
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


    /*public void createQuestion(final String body) {


        service.getMe(new SpotifyCallback<UserPrivate>() {
                          @Override
                          public void success(UserPrivate u, Response response) {
                              Question question = new Question(u.id,body);
                              dataHandler.saveQuestion(question);
                          }
                          @Override
                          public void failure(SpotifyError error) {
                              Log.i("Error", error.toString());
                          }
                      }
        );

    }*/

}
