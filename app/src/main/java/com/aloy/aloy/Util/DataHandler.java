package com.aloy.aloy.Util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.aloy.aloy.Adapters.AnswersAdapter;
import com.aloy.aloy.Adapters.SearchAdapter;
import com.aloy.aloy.Contracts.SearchContract;
import com.aloy.aloy.LoginActivity;
import com.aloy.aloy.Models.Answer;
import com.aloy.aloy.Models.MainUser;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Presenters.QuestionDetailsPresenter;
import com.aloy.aloy.Presenters.SearchPresenter;
import com.aloy.aloy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;

import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.CategoriesPager;
import kaaes.spotify.webapi.android.models.Track;

import static android.content.ContentValues.TAG;

/**
 * Created by tldonne on 29/10/2017.
 */

public class DataHandler {

    private DatabaseReference refQuestionFeed;
    private DatabaseReference refUser;
    private DatabaseReference refCategories;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private String profilePicture;



    public DataHandler(Context context){
        sharedPreferenceHelper = new SharedPreferenceHelper(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        refQuestionFeed = database.getReference("questions");
        refUser = database.getReference("users");
        refCategories = database.getReference("categories");
    }

    public DatabaseReference getRefQuestionFeed(){
        return refQuestionFeed;
    }

    public DatabaseReference getRefAnswers(String questionId) {
        //return database.getReference(questionId);
        return refQuestionFeed.child(questionId).child("answers");
    }

    public void saveQuestion(final Question question, final HashMap<String,Track> tracks, final HashMap<String,Artist> artists, final HashMap<String,AlbumSimple> albums, final HashMap<String,String> genres) {
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
                for (HashMap.Entry<String, String> genre : genres.entrySet()) {
                    databaseReference.child("genres").child(genre.getKey()).child("cover").setValue(genre.getValue());
                }
                refUser.child(sharedPreferenceHelper.getCurrentUserId()).child("questions").child(databaseReference.getKey()).setValue("true");
            }
        });
    }

    public void saveUser(MainUser user){
        refUser.child(sharedPreferenceHelper.getCurrentUserId()).setValue(user);

    }

    public void saveAnswer(Answer answer, final String questionID, final LinkedHashMap<String, Track> tracksSelected, final HashMap<String,Artist> artistsSelected, final HashMap<String,AlbumSimple> albumsSelected, final HashMap<String,String> genreSelected) {
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
                for (HashMap.Entry<String, String> genre : genreSelected.entrySet()) {
                    databaseReference.child("genres").child(genre.getKey()).child("cover").setValue(genre.getValue());
                }
                refUser.child(sharedPreferenceHelper.getCurrentUserId()).child("answers").child(questionID).setValue(databaseReference.getKey());
            }
        });
    }

    public void upvote(DatabaseReference questionId,String answerId) {
        //refQuestionFeed.runTransaction(new Transaction.Handler() {
           // @Override
            //public Transaction.Result doTransaction(MutableData mutableData) {
                //Post p = mutableData.getValue(Post.class);
                final DatabaseReference mUpvoterReference = questionId.child(answerId).child("upvotes").child("users").child(sharedPreferenceHelper.getCurrentUserId());
                final DatabaseReference mUpvotesReference = questionId.child(answerId).child("upvotes").child("number");
                mUpvotesReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot votes) {
                        mUpvoterReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot voter) {
                                if (voter.exists()) {
                                    //Log.i("votes",""+(int)votes.getValue());
                                    mUpvotesReference.setValue(votes.getValue(Integer.class)-1);
                                    mUpvoterReference.removeValue();

                                }else{
                                    mUpvoterReference.setValue("voted");
                                    if (votes.exists()) {
                                        mUpvotesReference.setValue(votes.getValue(Integer.class)+1);
                                    }else{
                                        mUpvotesReference.setValue(1);
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


    public void bindGenre(final SearchAdapter.ViewHolder holder, final int position, final Context context){
        refCategories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    holder.primaryText.setText(dataSnapshot.child(String.valueOf(position)).child("name").getValue().toString());
                    holder.secondaryText.setVisibility(View.GONE);
                    Picasso.with(context).load(dataSnapshot.child(String.valueOf(position)).child("pic").getValue().toString()).into(holder.cover);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "addListenerForSingleValueEvent:failure", databaseError.toException());
            }
        });
    }

    public void addGenre(final int position, final SearchContract.View searchView, final boolean add){
        refCategories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (add) {
                    searchView.getAsk().getAskPresenter().addGenre(dataSnapshot.child(String.valueOf(position)).child("name").getValue().toString(),dataSnapshot.child(String.valueOf(position)).child("pic").getValue().toString());
                }
                else
                    searchView.getAsk().getAskPresenter().removeGenre(dataSnapshot.child(String.valueOf(position)).child("name").getValue().toString());
                searchView.updateCount("genre");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "addListenerForSingleValueEvent:failure", databaseError.toException());
            }
        });
    }

    public void follow(final String questionId){
        final DatabaseReference mFollowersReference = refQuestionFeed.child(questionId).child("following").child("users").child(sharedPreferenceHelper.getCurrentUserId());
        final DatabaseReference mFollowingReference = refQuestionFeed.child(questionId).child("following").child("number");
        final DatabaseReference mUserFollow = refUser.child(sharedPreferenceHelper.getCurrentUserId()).child("following");
        mFollowingReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot following) {
                mFollowersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot follower) {
                        if (follower.exists()) {
                            //unfollow
                            refQuestionFeed.child(questionId).child("following").child("number").setValue(following.getValue(Integer.class)-1);
                            refQuestionFeed.child(questionId).child("following").child("users").child(sharedPreferenceHelper.getCurrentUserId()).removeValue();
                            mUserFollow.child(questionId).removeValue();

                        }else{
                            refQuestionFeed.child(questionId).child("following").child("users").child(sharedPreferenceHelper.getCurrentUserId()).setValue("is following");
                            mUserFollow.child(questionId).setValue("true");

                            if (following.exists()) {
                                refQuestionFeed.child(questionId).child("following").child("number").setValue(following.getValue(Integer.class)+1);
                            }else{
                                refQuestionFeed.child(questionId).child("following").child("number").setValue(1);
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

    }

    public void getUpvote(final DatabaseReference questionRef, final String answerId, final AnswersAdapter.ViewHolder holder) {
        final DatabaseReference mUpvoterReference = questionRef.child(answerId).child("upvotes").child("users").child(sharedPreferenceHelper.getCurrentUserId());
        final DatabaseReference mUpvotesReference = questionRef.child(answerId).child("upvotes").child("number");
        mUpvotesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot votes) {
                mUpvoterReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot voter) {
                        if (voter.exists()) {
                            //Log.i("votes",""+(int)votes.getValue());
                            holder.upvote.setImageResource(R.drawable.ic_favorite);

                        }else{
                            holder.upvote.setImageResource(R.drawable.ic_favorite_border);
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
    }

    public void updateURL(final String username){
        final DatabaseReference picReference = refUser.child(username).child("pic");
        picReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot userURL) {
                setProfilePicture(userURL.getValue().toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG,"addListenerForSingleValueEvent:failure",databaseError.toException());
            }

        });

    }

    public void setProfilePicture(String url){
        profilePicture=url;
    }

    public String getProfilePicture(){
        return profilePicture;
    }

    public void updateData(String username){
        refUser.child(username).child("pic").setValue(sharedPreferenceHelper.getProfilePicture());
    }

}
