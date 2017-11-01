package com.aloy.aloy.Util;

import com.aloy.aloy.Models.MainUser;
import com.aloy.aloy.Models.Question;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by tldonne on 29/10/2017.
 */

public class DataHandler {

    private FirebaseDatabase database;
    private DatabaseReference refQuestionFeed;
    private DatabaseReference refUser;

    public DataHandler(){
        database = FirebaseDatabase.getInstance();
        refQuestionFeed = database.getReference("questions");
        refUser = database.getReference("users");

    }

    public void saveQuestion(Question question) {
        refQuestionFeed.push().setValue(question);
    }

    public void saveUser(MainUser user){
        refUser.push().setValue(user);

    }

}
