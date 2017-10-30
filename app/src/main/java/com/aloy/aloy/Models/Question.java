package com.aloy.aloy.Models;

import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * Created by tldonne on 29/10/2017.
 */

public class Question {
    private String username;
    private String body;
    private CircularImageView profilePicture;

    public Question() {

    }

    public Question(String username, String body) {
        this.username = username;
        this.body = body;
    }

    public String getBody(){
        return body;
    }

    public String getUsername() {return username; }

    public void setBody(String body) {
        this.body=body;
    }

}
