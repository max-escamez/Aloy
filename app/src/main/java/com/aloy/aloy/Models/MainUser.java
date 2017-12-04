package com.aloy.aloy.Models;

/**
 * Created by tldonne on 30/10/2017.
 */

public class MainUser {

    private String pic;
    private String name;
    private String id;


    public MainUser() {

    }

    public MainUser(String name, String pic, String id) {
        this.pic=pic;
        this.name=name;
        this.id=id;
    }

    public String getPic() {
        return this.pic;
    }

    public String getName(){return this.name;}

    public String getId(){return this.id;}

}
