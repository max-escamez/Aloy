package com.aloy.aloy.Presenters;

import com.aloy.aloy.Adapters.AnswersAdapter;
import com.aloy.aloy.Contracts.QuestionDetailsContract;
import com.aloy.aloy.Util.DataHandler;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by tldonne on 06/11/2017.
 */

public class QuestionDetailsPresenter implements QuestionDetailsContract.Presenter {

    private QuestionDetailsContract.View questionDetailsView;
    private DataHandler dataHandler;

    public QuestionDetailsPresenter(QuestionDetailsContract.View questionDetailsView, DataHandler dataHandler) {
        this.questionDetailsView=questionDetailsView;
        this.dataHandler=dataHandler;
    }

    @Override
    public Query getRef(String questionId) {
        return dataHandler.getRefAnswers(questionId);
    }

    @Override
    public void upvoteAnswer(DatabaseReference questionId, String answerId){
        //System.out.println(questionId + " --- " + answerId);
        dataHandler.upvote(questionId,answerId);
    }

    @Override
    public void getUpvotes(String questionId, String answerId, AnswersAdapter.ViewHolder holder) {
        dataHandler.getUpvote(questionId,answerId,holder);
    }


}
