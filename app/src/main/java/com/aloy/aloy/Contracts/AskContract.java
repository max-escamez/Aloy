package com.aloy.aloy.Contracts;

import android.content.Context;


import java.util.ArrayList;
import java.util.HashMap;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by tldonne on 29/10/2017.
 */

public interface AskContract {

    interface View  {
        void removeTrack(Track track);

        HashMap getTracks();

        void hideAskQuestion();
        void showSearch(String type);
        void hideKeyboardFrom(Context context, android.view.View view);
        void update();
        void addTrack(Track track);

    }

    interface Presenter {
        void createQuestion(String body,HashMap<String,Track> tracksSelected);
        void createAnswer(String body, String questionID);

    }
}
