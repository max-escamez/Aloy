package com.aloy.aloy.Views;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.aloy.aloy.Adapters.SimpleTabAdapter;
import com.aloy.aloy.R;
import com.aloy.aloy.Util.AchievementsHandler;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    //private ProfilePresenter profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportPostponeEnterTransition();
        if(this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.fragment_profile_horizontal);
        } else if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.fragment_profile);
        }
        //profilePresenter = new ProfilePresenter(this, MainActivity.getDataHandler());
        Bundle extras = getIntent().getExtras();
        String usernameTransitionName = extras.getString(Details.USERNAME_TRANSITION_NAME);
        String picTranstionName = extras.getString(Details.PROFILE_PIC_TRANSITION_NAME);
        String fbName = extras.getString(Details.FB_NAME);

        final AchievementsHandler mAchievementsHandler = new AchievementsHandler(this,usernameTransitionName);

        TextView username = (TextView) findViewById(R.id.username);
        CircleImageView profilePicture = (CircleImageView) findViewById(R.id.profilePicture);
        ViewPager profileViewPager = (ViewPager) findViewById(R.id.profile_view_pager);
        CircleImageView achievement1 = (CircleImageView) findViewById(R.id.achievement_1);
        CircleImageView achievement2 = (CircleImageView) findViewById(R.id.achievement_2);
        CircleImageView achievement3 = (CircleImageView) findViewById(R.id.achievement_3);
        CircleImageView achievement4 = (CircleImageView) findViewById(R.id.achievement_4);
        CircleImageView achievement5 = (CircleImageView) findViewById(R.id.achievement_5);
        CircleImageView achievement6 = (CircleImageView) findViewById(R.id.achievement_6);
        CircleImageView achievement7 = (CircleImageView) findViewById(R.id.achievement_7);
        CircleImageView achievement8 = (CircleImageView) findViewById(R.id.achievement_8);
        CircleImageView achievement9 = (CircleImageView) findViewById(R.id.achievement_9);
        CircleImageView achievement10 = (CircleImageView) findViewById(R.id.achievement_10);

        Picasso.with(this).load(picTranstionName).noFade().into(profilePicture);
        AchievementsHandler achievementsHandler = new AchievementsHandler(this,usernameTransitionName);
        achievementsHandler.setProfilePicBorder(profilePicture);


        if (fbName.equals("")){
            username.setText(usernameTransitionName);
        }
        else {
            username.setText(fbName);
        }

        mAchievementsHandler.getAchievements(achievement1,"questions");
        mAchievementsHandler.getAchievements(achievement2,"answers");
        mAchievementsHandler.getAchievements(achievement3,"requests");
        mAchievementsHandler.getAchievements(achievement4,"answersVIP");
        mAchievementsHandler.getAchievements(achievement5,"upvotesVIP");
        mAchievementsHandler.getAchievements(achievement6,"followersTOP");
        mAchievementsHandler.getAchievements(achievement7,"answersTOP");
        mAchievementsHandler.getAchievements(achievement8,"upvotesTOP");
        mAchievementsHandler.getAchievements(achievement9,"requestsVIP");
        mAchievementsHandler.getAchievements(achievement10,"followersVIP");

        setupViewPager(profileViewPager,usernameTransitionName);
        TabLayout tabs = (TabLayout) findViewById(R.id.profile_tabs);
        tabs.setupWithViewPager(profileViewPager);

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

        SimpleTabAdapter profileAdapter = new SimpleTabAdapter(getSupportFragmentManager());
        profileAdapter.addFragments(questions,"Questions");
        profileAdapter.addFragments(answers,"Answers");
        viewPager.setAdapter(profileAdapter);
    }
}



