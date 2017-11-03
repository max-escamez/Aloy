package com.aloy.aloy.Models;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tldonne on 29/10/2017.
 */

public class Question {
    private String username;
    private String body;
    private String pic;
    private CircularImageView profilePicture;
    private List<SearchResult> tracksAdded;

    public Question() {

    }

    public Question(String username, String body, String pic) {
        this.username = username;
        this.body = body;
        this.pic = pic;
        this.tracksAdded = new ArrayList<>();
    }

    public String getBody(){
        return body;
    }

    public String getUsername() {return username; }

    public String getPic(){return pic;}

    public void setBody(String body) {
        this.body=body;
    }

    public void addTrack(SearchResult track) {
        tracksAdded.add(track);
    }

    public int getTracksNb() {
        return tracksAdded.size();
    }

}
