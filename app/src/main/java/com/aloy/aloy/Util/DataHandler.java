package com.aloy.aloy.Util;

import android.content.Context;
import android.util.Log;

import com.aloy.aloy.Models.Answer;
import com.aloy.aloy.Models.MainUser;
import com.aloy.aloy.Models.Question;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import kaaes.spotify.webapi.android.models.Track;

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

    public void saveQuestion(Question question, final HashMap<String,Track> tracks) {
        refQuestionFeed.push().setValue(question,new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError,
                                   DatabaseReference databaseReference) {

                for (HashMap.Entry<String, Track> entry : tracks.entrySet()) {
                    //refQuestionFeed.child(databaseReference.getKey()).child("tracks").child(entry.getKey()).setValue(entry.getValue());

                    refQuestionFeed.child(databaseReference.getKey()).child("tracks").child(entry.getKey()).child("name").setValue(entry.getValue().name);
                    refQuestionFeed.child(databaseReference.getKey()).child("tracks").child(entry.getKey()).child("href").setValue(entry.getValue().href);
                    refQuestionFeed.child(databaseReference.getKey()).child("tracks").child(entry.getKey()).child("artist").setValue(entry.getValue().artists.get(0).name);
                    refQuestionFeed.child(databaseReference.getKey()).child("tracks").child(entry.getKey()).child("cover").setValue(entry.getValue().album.images.get(0).url);



                }
                refUser.child(sharedPreferenceHelper.getCurrentUserId()).child("questions").push().setValue(databaseReference.getKey());
            }
        });

    }

    public void saveUser(MainUser user){
        refUser.child(sharedPreferenceHelper.getCurrentUserId()).setValue(user);

    }

    public void saveAnswer(Answer answer, final String questionID) {
        refQuestionFeed.child(questionID).child("answers").push().setValue(answer,new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError,
                                   DatabaseReference databaseReference) {
                refUser.child(sharedPreferenceHelper.getCurrentUserId()).child("answers").push().setValue(databaseReference.getKey());
            }
        });
    }

}
