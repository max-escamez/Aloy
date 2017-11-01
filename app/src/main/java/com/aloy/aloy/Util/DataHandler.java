package com.aloy.aloy.Util;

import android.content.Context;
import android.util.Log;

import com.aloy.aloy.Models.MainUser;
import com.aloy.aloy.Models.Question;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by tldonne on 29/10/2017.
 */

public class DataHandler {

    private FirebaseDatabase database;
    private DatabaseReference refQuestionFeed;
    private DatabaseReference refUser;
    SharedPreferenceHelper sharedPreferenceHelper;


    public DataHandler(Context context){
        sharedPreferenceHelper = new SharedPreferenceHelper(context);
        database = FirebaseDatabase.getInstance();
        refQuestionFeed = database.getReference("questions");
        refUser = database.getReference("users");

    }

    public void saveQuestion(Question question) {
        refQuestionFeed.push().setValue(question,new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError,
                                   DatabaseReference databaseReference) {
                //1)sharedPreferenceHelper.getFirebaseID()
                //2)databaseReference.getKey()
                //push inside User's key, that key.
                refUser.child(sharedPreferenceHelper.getFirebaseID()).child("questions").push().setValue(databaseReference.getKey());

            }
        });

        //update the User with the question key
    }

    public void saveUser(MainUser user){
        refUser.push().setValue(user,new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError,
                                   DatabaseReference databaseReference) {
                sharedPreferenceHelper.saveFirebaseID(databaseReference.getKey());


            }
        });

    }

}
