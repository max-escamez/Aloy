package com.aloy.aloy.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tldonne on 29/10/2017.
 */

public class Question implements Parcelable {
    private String id;
    private String username;
    private String body;
    private String pic;
    @Exclude
    private String cover1;
    @Exclude
    private String cover2;

    public Question() {
    }

    public Question(String username, String pic, String body)  {
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

    public String getCover1() {
        return cover1;
    }

    public String getCover2() {
        return cover2;
    }

    public void setCover1(String url){
        cover1=url;
    }

    public void setCover2(String url){
        cover2=url;
    }

    public void setId(String id) {
        this.id=id;
    }

    public String getId() {
        return id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(pic);
        dest.writeString(body);

    }
}
