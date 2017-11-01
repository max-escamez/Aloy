package com.aloy.aloy.Models;

/**
 * Created by tldonne on 30/10/2017.
 */

public class MainUser {

    private String username;
    private String pic;

    public MainUser() {

    }

    public MainUser(String username, String pic) {
        this.username=username;
        this.pic=pic;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username=username;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic=pic;
    }

}
