package com.aloy.aloy.Contracts;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aloy.aloy.Adapters.SearchAdapter;
import com.aloy.aloy.Fragments.Ask;
import com.aloy.aloy.Models.SearchResult;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by tldonne on 01/11/2017.
 */

public interface SearchContract {

    interface View {

        void hideKeyboardFrom(Context context, android.view.View view);
        void hideSearch();
        Ask getAsk();
        void setupRecyclerView(android.view.View searchView, String searchQuery, String type);
        void updateCount(String type);

    }

    interface Presenter {
        void bind(String query, SearchAdapter.ViewHolder holder,int position, Context context, String type);
        int getCount();
        void addItem(String type, int position, String query);

        void removeItem(String type, int position, String query);
    }
}
