package com.aloy.aloy.Fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aloy.aloy.Contracts.FindContract;
import com.aloy.aloy.MainActivity;
import com.aloy.aloy.Presenters.AskPresenter;
import com.aloy.aloy.Presenters.FindPresenter;
import com.aloy.aloy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Find extends DialogFragment implements FindContract.View {

    private FindContract.Presenter findPresenter;
    private String type;
    private EditText searchField;
    private String searchQuery;
    private Button validateSearch;


    public Find() {
        // Required empty public constructor
    }

    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View findView = inflater.inflate(R.layout.fragment_find, container, false);
        findPresenter = new FindPresenter(this, MainActivity.getDataHandler(), MainActivity.getSpotifyHandler());
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Bundle args = getArguments();
        args.getString("type", type);
        validateSearch = (Button) findView.findViewById(R.id.validateSearch);
        /*switch (type) {
            case "track" :
                searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            searchQuery = searchField.getText().toString();
                            //findPresenter.search(searchQuery,type);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                hideKeyboardFrom(getContext(),findView);
                            }
                            hideFind();
                            return true;
                        }
                        return false;
                    }
                });
        }*/

        validateSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFind();
            }
        });


        return findView;

    }

    @Override
    public void hideFind() {
        this.dismiss();

    }

}
