package com.aloy.aloy.Presenters;

import com.aloy.aloy.Contracts.SearchContract;
import com.aloy.aloy.Fragments.Search;
import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.SpotifyHandler;

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

}
