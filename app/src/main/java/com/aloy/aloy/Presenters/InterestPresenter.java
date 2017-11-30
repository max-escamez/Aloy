package com.aloy.aloy.Presenters;

import android.content.Context;

import com.aloy.aloy.Contracts.AskContract;
import com.aloy.aloy.Contracts.InterestContract;
import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.SharedPreferenceHelper;
import com.aloy.aloy.Util.SpotifyHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by madmax on 2017-11-29.
 */

public class InterestPresenter implements InterestContract.Presenter {

    private InterestContract.View interestView;
    private DataHandler dataHandler;
    private SpotifyHandler spotifyHandler;

    private HashMap genreSelected;

    public InterestPresenter(InterestContract.View interestView, DataHandler dataHandler, SpotifyHandler spotifyHandler, Context context) {
        this.dataHandler = dataHandler;
        this.spotifyHandler = spotifyHandler;
        this.interestView = interestView;
        genreSelected = new HashMap();
    }

    @Override
    public void addGenre(String name,String url){genreSelected.put(name,url);}

    @Override
    public void removeGenre(String name){genreSelected.remove(name);}

    @Override
    public HashMap getGenres(){return this.genreSelected;}

    @Override
    public void update(){dataHandler.saveInterests(genreSelected);}

}
