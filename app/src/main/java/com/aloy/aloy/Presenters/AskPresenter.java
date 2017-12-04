package com.aloy.aloy.Presenters;

import android.content.Context;

import com.aloy.aloy.Contracts.AskContract;
import com.aloy.aloy.Models.Answer;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.SharedPreferenceHelper;
import com.aloy.aloy.Util.SpotifyHandler;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by tldonne on 29/10/2017.
 */

public class AskPresenter implements AskContract.Presenter {

    private AskContract.View askView;
    private DataHandler dataHandler;
    private SpotifyHandler spotifyHandler;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private LinkedHashMap<String,Track> tracksSelected;
    private HashMap artistsSelected;
    private HashMap albumsSelected;
    private HashMap genreSelected;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date;

    public AskPresenter(AskContract.View askView, DataHandler dataHandler, SpotifyHandler spotifyHandler, Context context) {
        sharedPreferenceHelper = new SharedPreferenceHelper(context);
        this.dataHandler = dataHandler;
        this.spotifyHandler = spotifyHandler;
        this.askView = askView;
        tracksSelected = new LinkedHashMap<>();
        albumsSelected = new HashMap();
        artistsSelected = new HashMap();
        genreSelected = new HashMap();
    }


    @Override
    public void createAnswer(String body, String questionID) {
        String name=sharedPreferenceHelper.getCurrentUserName();
        String id=sharedPreferenceHelper.getCurrentUserId();
        DatabaseReference answerRef = dataHandler.pushAnswerRef(questionID);
        //String answerId = UUID.randomUUID().toString();
        date=new Date();
        Answer newAnswer = new Answer(id,body,sharedPreferenceHelper.getProfilePicture(),name,(dateFormat.format(date)),answerRef.getKey());
        dataHandler.saveAnswer(newAnswer, questionID,answerRef, tracksSelected, artistsSelected, albumsSelected,genreSelected);
    }
    @Override
    public void createQuestion(String body) {
        String name=sharedPreferenceHelper.getCurrentUserName();
        String id=sharedPreferenceHelper.getCurrentUserId();
        date=new Date();
        DatabaseReference myRef = dataHandler.pushQuestionRef();
        //String url = dataHandler.getUrl(sharedPreferenceHelper.getProfilePicture());
        Question newQuestion = new Question(id,sharedPreferenceHelper.getProfilePicture(),body,name,(dateFormat.format(date)),myRef.getKey());
        dataHandler.saveQuestion(newQuestion,tracksSelected,artistsSelected,albumsSelected,genreSelected,myRef);
    }

    @Override
    public void addTrack(Track track) {
        tracksSelected.put(track.id,track);
    }

    @Override
    public void removeTrack(Track track) {
        tracksSelected.remove(track.id);
    }

    @Override
    public HashMap getTracks(){
        return this.tracksSelected;
    }

    @Override
    public void addArtist(Artist artist) { artistsSelected.put(artist.id,artist);}

    @Override
    public void removeArtist(Artist artist) { artistsSelected.remove(artist.id); }

    @Override
    public HashMap getArtists() { return this.artistsSelected ; }

    @Override
    public void addAlbum(Album album) { albumsSelected.put(album.id,album); }

    @Override
    public void removeAlbum(Album album) { albumsSelected.remove(album.id); }

    @Override
    public HashMap getAlbums() { return this.albumsSelected; }

    @Override
    public void addGenre(String name,String url){genreSelected.put(name,url);}

    @Override
    public void removeGenre(String name){genreSelected.remove(name);}

    @Override
    public HashMap getGenres(){return this.genreSelected;}

    @Override
    public void clearGenres(){this.genreSelected.clear();}

    @Override
    public void clearItems(String type){
        switch (type) {
            case "track" :
                this.tracksSelected.clear();
                break;
            case "artist" :
                this.artistsSelected.clear();
                break;
            case "album" :
                this.albumsSelected.clear();
                break;
        }
    }







}
