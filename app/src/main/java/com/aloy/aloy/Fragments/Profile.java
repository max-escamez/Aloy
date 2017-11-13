package com.aloy.aloy.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.aloy.aloy.Adapters.InboxAdapter;
import com.aloy.aloy.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    //private ProfilePresenter profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportPostponeEnterTransition();

        setContentView(R.layout.fragment_profile);
        //profilePresenter = new ProfilePresenter(this, MainActivity.getDataHandler());
        Bundle extras = getIntent().getExtras();
        String usernameTransitionName = extras.getString(Details.USERNAME_TRANSITION_NAME);
        String picTranstionName = extras.getString(Details.PROFILE_PIC_TRANSITION_NAME);
        TextView username = (TextView) findViewById(R.id.username);
        CircleImageView profilePicture = (CircleImageView) findViewById(R.id.profilePicture);
        username.setText(usernameTransitionName);
        Picasso.with(this).load(picTranstionName).noFade().into(profilePicture);

        ViewPager profileViewPager = (ViewPager) findViewById(R.id.profile_view_pager);
        setupViewPager(profileViewPager,usernameTransitionName);

        supportStartPostponedEnterTransition();

    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }

    private void setupViewPager(ViewPager viewPager,String username) {

        Bundle questionArgs = new Bundle();
        questionArgs.putString("userId", username);
        questionArgs.putString("type","questions");
        Fragment questions = new IndexedFeed();
        questions.setArguments(questionArgs);


        Bundle answersArgs = new Bundle();
        answersArgs.putString("userId", username);
        answersArgs.putString("type","answers");
        Fragment answers = new IndexedFeed();
        answers.setArguments(answersArgs);

        InboxAdapter profileAdapter = new InboxAdapter(getSupportFragmentManager());
        profileAdapter.addFragments(questions,"Questions");
        profileAdapter.addFragments(answers,"Answers");
        viewPager.setAdapter(profileAdapter);
    }
}



