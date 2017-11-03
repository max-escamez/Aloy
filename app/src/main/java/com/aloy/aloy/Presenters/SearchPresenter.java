package com.aloy.aloy.Presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aloy.aloy.Adapters.SearchAdapter;
import com.aloy.aloy.Contracts.SearchContract;
import com.aloy.aloy.Fragments.Search;
import com.aloy.aloy.Models.SearchResult;
import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.SpotifyHandler;

import java.util.ArrayList;

/**
 * Created by tldonne on 01/11/2017.
 */

public class SearchPresenter implements SearchContract.Presenter {

    private Search searchView;
    private DataHandler dataHandler;
    private SpotifyHandler spotifyHandler;

    public SearchPresenter(Search searchView, DataHandler dataHandler, SpotifyHandler spotifyHandler) {
        this.dataHandler = dataHandler;
        this.spotifyHandler = spotifyHandler;
        this.searchView = searchView;
    }

    @Override
    public void bindTrack(String query, SearchAdapter.ViewHolder holder, int position, Context context) {
        spotifyHandler.bindTrack(query,holder,position,context);
    }


    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public void addTrack(int position, String query) {
        spotifyHandler.addTrack(position,query,searchView,true);

    }

    @Override
    public  void removeTrack(int position, String query) {
        spotifyHandler.addTrack(position,query,searchView,false);
    }
}
