package com.aloy.aloy.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aloy.aloy.Adapters.InboxAdapter;
import com.aloy.aloy.R;
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
        TextView username = (TextView) profileView.findViewById(R.id.username);
        CircleImageView profilePicture = (CircleImageView) profileView.findViewById(R.id.profilePicture);

        if(mSharedPreferenceHelper.getCurrentUserName().equals("")){
            username.setText(mSharedPreferenceHelper.getCurrentUserId());
        }else{
            username.setText(mSharedPreferenceHelper.getCurrentUserName());
        }
        Picasso.with(getContext()).load(mSharedPreferenceHelper.getProfilePicture()).into(profilePicture);
        ViewPager profileViewPager = (ViewPager) profileView.findViewById(R.id.profile_view_pager);
        setupViewPager(profileViewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) profileView.findViewById(R.id.profile_tabs);
        tabs.setupWithViewPager(profileViewPager);

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

        InboxAdapter profileAdapter = new InboxAdapter(getChildFragmentManager());
        profileAdapter.addFragments(questions,"Questions");
        profileAdapter.addFragments(answers,"Answers");
        viewPager.setAdapter(profileAdapter);
    }
}
