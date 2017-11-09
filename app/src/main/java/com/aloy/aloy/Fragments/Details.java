package com.aloy.aloy.Fragments;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.Adapters.AnswersAdapter;
import com.aloy.aloy.Contracts.QuestionDetailsContract;
import com.aloy.aloy.MainActivity;
import com.aloy.aloy.Models.Answer;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Presenters.QuestionDetailsPresenter;
import com.aloy.aloy.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Details extends AppCompatActivity implements QuestionDetailsContract.View {
    private QuestionDetailsPresenter questionDetailsPresenter;
    private ArrayList<Answer> adapterAnswer;
    private ArrayList<String> adapterKeys;
    private CircularImageView profilePic;
    private TextView username;
    private TextView body ;
    private ImageView cover1 ;
    private ImageView cover2 ;
    private View questionView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_question_details);
        supportPostponeEnterTransition();
        questionDetailsPresenter = new QuestionDetailsPresenter(this, MainActivity.getDataHandler());
        Bundle extras = getIntent().getExtras();
        Question question = extras.getParcelable(Feed.EXTRA_QUESTION);
        String transitionName = extras.getString(Feed.EXTRA_QUESTION_TRANSITION_NAME);

        setupQuestion(question,transitionName);
        setupRecyclerView(question.getId());

        supportStartPostponedEnterTransition();
    }

    @Override
    public void setupRecyclerView(String questionId) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.answerRecyclerView);
        AnswersAdapter answerRecyclerAdapter = new AnswersAdapter(questionDetailsPresenter.getRef(questionId), adapterAnswer, adapterKeys, this, questionDetailsPresenter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(answerRecyclerAdapter);
    }

    @Override
    public void setupQuestion(Question question, String transitionName){
        profilePic = (CircularImageView) findViewById(R.id.questionDetailProfilePic);
        username = (TextView) findViewById(R.id.questionDetailUsername);
        body = (TextView) findViewById(R.id.questionDetailBody);
        cover1 = (ImageView) findViewById(R.id.questionDetailCover1);
        cover2 = (ImageView) findViewById(R.id.questionDetailCover2);

        body.setText(question.getBody());
        username.setText(question.getUsername());
        Picasso.with(this).load(question.getPic()).into(profilePic);
        Picasso.with(this).load(question.getCover1()).into(cover1);
        Picasso.with(this).load(question.getCover2()).into(cover2);
        questionView = findViewById(R.id.questionDetail);
        questionView.setTransitionName(transitionName);
    }

    @Override
    public QuestionDetailsPresenter getPresenter() {
        return this.questionDetailsPresenter;
    }

    @Override
    public void onBackPressed() {
        // finishAfterTransition()
        finish();
    }




}



