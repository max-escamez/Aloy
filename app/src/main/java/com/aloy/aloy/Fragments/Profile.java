package com.aloy.aloy.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aloy.aloy.MainActivity;
import com.aloy.aloy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
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

    public Profile(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myInflatedView = inflater.inflate(R.layout.fragment_profile, container, false);
        Bundle args = getArguments();
        String token = args.getString("token");
        SpotifyApi api = new SpotifyApi();
        api.setAccessToken(token);
        Log.i("Token",token.toString());
        SpotifyService spotify = api.getService();


        username = (TextView) myInflatedView.findViewById(R.id.username);
        profilePicture = (com.mikhaellopez.circularimageview.CircularImageView) myInflatedView.findViewById(R.id.profilePicture);
        spotify.getMe(new SpotifyCallback<UserPrivate>() {
            @Override
            public void success(UserPrivate u, Response response) {
                username.setText(u.id);
                List<Image> images = u.images;
                for (Image image : images) {
                    Picasso.with(getContext()).load(image.url).into(profilePicture);
                }


            }

            @Override
            public void failure(SpotifyError error) {
                Log.i("Error", error.toString());
            }
        });

        return myInflatedView;
    }

}
