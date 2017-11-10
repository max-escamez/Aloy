package com.aloy.aloy.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.aloy.aloy.Util.DataHandler;
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
    private String name;
    private String date;
    @Exclude
    private String cover1;
    @Exclude
    private String cover2;

    public Question() {
    }

    public Question(String username, String pic, String body,String name, String date)  {
        this.username = username;
        this.body = body;
        this.pic = pic;
        this.name=name;
        this.date=date;
    }

    protected Question(Parcel in) {
        id = in.readString();
        username = in.readString();
        body = in.readString();
        pic = in.readString();
        name = in.readString();
        cover1 = in.readString();
        cover2 = in.readString();
    }

    @SuppressWarnings("unused")
    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getBody(){
        return body;
    }

    public String getUsername() {return username; }

    public String getName() {return name; }

    public String getPic(){
        return pic;
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

    public String getDate(){return date;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(body);
        dest.writeString(pic);
        dest.writeString(name);
        dest.writeString(cover1);
        dest.writeString(cover2);


    }
}
