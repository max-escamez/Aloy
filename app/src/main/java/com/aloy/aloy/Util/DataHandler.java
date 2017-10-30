package com.aloy.aloy.Util;

import com.aloy.aloy.Models.Question;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by tldonne on 29/10/2017.
 */

public class DataHandler {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("questions");

    public void saveQuestion(Question question) {
        myRef.push().setValue(question);

    }
}
