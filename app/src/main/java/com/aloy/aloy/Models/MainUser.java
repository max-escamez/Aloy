package com.aloy.aloy.Models;

/**
 * Created by tldonne on 30/10/2017.
 */

public class MainUser {

    private String pic;
    private String name;


    public MainUser() {

    }

    public MainUser(String name, String pic) {
        this.pic=pic;
        this.name=name;
    }

    public String getPic() {
        return this.pic;
    }

    public String getName(){return this.name;}

}
