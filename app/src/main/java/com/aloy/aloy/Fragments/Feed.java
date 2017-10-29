package com.aloy.aloy.Fragments;


import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.aloy.aloy.Contracts.FeedContract;
import com.aloy.aloy.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class Feed extends Fragment implements FeedContract.View {

    private FeedContract.Presenter feedPresenter;
    private EditText addQuestionField;
    private FloatingActionButton addQuestionFab;
    private String question;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("questions");


    public Feed() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View feedView = inflater.inflate(R.layout.fragment_feed, container, false);
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
