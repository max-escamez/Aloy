package com.aloy.aloy.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.MainActivity;
import com.aloy.aloy.R;
import com.aloy.aloy.Util.CredentialsHandler;
import com.aloy.aloy.Util.SharedPreferenceHelper;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.AlbumsPager;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.CategoriesPager;
import kaaes.spotify.webapi.android.models.Category;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import kaaes.spotify.webapi.android.models.UserPrivate;
import kaaes.spotify.webapi.android.models.UserPublic;
import retrofit.client.Response;

public class Profile extends Fragment {

    //SpotifyService service = MainActivity.getService();
    //String username = service.getMe().id;

    private TextView username;
    private com.mikhaellopez.circularimageview.CircularImageView profilePicture;
    private ImageView cover;
    private  String link="";
    private String image_url="";
    private String search_query;
    private EditText search_bar;
    private SharedPreferenceHelper mSharedPreferenceHelper;

    public Profile(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myInflatedView = inflater.inflate(R.layout.fragment_profile, container, false);
        mSharedPreferenceHelper=new SharedPreferenceHelper(getContext());
        //Bundle args = getArguments();
        //String token = args.getString("token");
        String token = CredentialsHandler.getAccessToken(getContext());

        SpotifyApi api = new SpotifyApi();
        api.setAccessToken(token);
        SpotifyService spotify = api.getService();


        username = (TextView) myInflatedView.findViewById(R.id.username);
        profilePicture = (com.mikhaellopez.circularimageview.CircularImageView) myInflatedView.findViewById(R.id.profilePicture);
        cover = (ImageView) myInflatedView.findViewById(R.id.cover);
        search_bar = (EditText) myInflatedView.findViewById(R.id.search_bar);
        search_bar.setVisibility(View.VISIBLE);
        if(mSharedPreferenceHelper.getCurrentUserName().equals("")){
            username.setText(mSharedPreferenceHelper.getCurrentUserId());
        }else{
            username.setText(mSharedPreferenceHelper.getCurrentUserName());
        }
        Picasso.with(getContext()).load(mSharedPreferenceHelper.getProfilePicture()).into(profilePicture);

        search_bar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try {


                        search_query = search_bar.getText().toString();
                        Log.i("Before encoding",search_query);
                        search_query=URLEncoder.encode(search_query,"utf-8");
                        Log.i("After encoding",search_query);


                        search_bar.setText("");
                        SpotifyApi api = new SpotifyApi();
                        api.setAccessToken(CredentialsHandler.getAccessToken(getContext()));
                        SpotifyService spotify = api.getService();

                        /*Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SpotifyApi api = new SpotifyApi();
                                api.setAccessToken(CredentialsHandler.getAccessToken(getContext()));
                                SpotifyService spotify = api.getService();
                                UserPrivate u = spotify.getMe();
                                Log.i("Name",u.id);
                                try {
                                    TracksPager p = spotify.searchTracks(search_query);
                                    Pager<Track> trackPager = p.tracks;
                                    List<Track> trackList = trackPager.items;
                                    for (Track s : trackList) {
                                        Log.i("Song", s.name);
                                    }
                                }catch(IndexOutOfBoundsException e){
                                    e.printStackTrace();
                                }
                            }
                        });
                        t.start();*/


                        spotify.searchTracks(search_query, new SpotifyCallback<TracksPager>() {
                            @Override
                            public void failure(SpotifyError spotifyError) {
                             Log.e("Tracks", "Could not get tracks");
                            }
                            @Override
                            public void success(TracksPager p, Response response) {
                                try {
                                Pager<Track> trackPager = p.tracks;
                                List<Track> trackList = trackPager.items;
                                Track song = trackList.get(0);
                                AlbumSimple album = song.album;
                                List<Image> imageList = album.images;
                                image_url = imageList.get(0).url;

                                    Picasso.with(getContext()).load(image_url).into(cover);
                                    link = song.uri;
                                    cover.setOnClickListener(new View.OnClickListener() {

                                        public void onClick(View arg0) {
                                            Intent viewIntent = new Intent("android.intent.action.VIEW",
                                                    Uri.parse(link));
                                            startActivity(viewIntent);
                                        }
                                    });
                                }catch(IndexOutOfBoundsException e){
                                    e.printStackTrace();

                                }
                            }

                        });

                    } catch (UnsupportedEncodingException | IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });




        return myInflatedView;
    }



}
