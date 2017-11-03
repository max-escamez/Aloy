package com.aloy.aloy.Contracts;

import android.content.Context;
import android.view.View;


import com.aloy.aloy.Models.Question;

import java.util.ArrayList;

/**
 * Created by tldonne on 29/10/2017.
 */

public interface AskContract {

    interface View  {
        void hideAskQuestion();
        void showSearch(String type);
        void hideKeyboardFrom(Context context, android.view.View view);
        void setSelectedTracks( ArrayList<Integer> selectedTracks, String searchQuery);

    }

    interface Presenter {
        void createQuestion(String body,ArrayList<Integer> tracksSelected,String tracksQuery);

        void createAnswer(String body, String questionID);

    }
}
