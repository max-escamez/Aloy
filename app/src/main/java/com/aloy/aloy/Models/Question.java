package com.aloy.aloy.Models;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tldonne on 29/10/2017.
 */

public class Question {
    private String username;
    private String body;
    private String pic;

    public Question() {
    }

    public Question(String username, String pic, String body) {
        this.username = username;
        this.body = body;
        this.pic = pic;
    }

    public String getBody(){
        return body;
    }

    public String getUsername() {return username; }

    public String getPic(){return pic;}

    public void setBody(String body) {
        this.body=body;
    }



}
