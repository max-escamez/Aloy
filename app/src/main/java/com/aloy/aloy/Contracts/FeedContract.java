package com.aloy.aloy.Contracts;

import com.aloy.aloy.Util.BaseView;
import com.google.firebase.database.Query;

/**
 * Created by tldonne on 28/10/2017.
 */

public interface FeedContract {

    interface View extends BaseView<Presenter> {
        void showAddQuestion();
        void setupRecyclerView(android.view.View feedView);
        void showAnswerQuestion(String questionId);
    }

    interface Presenter {
        void addQuestion();
        Query getQuery();
    }
}
