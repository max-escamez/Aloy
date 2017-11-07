package com.aloy.aloy.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.Adapters.AnswersAdapter;
import com.aloy.aloy.Adapters.MyRecyclerAdapter;
import com.aloy.aloy.Contracts.QuestionDetailsContract;
import com.aloy.aloy.MainActivity;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Presenters.QuestionDetailsPresenter;
import com.aloy.aloy.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QuestionDetails extends DialogFragment implements QuestionDetailsContract.View {


    private static final String EXTRA_TRANSITION_NAME = "transitionName";
    private static final String EXTRA_QUESTION = "questionId";
    private AnswersAdapter answerRecyclerAdapter;
    private ArrayList<Question> adapterAnswer;
    private ArrayList<String> adapterKeys;
    private QuestionDetailsPresenter questionDetailsPresenter;
    private LinearLayoutManager layoutManager;
    private TextView body;
    private TextView username;
    private CircularImageView profilePic;
    private ImageView cover1;
    private ImageView cover2;

    public QuestionDetails() {
        // Required empty public constructor
    }

    public static QuestionDetails newInstance(Question question, String transitionName) {
        QuestionDetails questionDetails = new QuestionDetails();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_QUESTION, question);
        bundle.putString(EXTRA_TRANSITION_NAME, transitionName);
        questionDetails.setArguments(bundle);
        return questionDetails;
    }


    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View questionDetailsView = inflater.inflate(R.layout.fragment_question_details, container, false);
        questionDetailsPresenter = new QuestionDetailsPresenter(this, MainActivity.getDataHandler());

        Question question = getArguments().getParcelable(EXTRA_QUESTION);
        String transitionName = getArguments().getString(EXTRA_TRANSITION_NAME);
        body = (TextView) questionDetailsView.findViewById(R.id.questionDetailBody);
        body.setText(question.getBody());
        username = (TextView) questionDetailsView.findViewById(R.id.questionDetailUsername);
        username.setText(question.getUsername());
        profilePic = (CircularImageView) questionDetailsView.findViewById(R.id.questionDetailProfilePic);
        Picasso.with(getContext()).load(question.getPic()).into(profilePic);
        cover1 = (ImageView) questionDetailsView.findViewById(R.id.questionDetailCover1);
        Picasso.with(getContext()).load(question.getCover1()).into(cover1);
        cover2 = (ImageView) questionDetailsView.findViewById(R.id.questionDetailCover2);
        Picasso.with(getContext()).load(question.getCover2()).into(cover2);



        setupRecyclerView(questionDetailsView,question.getId());




        // Inflate the layout for this fragment
        return questionDetailsView;
    }




    public void setupRecyclerView(View questionDetailsView, String questionId) {
        RecyclerView recyclerView = (RecyclerView) questionDetailsView.findViewById(R.id.answerRecyclerView);
        answerRecyclerAdapter = new AnswersAdapter(questionDetailsPresenter.getRef(questionId), adapterAnswer, adapterKeys,getContext(),this);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(answerRecyclerAdapter);
    }





}
