package com.aloy.aloy.Presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.aloy.aloy.Contracts.AskContract;
import com.aloy.aloy.Fragments.Ask;
import com.aloy.aloy.Models.Answer;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.SharedPreferenceHelper;
import com.aloy.aloy.Util.SpotifyHandler;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.client.Response;

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

    public AskPresenter(AskContract.View askView, DataHandler dataHandler, SpotifyHandler spotifyHandler, Context context) {
        sharedPreferenceHelper = new SharedPreferenceHelper(context);
        this.dataHandler = dataHandler;
        this.spotifyHandler = spotifyHandler;
        this.askView = askView;
        tracksSelected = new LinkedHashMap<>();
        albumsSelected = new HashMap();
        artistsSelected = new HashMap();
    }


    @Override
    public void createAnswer(String body, String questionID) {
        Answer newAnswer = new Answer(sharedPreferenceHelper.getCurrentUserId(),body,sharedPreferenceHelper.getProfilePicture());
        dataHandler.saveAnswer(newAnswer, questionID, tracksSelected, artistsSelected, albumsSelected);
    }
    @Override
    public void createQuestion(String body) {
        Question newQuestion = new Question(sharedPreferenceHelper.getCurrentUserId(),sharedPreferenceHelper.getProfilePicture(),body);
        if (tracksSelected.size()>=2) {
            newQuestion.setCover1(new ArrayList<>(tracksSelected.values()).get(0).album.images.get(0).url );
            newQuestion.setCover2(new ArrayList<>(tracksSelected.values()).get(1).album.images.get(0).url );
        }
        dataHandler.saveQuestion(newQuestion,tracksSelected,artistsSelected,albumsSelected);
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
    public void addAlbum(AlbumSimple album) { albumsSelected.put(album.id,album); }

    @Override
    public void removeAlbum(AlbumSimple album) { albumsSelected.remove(album.id); }

    @Override
    public HashMap getAlbums() { return this.albumsSelected; }


}
