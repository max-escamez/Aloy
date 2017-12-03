package com.aloy.aloy.Contracts;

import com.aloy.aloy.Models.Question;
import com.google.firebase.database.Query;

/**
 * Created by tldonne on 28/10/2017.
 */

public interface FeedContract {

    interface View {
        void setPresenter(Presenter presenter);

        void showAddQuestion();
        void setupRecyclerView(android.view.View feedView);
        void showAnswerQuestion(String questionId);
        void onQuestionCLick(Question question, android.view.View itemView);
    }

    interface Presenter {
        void addQuestion();
        Query getQuery();
    }
}
