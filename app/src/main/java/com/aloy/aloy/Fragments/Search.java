package com.aloy.aloy.Fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.aloy.aloy.Adapters.MyRecyclerAdapter;
import com.aloy.aloy.Adapters.SearchAdapter;
import com.aloy.aloy.Contracts.SearchContract;
import com.aloy.aloy.MainActivity;
import com.aloy.aloy.Models.SearchResult;
import com.aloy.aloy.Presenters.SearchPresenter;
import com.aloy.aloy.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends DialogFragment implements SearchContract.View {

    private SearchPresenter searchPresenter;
    private String type;
    private EditText searchField;
    private String searchQuery;
    private Button validateSearch;
    private SearchAdapter searchAdapter;
    private GridLayoutManager layoutManager;


    public Search() {
        // Required empty public constructor
    }

    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View searchView = inflater.inflate(R.layout.fragment_search, container, false);
        searchPresenter = new SearchPresenter(this, MainActivity.getDataHandler(), MainActivity.getSpotifyHandler());
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        validateSearch = (Button) searchView.findViewById(R.id.validateSearch);
        searchField = (EditText) searchView.findViewById(R.id.searchSpotify);

        Bundle args = getArguments();
        type = args.getString("type");
        switch (type) {
            case "track" :
                searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            searchQuery = searchField.getText().toString();
                            setupRecyclerView(searchView,searchQuery);
                            //setupRecyclerView(searchView);

                            //searchPresenter.search(searchQuery,type);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                hideKeyboardFrom(getContext(),searchView);
                            }
                            return true;
                        }
                        return false;
                    }
                });
        }

        validateSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSearch();
            }
        });


        return searchView;

    }

    @Override
    public void hideSearch() {
        this.dismiss();

    }


    @Override
    public void setupRecyclerView(View searchView, String searchQuery) {
        RecyclerView recyclerView = (RecyclerView) searchView.findViewById(R.id.searchGrid);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        searchAdapter = new SearchAdapter(getContext(), searchPresenter, searchQuery);
        recyclerView.setAdapter(searchAdapter);

    }

    /*@Override
    public void setupRecyclerView(View searchView) {
        String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48"};
        int numberOfColumns = 3;
        RecyclerView recyclerView = (RecyclerView) searchView.findViewById(R.id.searchGrid);
        searchAdapter = new SearchAdapter(getContext(), data);
        layoutManager = new GridLayoutManager(getContext(), numberOfColumns);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(searchAdapter);

    }*/



}
