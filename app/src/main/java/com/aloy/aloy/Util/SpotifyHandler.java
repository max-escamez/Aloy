package com.aloy.aloy.Util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aloy.aloy.Adapters.SearchAdapter;
import com.aloy.aloy.Contracts.SearchContract;
import com.aloy.aloy.Models.MainUser;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Models.SearchResult;
import com.aloy.aloy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.Albums;
import kaaes.spotify.webapi.android.models.AlbumsPager;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.SavedTrack;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.RetrofitError;
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


    public SpotifyHandler(String token, Context context) {
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


    public void getMyInfo() {
        service.getMe(new SpotifyCallback<UserPrivate>() {
            @Override
            public void success(UserPrivate u, Response response) {
                sharedPreferenceHelper.saveCurrentUserId(u.id);
                sharedPreferenceHelper.saveName(u.display_name);
                sharedPreferenceHelper.saveProfilePicture(u.images.get(0).url);
            }

            @Override
            public void failure(SpotifyError error) {
                Log.i("Error", error.toString());
            }

        });
    }

    public void createMainUser() {
        mainUser = new MainUser(sharedPreferenceHelper.getCurrentUserId(), sharedPreferenceHelper.getProfilePicture());
        dataHandler.saveUser(mainUser);
    }



    public void bindTrack(String query, final SearchAdapter.ViewHolder holder, final int position, final Context context) {
        service.searchTracks(query, new SpotifyCallback<TracksPager>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.e("Tracks", "Could not get tracks");
            }
            @Override
            public void success(TracksPager p, Response response) {
                Track track = p.tracks.items.get(position);
                SearchResult result = new SearchResult(track.album.images.get(0).url, track.name, track.artists.get(0).name);
                holder.primaryText.setText(result.getPrimaryText());
                holder.secondaryText.setText(result.getSecondaryText());
                Picasso.with(context).load(track.album.images.get(0).url).into(holder.cover);

            }

        });
    }

    public void bindArtist(String query, final SearchAdapter.ViewHolder holder, final int position, final Context context) {
        service.searchArtists(query, new SpotifyCallback<ArtistsPager>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.e("Artists", "Could not get artists");
            }
            @Override
            public void success(ArtistsPager artistsPager, Response response) {
                Artist artist = artistsPager.artists.items.get(position);
                holder.primaryText.setText(artist.name);
                holder.secondaryText.setText(artist.followers.total + " followers");
                if (artist.images.size()!=0)
                    Picasso.with(context).load(artist.images.get(0).url).into(holder.cover);

            }
        });
    }




    public void bindAlbum(String query, final SearchAdapter.ViewHolder holder, final int position, final Context context) {
        service.searchAlbums(query, new SpotifyCallback<AlbumsPager>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.e("Albums", "Could not get albums");
            }

            @Override
            public void success(AlbumsPager albumsPager, Response response) {
                AlbumSimple album = albumsPager.albums.items.get(position);
                holder.primaryText.setText(album.name);

                service.getAlbum(album.id, new SpotifyCallback<Album>() {
                    @Override
                    public void failure(SpotifyError spotifyError) {
                        Log.e("Albums", "Could not get albums");
                    }

                    @Override
                    public void success(Album album2, Response response) {
                        holder.secondaryText.setText(album2.artists.get(0).name);

                    }
                });

                Picasso.with(context).load(album.images.get(0).url).into(holder.cover);
            }
        });
    }




    public void addTrack(final int position, String query, final SearchContract.View searchView, final boolean add) {
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
                if (add == true ) {
                    searchView.getAsk().getAskPresenter().addTrack(song);
                }
                else
                    searchView.getAsk().getAskPresenter().removeTrack(song);
                searchView.updateCount("track");
            }
        });
    }

    public void addArtist(final int position, String query, final SearchContract.View searchView, final boolean add) {
        service.searchArtists(query, new SpotifyCallback<ArtistsPager>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.e("Artists", "Could not get artists");
            }
            @Override
            public void success(ArtistsPager artists, Response response) {
                Artist artist = artists.artists.items.get(position);
                if (add == true ) {
                    searchView.getAsk().getAskPresenter().addArtist(artist);
                }
                else
                    searchView.getAsk().getAskPresenter().removeArtist(artist);
                searchView.updateCount("artist");

            }
        });
    }

    public void addAlbum(final int position, String query, final SearchContract.View searchView, final boolean add) {
        service.searchAlbums(query, new SpotifyCallback<AlbumsPager>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.e("Albums", "Could not get albums");
            }
            @Override
            public void success(AlbumsPager albumsPager, Response response) {
                AlbumSimple album = albumsPager.albums.items.get(position);
                if (add == true ) {
                    searchView.getAsk().getAskPresenter().addAlbum(album);
                }
                else
                    searchView.getAsk().getAskPresenter().removeAlbum(album);
                searchView.updateCount("album");

            }
        });
    }

}



