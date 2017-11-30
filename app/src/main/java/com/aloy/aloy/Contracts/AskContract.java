package com.aloy.aloy.Contracts;

import android.content.Context;

import java.util.HashMap;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by tldonne on 29/10/2017.
 */

public interface AskContract {

    interface View  {
        void hideAskQuestion();
        void showSearch(String type);
        void hideKeyboardFrom(Context context, android.view.View view);
        void update();

        Presenter getAskPresenter();


    }

    interface Presenter {
        void createQuestion(String body);
        void createAnswer(String body, String questionID);

        void addTrack(Track track);
        void removeTrack(Track track);
        HashMap getTracks();

        void addArtist(Artist artist);
        void removeArtist(Artist artist);
        HashMap getArtists();

        void addAlbum(Album album);
        void removeAlbum(Album album);
        HashMap getAlbums();

        void addGenre(String name, String url);
        void removeGenre(String name);
        HashMap getGenres();
        void clearGenres();
        void clearItems(String type);

    }
}
