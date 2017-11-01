package com.aloy.aloy.Presenters;

import com.aloy.aloy.Contracts.AskContract;
import com.aloy.aloy.Contracts.FindContract;
import com.aloy.aloy.Fragments.Find;
import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.SpotifyHandler;

/**
 * Created by tldonne on 01/11/2017.
 */

public class FindPresenter implements FindContract.Presenter {

    private Find findView;
    private DataHandler dataHandler;
    private SpotifyHandler spotifyHandler;

    public FindPresenter(Find findView, DataHandler dataHandler, SpotifyHandler spotifyHandler) {
        this.dataHandler = dataHandler;
        this.spotifyHandler = spotifyHandler;
        this.findView = findView;
    }

}
