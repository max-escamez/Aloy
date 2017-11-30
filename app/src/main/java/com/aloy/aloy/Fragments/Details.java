package com.aloy.aloy.Fragments;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.aloy.aloy.Adapters.AnswersAdapter;
import com.aloy.aloy.Adapters.CoverFlowAdapter;
import com.aloy.aloy.Contracts.QuestionDetailsContract;
import com.aloy.aloy.MainActivity;
import com.aloy.aloy.Models.Answer;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Presenters.QuestionDetailsPresenter;
import com.aloy.aloy.R;
import com.aloy.aloy.Util.SharedPreferenceHelper;
import com.squareup.picasso.Picasso;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class Details extends AppCompatActivity implements QuestionDetailsContract.View {
    private QuestionDetailsPresenter questionDetailsPresenter;
    private ArrayList<Answer> adapterAnswer;
    private ArrayList<String> adapterKeys;
    private CircleImageView profilePic;
    private TextView username;
    private Button request;
    private ImageButton answer;
    private ImageButton follow;
    private TextView body ;
    private View questionView;
    public static final String USERNAME_TRANSITION_NAME = "usernameTransitionName" ;
    public static final String PROFILE_PIC_TRANSITION_NAME = "profilePicTransitionName" ;
    public static final String FB_NAME = "fbName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_question_details);
        supportPostponeEnterTransition();
        questionDetailsPresenter = new QuestionDetailsPresenter(this, MainActivity.getDataHandler());
        SharedPreferenceHelper mSharedPreferenceHelper = new SharedPreferenceHelper(this);
        Bundle extras = getIntent().getExtras();
        final Question question = extras.getParcelable(Feed.EXTRA_QUESTION);
        String transitionName = extras.getString(Feed.EXTRA_QUESTION_TRANSITION_NAME);

        setupQuestion(question,transitionName,this);
        setupAnswers(question.getId());

        supportStartPostponedEnterTransition();


        if (!(question.getUsername().equals(mSharedPreferenceHelper.getCurrentUserId()))) {
            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openProfile(question);
                }
            });
            profilePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openProfile(question);

                }
            });
        }
        request.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showRequest(question.getId());
            }
        });

        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswerQuestion(question.getId());
            }
        });

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getDataHandler().follow(question.getId());
                if (follow.getTag().equals(R.drawable.ic_playlist_add)){
                    follow.setImageResource(R.drawable.ic_playlist_add_check);
                    follow.setTag(R.drawable.ic_playlist_add_check);
                }
                else {
                    follow.setImageResource(R.drawable.ic_playlist_add);
                    follow.setTag(R.drawable.ic_playlist_add);

                }
                //MainActivity.getDataHandler().getFollow(question.getId(),follow);
            }
        });



    }

    private void openProfile(Question question) {
        Intent intent = new Intent(this, Profile.class);
        ViewCompat.setTransitionName(username,question.getUsername());
        ViewCompat.setTransitionName(profilePic,question.getPic());
        Pair usernamePair = Pair.create(username,ViewCompat.getTransitionName(username));
        Pair picturePair = Pair.create(profilePic,ViewCompat.getTransitionName(profilePic));
        intent.putExtra(USERNAME_TRANSITION_NAME, ViewCompat.getTransitionName(username));
        intent.putExtra(PROFILE_PIC_TRANSITION_NAME, ViewCompat.getTransitionName(profilePic));
        intent.putExtra(FB_NAME,question.getName());

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                usernamePair,
                picturePair);
        startActivity(intent, options.toBundle());

    }

    @Override
    public void setupAnswers(String questionId) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.answerRecyclerView);
        AnswersAdapter answerRecyclerAdapter = new AnswersAdapter(this, questionDetailsPresenter,questionId,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(answerRecyclerAdapter.getAdapter());
    }

    @Override
    public void setupQuestion(Question question, String transitionName,final Context context){
        profilePic = (CircleImageView) findViewById(R.id.questionDetailProfilePic);
        username = (TextView) findViewById(R.id.questionDetailUsername);
        body = (TextView) findViewById(R.id.questionDetailBody);
        request = (Button) findViewById(R.id.requestButton);
        follow = (ImageButton) findViewById(R.id.detail_follow);
        answer = (ImageButton) findViewById(R.id.detail_answer);

        body.setText(question.getBody());
        if((question.getName()).equals("")){
            username.setText(question.getUsername());
        }else{
            username.setText(question.getName());
        }
        MainActivity.getDataHandler().getUrl(question.getUsername(),profilePic,context);
        MainActivity.getDataHandler().getFollow(question.getId(),follow);
        setupCoverFlow(question);
        //Picasso.with(this).load(question.getPic()).into(profilePic);
        questionView = findViewById(R.id.questionDetail);
        questionView.setTransitionName(transitionName);
    }

    private void setupCoverFlow(Question question) {
        RecyclerView items = (RecyclerView) findViewById(R.id.detail_recycler);
        CoverFlowAdapter adapter = new CoverFlowAdapter(this,MainActivity.getDataHandler().getRefQuestionFeed().child(question.getId()),this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        items.setLayoutManager(layoutManager);
        items.setAdapter(adapter.getAdapter());
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(items);

    }

    @Override
    public void showRequest(String questionId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        RequestDialog requestDialog = new RequestDialog();
        Bundle args = new Bundle();
        args.putString("question","false");
        args.putString("questionId", questionId);
        requestDialog.setArguments(args);
        requestDialog.show(fragmentManager,"request");
    }

    @Override
    public QuestionDetailsPresenter getPresenter() {
        return this.questionDetailsPresenter;
    }

    @Override
    public void onBackPressed() {
        //finishAfterTransition();

        finish();
    }

    @Override
    public void showAnswerQuestion(String questionId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Ask askDialog = new Ask();
        Bundle args = new Bundle();
        args.putString("questionId", questionId);
        askDialog.setArguments(args);
        askDialog.show(fragmentManager,"answer");
    }




}



