package com.aloy.aloy.Contracts;

import android.content.Context;
import android.widget.ImageButton;

import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Presenters.QuestionDetailsPresenter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by tldonne on 06/11/2017.
 */

public interface QuestionDetailsContract {

    interface View  {
        void openProfile(Question question);

        void setupAnswers(String questionId);

        void setupQuestion(Question question, String transitionName, Context context);

        QuestionDetailsPresenter getPresenter();

        void setupCoverFlow(Question question);

        void showRequest(String questionId);
        void showAnswerQuestion(String questionId);
    }

    interface Presenter {

        Query getRef(String questionId);

        void upvoteAnswer(DatabaseReference questionId,String answerId);

        void getUpvotes(String questionId, String answerId, ImageButton holder);
    }
}
