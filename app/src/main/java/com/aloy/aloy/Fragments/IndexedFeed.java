package com.aloy.aloy.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aloy.aloy.Adapters.IndexedFeedAdapter;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.R;

import static com.aloy.aloy.Fragments.Feed.EXTRA_QUESTION;
import static com.aloy.aloy.Fragments.Feed.EXTRA_QUESTION_TRANSITION_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexedFeed extends Fragment {

    private IndexedFeedAdapter indexedFeedAdapter;
    private LinearLayoutManager layoutManager;
    //tools:context="com.aloy.aloy.Fragments.Feed"


    public IndexedFeed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View indexedFeedView = inflater.inflate(R.layout.fragment_feed, container, false);
        Bundle args = getArguments();
        String userId = args.getString("userId");
        String type = args.getString("type");
        setupRecyclerView(indexedFeedView,userId,type);


        return indexedFeedView;
    }



    public void setupRecyclerView(View indexedFeedView,String userId,String type) {
        RecyclerView recyclerView = (RecyclerView) indexedFeedView.findViewById(R.id.feedRecyclerView);
        indexedFeedAdapter = new IndexedFeedAdapter(userId,type, getContext(), this);
        layoutManager = new LinearLayoutManager(getContext());
        //layoutManager.setReverseLayout(true);
        //layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(indexedFeedAdapter.getAdapter());
    }


    public void onQuestionCLick(Question question, View itemView) {
        Intent intent = new Intent(this.getActivity(), Details.class);
        itemView.setTransitionName(EXTRA_QUESTION_TRANSITION_NAME);
        intent.putExtra(EXTRA_QUESTION, question);
        intent.putExtra(EXTRA_QUESTION_TRANSITION_NAME, ViewCompat.getTransitionName(itemView));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this.getActivity(), itemView,
                ViewCompat.getTransitionName(itemView));

        startActivity(intent, options.toBundle());
    }

    public void onAnswerClick(String questionId) {
        FragmentManager fragmentManager = getFragmentManager();
        Ask askDialog = new Ask();
        Bundle args = new Bundle();
        args.putString("questionId", questionId);
        askDialog.setArguments(args);
        askDialog.show(fragmentManager,"answer");
    }

}
