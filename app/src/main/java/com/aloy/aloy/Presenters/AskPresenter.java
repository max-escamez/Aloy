package com.aloy.aloy.Presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.aloy.aloy.Contracts.AskContract;
import com.aloy.aloy.Fragments.Ask;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.SharedPreferenceHelper;
import com.aloy.aloy.Util.SpotifyHandler;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.client.Response;

/**
 * Created by tldonne on 29/10/2017.
 */

public class AskPresenter implements AskContract.Presenter {

    private AskContract.View askView;
    private DataHandler dataHandler;
    private SpotifyHandler spotifyHandler;
    private SharedPreferenceHelper sharedPreferenceHelper;

    public AskPresenter(AskContract.View askView, DataHandler dataHandler, SpotifyHandler spotifyHandler) {
        this.dataHandler = dataHandler;
        this.spotifyHandler = spotifyHandler;
        this.askView = askView;
    }

    @Override
    public void createQuestion(String body) {
        sharedPreferenceHelper = new SharedPreferenceHelper(context);
        Question newQuestion = new Question(sharedPreferenceHelper.getCurrentUserId(),body,sharedPreferenceHelper.getProfilePicture());
        //newQuestion.setBody(body);
        dataHandler.saveQuestion(newQuestion);
    }


}
