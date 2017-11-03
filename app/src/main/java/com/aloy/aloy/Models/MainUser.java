package com.aloy.aloy.Models;

/**
 * Created by tldonne on 30/10/2017.
 */

public class MainUser {

    private String pic;


    public MainUser() {

    }

    public MainUser(String username, String pic) {
        this.pic=pic;
    }


    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic=pic;
    }

}
