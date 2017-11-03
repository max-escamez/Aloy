package com.aloy.aloy.Contracts;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aloy.aloy.Adapters.SearchAdapter;
import com.aloy.aloy.Models.SearchResult;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by tldonne on 01/11/2017.
 */

public interface SearchContract {

    interface View {

        void hideKeyboardFrom(Context context, android.view.View view);
        void hideSearch();
        void setupRecyclerView(android.view.View searchView, String query);
        //void setupRecyclerView(android.view.View searchView);
        void addTrack(Track track);
        void removeTrack(Track track);
    }

    interface Presenter {
        void bindTrack(String query, SearchAdapter.ViewHolder holder,int position, Context context);
        int getCount();
        void addTrack(int position, String query);
        void removeTrack(int position, String query);
    }
}
