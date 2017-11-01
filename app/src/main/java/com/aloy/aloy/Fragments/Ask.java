package com.aloy.aloy.Fragments;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aloy.aloy.Contracts.AskContract;
import com.aloy.aloy.Contracts.FeedContract;
import com.aloy.aloy.MainActivity;
import com.aloy.aloy.Presenters.AskPresenter;
import com.aloy.aloy.R;
import com.aloy.aloy.Util.DataHandler;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ask extends DialogFragment implements AskContract.View{

    private EditText askQuestionField;
    private Button submitButton;
    private ImageButton close;
    private String questionBody;
    private AskContract.Presenter askPresenter;
    private DataHandler dataHandler;



    public Ask() {
        // Required empty public constructor
    }



    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View askView = inflater.inflate(R.layout.fragment_ask, container, false);
        askPresenter = new AskPresenter(this,MainActivity.getDataHandler(),MainActivity.getSpotifyHandler(),getContext());
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        askQuestionField = (EditText) askView.findViewById(R.id.askQuestionField);
        submitButton = (Button) askView.findViewById(R.id.submitQuestion);
        close = (ImageButton) askView.findViewById(R.id.closeButton);

        askQuestionField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    questionBody = askQuestionField.getText().toString();
                    askPresenter.createQuestion(questionBody);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        hideKeyboardFrom(getContext(),askView);
                    }
                    hideAskQuestion();
                    return true;
                }
                return false;
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAskQuestion();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionBody = askQuestionField.getText().toString();
                askPresenter.createQuestion(questionBody);
                hideAskQuestion();
            }
        });


        return askView;
    }



    @Override
    public void hideAskQuestion() {
        this.dismiss();

    }
}
