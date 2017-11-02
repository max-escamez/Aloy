package com.aloy.aloy.Util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.aloy.aloy.Adapters.SearchAdapter;
import com.aloy.aloy.Models.MainUser;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Models.SearchResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.client.Response;

/**
 * Created by tldonne on 30/10/2017.
 */

public class SpotifyHandler {

    private String username;
    private SpotifyService service;
    private DataHandler dataHandler;
    private MainUser mainUser;
    SharedPreferenceHelper sharedPreferenceHelper;


    public SpotifyHandler(String token, Context context){
        sharedPreferenceHelper = new SharedPreferenceHelper(context);
        SpotifyApi api = new SpotifyApi();
        api.setAccessToken(token);
        this.service = api.getService();
        dataHandler = new DataHandler(context);
    }

    public SpotifyHandler(SpotifyService service, Context context) {
        this.service = service;
        dataHandler = new DataHandler(context);
    }

    public void createMainUser() {
        service.getMe(new SpotifyCallback<UserPrivate>() {
            @Override
            public void success(UserPrivate u, Response response) {
                sharedPreferenceHelper.saveCurrentUserId(u.id);
                sharedPreferenceHelper.saveProfilePicture(u.images.get(0).url);
                mainUser = new MainUser(u.id, u.images.get(0).url);
                dataHandler.saveUser(mainUser);
            }
            @Override
            public void failure(SpotifyError error) {
                Log.i("Error", error.toString());
            }

        });

    }

    public void setUsername(String arg) {
        username = arg;
    }

    public String getUsername() {
        return username;
    }

    public void bindTrack(String query, final SearchAdapter.ViewHolder holder, final int position, final Context context) {
        final ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();
        service.searchTracks(query, new SpotifyCallback<TracksPager>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.e("Tracks", "Could not get tracks");
            }
            @Override
            public void success(TracksPager p, Response response) {
                Pager<Track> trackPager = p.tracks;
                List<Track> trackList = trackPager.items;
                Track song = trackList.get(position);
                AlbumSimple album = song.album;
                List<Image> imageList = album.images;
                String image_url = imageList.get(0).url;
                //Picasso.with(getContext()).load(image_url).into(cover);
                //link = song.uri;
                SearchResult result = new SearchResult(image_url,song.name,album.name);
                holder.primaryText.setText(result.getPrimaryText());
                holder.secondaryText.setText(result.getSecondaryText());
                Picasso.with(context).load(image_url).into(holder.cover);



            }

        });
        //System.out.println(" +++++++++++++++++ : " +searchResults.get(1).getPrimaryText());

    }


    /*public void createQuestion(final String body) {


        service.getMe(new SpotifyCallback<UserPrivate>() {
                          @Override
                          public void success(UserPrivate u, Response response) {
                              Question question = new Question(u.id,body);
                              dataHandler.saveQuestion(question);
                          }
                          @Override
                          public void failure(SpotifyError error) {
                              Log.i("Error", error.toString());
                          }
                      }
        );

    }*/

}
