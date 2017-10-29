package com.aloy.aloy.Fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View feedView = inflater.inflate(R.layout.fragment_feed, container, false);
        addQuestionFab = (FloatingActionButton) feedView.findViewById(R.id.addQuestionButton);
        addQuestionField = (EditText) feedView.findViewById(R.id.addQuestionField);
        addQuestionFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddQuestion();
            }
        });

        addQuestionField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    question = addQuestionField.getText().toString();
                    System.out.println(question);
                    myRef.push().setValue(question);
                    hideKeyboardFrom(getContext(),feedView);
                    hideAddQuestion();
                    return true;
                }
                return false;
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
        addQuestionField.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideAddQuestion() {
        addQuestionField.setVisibility(View.GONE);
        addQuestionField.setText(null);
    }


}
