package com.aloy.aloy.Contracts;

import com.aloy.aloy.BaseView;

/**
 * Created by tldonne on 29/10/2017.
 */

public interface AskContract {

    interface View  {
        void hideAskQuestion();

    }

    interface Presenter {
        void addQuestion(String question);
    }
}
