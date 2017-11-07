package com.aloy.aloy.Util;

import android.content.Context;
import android.util.Log;

import com.aloy.aloy.LoginActivity;
import com.aloy.aloy.Models.Answer;
import com.aloy.aloy.Models.MainUser;
import com.aloy.aloy.Models.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedHashMap;

import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

import static android.content.ContentValues.TAG;

/**
 * Created by tldonne on 29/10/2017.
 */

public class DataHandler {

    private DatabaseReference refQuestionFeed;
    private DatabaseReference refUser;
    private SharedPreferenceHelper sharedPreferenceHelper;


    public DataHandler(Context context){
        sharedPreferenceHelper = new SharedPreferenceHelper(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        refQuestionFeed = database.getReference("questions");
        refUser = database.getReference("users");
    }

    public void saveQuestion(final Question question, final HashMap<String,Track> tracks, final HashMap<String,Artist> artists, final HashMap<String,AlbumSimple> albums) {
        refQuestionFeed.push().setValue(question,new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                databaseReference.child("id").setValue(databaseReference.getKey());

                for (HashMap.Entry<String, Track> entry : tracks.entrySet()) {
                    //refQuestionFeed.child(databaseReference.getKey()).child("tracks").child(entry.getKey()).setValue(entry.getValue());
                    refQuestionFeed.child(databaseReference.getKey()).child("tracks").child(entry.getKey()).child("name").setValue(entry.getValue().name);
                    refQuestionFeed.child(databaseReference.getKey()).child("tracks").child(entry.getKey()).child("uri").setValue(entry.getValue().uri);
                    refQuestionFeed.child(databaseReference.getKey()).child("tracks").child(entry.getKey()).child("artist").setValue(entry.getValue().artists.get(0).name);
                    refQuestionFeed.child(databaseReference.getKey()).child("tracks").child(entry.getKey()).child("cover").setValue(entry.getValue().album.images.get(0).url);
                }
                for (HashMap.Entry<String, Artist> artist : artists.entrySet()) {
                    refQuestionFeed.child(databaseReference.getKey()).child("artists").child(artist.getKey()).child("name").setValue(artist.getValue().name);
                    refQuestionFeed.child(databaseReference.getKey()).child("artists").child(artist.getKey()).child("uri").setValue(artist.getValue().uri);
                    refQuestionFeed.child(databaseReference.getKey()).child("artists").child(artist.getKey()).child("cover").setValue(artist.getValue().images.get(0).url);
                }
                for (HashMap.Entry<String, AlbumSimple> album : albums.entrySet()) {
                    refQuestionFeed.child(databaseReference.getKey()).child("albums").child(album.getKey()).child("name").setValue(album.getValue().name);
                    refQuestionFeed.child(databaseReference.getKey()).child("albums").child(album.getKey()).child("uri").setValue(album.getValue().uri);
                    refQuestionFeed.child(databaseReference.getKey()).child("albums").child(album.getKey()).child("cover").setValue(album.getValue().images.get(0).url);
                }
                refUser.child(sharedPreferenceHelper.getCurrentUserId()).child("questions").push().setValue(databaseReference.getKey());
            }
        });
    }

    public void saveUser(MainUser user){
        refUser.child(sharedPreferenceHelper.getCurrentUserId()).setValue(user);

    }

    public void saveAnswer(Answer answer, final String questionID, final LinkedHashMap<String, Track> tracksSelected, final HashMap<String,Artist> artistsSelected, final HashMap<String,AlbumSimple> albumsSelected) {
        refQuestionFeed.child(questionID).child("answers").push().setValue(answer,new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError,
                                   DatabaseReference databaseReference) {
                databaseReference.child("id").setValue(databaseReference.getKey());

                for (HashMap.Entry<String, Track> entry : tracksSelected.entrySet()) {
                    //refQuestionFeed.child(databaseReference.getKey()).child("tracks").child(entry.getKey()).setValue(entry.getValue());
                    databaseReference.child("tracks").child(entry.getKey()).child("name").setValue(entry.getValue().name);
                    databaseReference.child("tracks").child(entry.getKey()).child("uri").setValue(entry.getValue().uri);
                    databaseReference.child("tracks").child(entry.getKey()).child("artist").setValue(entry.getValue().artists.get(0).name);
                    databaseReference.child("tracks").child(entry.getKey()).child("cover").setValue(entry.getValue().album.images.get(0).url);
                }
                for (HashMap.Entry<String, Artist> artist : artistsSelected.entrySet()) {
                    databaseReference.child("artists").child(artist.getKey()).child("name").setValue(artist.getValue().name);
                    databaseReference.child("artists").child(artist.getKey()).child("uri").setValue(artist.getValue().uri);
                    databaseReference.child("artists").child(artist.getKey()).child("cover").setValue(artist.getValue().images.get(0).url);
                }
                for (HashMap.Entry<String, AlbumSimple> album : albumsSelected.entrySet()) {
                    databaseReference.child("albums").child(album.getKey()).child("name").setValue(album.getValue().name);
                    databaseReference.child("albums").child(album.getKey()).child("uri").setValue(album.getValue().uri);
                    databaseReference.child("albums").child(album.getKey()).child("cover").setValue(album.getValue().images.get(0).url);
                }

                refUser.child(sharedPreferenceHelper.getCurrentUserId()).child("answers").child(questionID).setValue(databaseReference.getKey());
            }
        });
    }

    public void upvote(final String questionId) {
        //refQuestionFeed.runTransaction(new Transaction.Handler() {
           // @Override
            //public Transaction.Result doTransaction(MutableData mutableData) {
                //Post p = mutableData.getValue(Post.class);
                final DatabaseReference mUpvoterReference = refQuestionFeed.child(questionId).child("upvotes").child("users").child(sharedPreferenceHelper.getCurrentUserId());
                final DatabaseReference mUpvotesReference = refQuestionFeed.child(questionId).child("upvotes").child("number");
                mUpvotesReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot votes) {
                        mUpvoterReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot voter) {
                                if (voter.exists()) {
                                    //Log.i("votes",""+(int)votes.getValue());
                                    refQuestionFeed.child(questionId).child("upvotes").child("number").setValue(votes.getValue(Integer.class)-1);
                                    refQuestionFeed.child(questionId).child("upvotes").child("users").child(sharedPreferenceHelper.getCurrentUserId()).removeValue();

                                }else{
                                    refQuestionFeed.child(questionId).child("upvotes").child("users").child(sharedPreferenceHelper.getCurrentUserId()).setValue("voted");
                                    if (votes.exists()) {
                                        refQuestionFeed.child(questionId).child("upvotes").child("number").setValue(votes.getValue(Integer.class)+1);
                                    }else{
                                        refQuestionFeed.child(questionId).child("upvotes").child("number").setValue(1);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w(TAG,"addListenerForSingleValueEvent:failure",databaseError.toException());
                            }
                        });
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG,"addListenerForSingleValueEvent:failure",databaseError.toException());
                    }
                });

                //mutableData.setValue(p);
                //return Transaction.success(mutableData);
            //}

            //@Override
            //public void onComplete(DatabaseError databaseError, boolean b,
            //                       DataSnapshot dataSnapshot) {
                // Transaction completed
            //    Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            //}
        //});
    }

}
