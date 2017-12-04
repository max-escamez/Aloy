package com.aloy.aloy.Views;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aloy.aloy.Adapters.SimpleTabAdapter;
import com.aloy.aloy.R;
import com.aloy.aloy.Util.AchievementsHandler;
import com.aloy.aloy.Util.SharedPreferenceHelper;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile extends Fragment {

    //SpotifyService service = MainActivity.getService();
    //String username = service.getMe().id;

    private SharedPreferenceHelper mSharedPreferenceHelper;

    public MyProfile(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);

        mSharedPreferenceHelper = new SharedPreferenceHelper(getContext());
        final AchievementsHandler mAchievementsHandler = new AchievementsHandler(getContext(),mSharedPreferenceHelper.getCurrentUserId());
        TextView username = (TextView) profileView.findViewById(R.id.username);
        CircleImageView profilePicture = (CircleImageView) profileView.findViewById(R.id.profilePicture);
        CircleImageView achievement1 = (CircleImageView) profileView.findViewById(R.id.achievement_1);
        CircleImageView achievement2 = (CircleImageView) profileView.findViewById(R.id.achievement_2);
        CircleImageView achievement3 = (CircleImageView) profileView.findViewById(R.id.achievement_3);
        CircleImageView achievement4 = (CircleImageView) profileView.findViewById(R.id.achievement_4);
        CircleImageView achievement5 = (CircleImageView) profileView.findViewById(R.id.achievement_5);
        CircleImageView achievement6 = (CircleImageView) profileView.findViewById(R.id.achievement_6);
        CircleImageView achievement7 = (CircleImageView) profileView.findViewById(R.id.achievement_7);
        CircleImageView achievement8 = (CircleImageView) profileView.findViewById(R.id.achievement_8);
        CircleImageView achievement9 = (CircleImageView) profileView.findViewById(R.id.achievement_9);
        CircleImageView achievement10 = (CircleImageView) profileView.findViewById(R.id.achievement_10);


        if(mSharedPreferenceHelper.getCurrentUserName().equals("")){
            username.setText(mSharedPreferenceHelper.getCurrentUserId());
        }else{
            username.setText(mSharedPreferenceHelper.getCurrentUserName());
        }
        Picasso.with(getContext()).load(mSharedPreferenceHelper.getProfilePicture()).into(profilePicture);
        final AchievementsHandler achievementsHandler = new AchievementsHandler(getContext(),mSharedPreferenceHelper.getCurrentUserId());
        achievementsHandler.setProfilePicBorder(profilePicture);

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

        ViewPager profileViewPager = (ViewPager) profileView.findViewById(R.id.profile_view_pager);
        setupViewPager(profileViewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) profileView.findViewById(R.id.profile_tabs);
        tabs.setupWithViewPager(profileViewPager);

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                achievementsHandler.getScore(getActivity());
            }
        });


        achievement1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"questions");
            }
        });

        achievement2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"answers");
            }
        });

        achievement3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"requests");
            }
        });

        achievement4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"answersVIP");
            }
        });
        achievement5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"upvotesVIP");
            }
        });
        achievement6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"followersTOP");
            }
        });

        achievement7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"answersTOP");
            }
        });

        achievement8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"upvotesTOP");
            }
        });

        achievement9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"requestsVIP");
            }
        });
        achievement10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"followersVIP");
            }
        });

        return profileView;
    }

    private void setupViewPager(ViewPager viewPager) {

        Bundle questionArgs = new Bundle();
        questionArgs.putString("userId", mSharedPreferenceHelper.getCurrentUserId());
        questionArgs.putString("type","questions");
        Fragment questions = new IndexedFeed();
        questions.setArguments(questionArgs);


        Bundle answersArgs = new Bundle();
        answersArgs.putString("userId", mSharedPreferenceHelper.getCurrentUserId());
        answersArgs.putString("type","answers");
        Fragment answers = new IndexedFeed();
        answers.setArguments(answersArgs);

        SimpleTabAdapter profileAdapter = new SimpleTabAdapter(getChildFragmentManager());
        profileAdapter.addFragments(questions,"Questions");
        profileAdapter.addFragments(answers,"Answers");
        viewPager.setAdapter(profileAdapter);
    }
}
