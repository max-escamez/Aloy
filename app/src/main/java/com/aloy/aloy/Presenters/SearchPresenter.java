package com.aloy.aloy.Presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aloy.aloy.Adapters.SearchAdapter;
import com.aloy.aloy.Contracts.SearchContract;
import com.aloy.aloy.Fragments.Search;
import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.SpotifyHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by tldonne on 01/11/2017.
 */

public class SearchPresenter implements SearchContract.Presenter {

    private Search searchView;
    private DataHandler dataHandler;
    private SpotifyHandler spotifyHandler;
    private int tracksNb;

    public SearchPresenter(Search searchView, DataHandler dataHandler, SpotifyHandler spotifyHandler) {
        this.dataHandler = dataHandler;
        this.spotifyHandler = spotifyHandler;
        this.searchView = searchView;
        tracksNb=0;

    }

    @Override
    public void bind(String query, SearchAdapter.ViewHolder holder, int position, Context context, String type, SearchAdapter searchAdapter) {
        switch (type) {
            case "track":
                spotifyHandler.bindTrack(query,holder,position,context);
                break;
            case "artist":
                spotifyHandler.bindArtist(query,holder,position,context);
                break;
            case "album":
                spotifyHandler.bindAlbum(query,holder,position,context);
                break;
        }
    }




    @Override
    public void addItem(String type, int position, String query) {
        switch (type) {
            case "track" :
                spotifyHandler.addTrack(position,query,searchView,true);
                break;
            case "artist" :
                spotifyHandler.addArtist(position,query,searchView,true);
                break;
            case "album" :
                spotifyHandler.addAlbum(position,query,searchView,true);
                break;
        }
    }

    @Override
    public  void removeItem(String type, int position, String query) {
        switch (type) {
            case "track" :
                spotifyHandler.addTrack(position,query,searchView,false);
                break;
            case "artist" :
                spotifyHandler.addArtist(position,query,searchView,false);
                break;
            case "album" :
                spotifyHandler.addAlbum(position,query,searchView,false);
                break;
        }
    }

    @Override
    public void setupSearchRecycler(RecyclerView recyclerView, View searchView, Context context, String searchQuery, String type) {
        switch (type) {
            case "track" :
                spotifyHandler.setupSearchRecyclerTracks(recyclerView,context,this,encodeQuery(searchQuery),type);
                break;
            case "artist" :
                spotifyHandler.setupSearchRecyclerArtists(recyclerView,context,this,encodeQuery(searchQuery),type);
                break;
            case "album" :
                spotifyHandler.setupSearchRecyclerAlbums(recyclerView,context,this,encodeQuery(searchQuery),type);
                break;
        }
    }

    @Override
    public String encodeQuery(String query) {
        try {
            query=URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return query;
    }

    /*public int getitemCount() {
        return this.tracksNb;
    }

    @Override
    public void setCount(int tracksNb) {
        this.tracksNb=tracksNb;
        //System.out.println(this.tracksNb.get(0));
    }

    public void updateCount(String searchQuery) {
        spotifyHandler.setTrackCount(encodeQuery(searchQuery),this);
    }*/
}
