package com.aloy.aloy.Contracts;

import android.content.Context;
import android.view.View;


import com.aloy.aloy.Models.Question;

/**
 * Created by tldonne on 29/10/2017.
 */

public interface AskContract {

    interface View  {
        void hideAskQuestion();
        void showFind(String type);
        void hideKeyboardFrom(Context context, android.view.View view);

    }

    interface Presenter {
        void createQuestion(String body);

    }
}
