package com.aloy.aloy.Fragments;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aloy.aloy.Contracts.AskContract;
import com.aloy.aloy.MainActivity;
import com.aloy.aloy.Presenters.AskPresenter;
import com.aloy.aloy.R;

import java.util.HashMap;

import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ask extends DialogFragment implements AskContract.View{

    private EditText askQuestionField;
    private Button submitButton;
    private Button searchTracks;
    private Button searchArtists;
    private Button searchAlbums;
    private ImageButton close;
    private String questionBody;
    private AskContract.Presenter askPresenter;
    private TextView tracksSelectedTextView;
    private TextView artistsSelectedTextView;
    private TextView albumsSelectedTextView;
    private String questionId;
    private boolean answer;




    public Ask() {
        // Required empty public constructor
    }

    public void hideKeyboardFrom(Context context, View view) {
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
        searchTracks = (Button) askView.findViewById(R.id.findTracks);
        tracksSelectedTextView = (TextView) askView.findViewById(R.id.tracksSelected);
        searchAlbums = (Button) askView.findViewById(R.id.findAlbums);
        albumsSelectedTextView = (TextView) askView.findViewById(R.id.albumsSelected);
        searchArtists = (Button) askView.findViewById(R.id.findArtists);
        artistsSelectedTextView = (TextView) askView.findViewById(R.id.artistsSelected);

        Bundle args = getArguments();
        questionId = args.getString("questionId");

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

                if (!questionId.equals("null"))
                    askPresenter.createAnswer(questionBody,questionId);
                else
                    askPresenter.createQuestion(questionBody);
                hideAskQuestion();
            }
        });

        searchTracks.setOnClickListener(new View.OnClickListener() {
            String type = "track";
            @Override
            public void onClick(View v) {
                showSearch(type);
            }
        });

        searchArtists.setOnClickListener(new View.OnClickListener() {
            String type = "artist";
            @Override
            public void onClick(View v) {
                showSearch(type);
            }
        });

        searchAlbums.setOnClickListener(new View.OnClickListener() {
            String type = "album";
            @Override
            public void onClick(View v) {
                showSearch(type);
            }
        });

        return askView;
    }



    @Override
    public void hideAskQuestion() {
        this.dismiss();

    }

    @Override
    public void showSearch(String type) {
        FragmentManager fragmentManager = getFragmentManager();
        Search searchTracksDialog = new Search();
        searchTracksDialog.setTargetFragment(this,0);
        Bundle args = new Bundle();
        args.putString("type", type);
        searchTracksDialog.setArguments(args);
        searchTracksDialog.show(fragmentManager,"search");
    }


    @Override
    public void update() {
        tracksSelectedTextView.setText(askPresenter.getTracks().size() + " Tracks Selected");
        artistsSelectedTextView.setText(askPresenter.getArtists().size() + " Artists Selected");
        albumsSelectedTextView.setText(askPresenter.getAlbums().size() + " Albums Selected");
    }

    @Override
    public AskContract.Presenter getAskPresenter() {
        return askPresenter;
    }




}
