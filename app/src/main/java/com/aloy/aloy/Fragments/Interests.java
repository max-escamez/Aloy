package com.aloy.aloy.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aloy.aloy.Adapters.QuestionFeedAdapter;
import com.aloy.aloy.Contracts.FeedContract;
import com.aloy.aloy.Contracts.InterestContract;
import com.aloy.aloy.MainActivity;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Presenters.FeedPresenter;
import com.aloy.aloy.Presenters.InterestPresenter;
import com.aloy.aloy.R;
import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.SharedPreferenceHelper;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Interests extends Fragment implements InterestContract.View{

   private Button findInterests;
   private TextView noInterests;
   private SharedPreferenceHelper mSharedPreferenceHelper;
   private Search findGenres;
   private InterestPresenter interestsPresenter;

    public Interests() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View interestView = inflater.inflate(R.layout.fragment_interests, container, false);
        mSharedPreferenceHelper = new SharedPreferenceHelper(getContext());
        interestsPresenter = new InterestPresenter(this,MainActivity.getDataHandler(),MainActivity.getSpotifyHandler(),getContext());
        final FragmentManager fragmentManager = getFragmentManager();
        findGenres = new Search();
        findGenres.setTargetFragment(this,0);
        final Bundle args = new Bundle();
        args.putString("type", "genre");
        args.putString("interest","true");
        findInterests = (Button) interestView.findViewById(R.id.findInterests);
        noInterests = (TextView) interestView.findViewById(R.id.noInterestsText);
        //mSharedPreferenceHelper.saveInterests("null");
        if(mSharedPreferenceHelper.getInterests().equals("null")) {
            findInterests.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    findGenres.setArguments(args);
                    findGenres.show(fragmentManager,"search");
                }
            });
        }else{
            findInterests.setVisibility(GONE);
            noInterests.setVisibility(GONE);
            //Display questions that matches the styles selected
        }

        return interestView;
    }

    @Override
    public InterestContract.Presenter getInterestPresenter() {
        return interestsPresenter;
    }

}
