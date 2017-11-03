package com.aloy.aloy.Fragments;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.aloy.aloy.Adapters.MyRecyclerAdapter;
import com.aloy.aloy.Contracts.FeedContract;
import com.aloy.aloy.MainActivity;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Presenters.FeedPresenter;
import com.aloy.aloy.R;
import com.aloy.aloy.Util.DataHandler;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Feed extends Fragment implements FeedContract.View {

    private FeedContract.Presenter feedPresenter;
    private FloatingActionButton addQuestionFab;
    private Query query;
    private MyRecyclerAdapter myRecyclerAdapter;
    private ArrayList<Question> adapterQuestions;
    private ArrayList<String> adapterKeys;
    private LinearLayoutManager layoutManager;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("questions");


    public Feed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View feedView = inflater.inflate(R.layout.fragment_feed, container, false);
        feedPresenter = new FeedPresenter(this,MainActivity.getDataHandler());
        query = feedPresenter.getQuery();
        setupRecyclerView(feedView);
        addQuestionFab = (FloatingActionButton) feedView.findViewById(R.id.addQuestionButton);
        addQuestionFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddQuestion();
            }
        });
        return feedView;
    }



    @Override
    public void setupRecyclerView(View feedView) {
        RecyclerView recyclerView = (RecyclerView) feedView.findViewById(R.id.feedRecyclerView);
        myRecyclerAdapter = new MyRecyclerAdapter(myRef, adapterQuestions, adapterKeys);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myRecyclerAdapter);
    }


    @Override
    public void setPresenter(FeedContract.Presenter presenter) {
        feedPresenter = presenter;
    }

    @Override
    public void showAddQuestion() {
        FragmentManager fragmentManager = getFragmentManager();
        Ask askDialog = new Ask();
        askDialog.show(fragmentManager,"ask");

    }

}
