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
    private String primaryText;
    private String secondaryText;
    private String spotifyId;
    private String type;
    private String uri;

    public SpotifyItem(){

    }

    public SpotifyItem(String cover,String primaryText,String secondaryText,String spotifyId,String type,String uri) {
        this.cover=cover;
        this.primaryText=primaryText;
        this.secondaryText=secondaryText;
        this.spotifyId=spotifyId;
        this.type=type;
        this.uri=uri;
    }

    public String getCover() {
        return cover;
    }

    public String getPrimaryText() {
        return primaryText;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public String getUri() {
        return uri;
    }

    public String getType() {
        return type;
    }
}
