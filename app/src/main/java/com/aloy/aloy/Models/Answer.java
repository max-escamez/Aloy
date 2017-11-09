package com.aloy.aloy.Models;

import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * Created by madmax on 01/11/2017.
 */

public class Answer {
    private String id;
    private String username;
    private String body;
    private String pic;
    private String name;
    private String date;
    private CircularImageView profilePicture;

    public Answer() {
    }

    public Answer(String username, String body, String pic,String name,String date) {
        this.username = username;
        this.body = body;
        this.pic = pic;
        this.name=name;
        this.date=date;
    }

    public void setId(String id) {
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public String getBody(){
            return body;
        }

    public String getUsername() {return username; }

    public String getName() {return name; }

    public String getPic(){return pic;}

    public String getDate(){return date;}

    public void setBody(String body) {
            this.body=body;
        }

}
