package com.aloy.aloy.Models;

import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * Created by madmax on 01/11/2017.
 */

public class Answer {
    private String username;
    private String body;
    private String pic;
    private CircularImageView profilePicture;

    public Answer() {
    }

    public Answer(String username, String body, String pic) {
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
