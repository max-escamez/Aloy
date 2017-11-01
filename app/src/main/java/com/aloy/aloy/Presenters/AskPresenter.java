package com.aloy.aloy.Presenters;

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

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("questions");
    private AskContract.View askView;
    private String question;
    private DataHandler dataHandler;
    private String username;
    private SpotifyHandler spotifyHandler;
    SharedPreferenceHelper sharedPreferenceHelper;

    public AskPresenter(AskContract.View askView, DataHandler dataHandler, SpotifyHandler spotifyHandler) {
        this.dataHandler = dataHandler;
        this.spotifyHandler = spotifyHandler;
        this.askView = askView;
    }



    @Override
    public void createQuestion(String body) {
        Question newQuestion = new Question(sharedPreferenceHelper.getCurrentUserId(),body);
        //newQuestion.setBody(body);
        dataHandler.saveQuestion(newQuestion);


    }
}
