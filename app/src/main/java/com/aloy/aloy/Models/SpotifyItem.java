package com.aloy.aloy.Models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.R;

/**
 * Created by tldonne on 02/11/2017.
 */

public class SpotifyItem {

    private String cover;
    private String name;
    private String artist;
    private String spotifyId;
    private String type;
    private String uri;

    public SpotifyItem(){

    }

    public SpotifyItem(String cover,String primaryText,String secondaryText,String spotifyId,String type,String uri) {
        this.cover=cover;
        this.name=primaryText;
        this.artist=secondaryText;
        this.spotifyId=spotifyId;
        this.type=type;
        this.uri=uri;
    }

    public String getCover() {
        return cover;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getUri() {
        return uri;
    }

    public String getType() {
        return type;
    }

}
