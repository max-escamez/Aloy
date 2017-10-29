package com.aloy.aloy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


import com.aloy.aloy.Adapters.BottomBarAdapter;
import com.aloy.aloy.Fragments.Feed;
import com.aloy.aloy.Fragments.Inbox;
import com.aloy.aloy.Fragments.Interests;
import com.aloy.aloy.Fragments.Profile;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


/**
 * Created by Max on 27/09/2017.
 */

public class MainActivity extends AppCompatActivity {

    static final String EXTRA_TOKEN = "EXTRA_TOKEN";
    static final int profileTabId = 3;
    private NoSwipePager viewPager;
    private BottomBarAdapter pagerAdapter;
    private BottomBar bottomBar;
    private int previousTab;
    public static SpotifyService service;

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    /*public void switchToProfile() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.profile_layout, new Profile()).commit();}*/


    private void setupViewPager(String token) {
        Bundle args = new Bundle();
        args.putString("token",token );

        viewPager = (NoSwipePager) findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);

        Fragment profile = new Profile();
        profile.setArguments(args);

        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new Feed());
        pagerAdapter.addFragments(new Interests());
        pagerAdapter.addFragments(new Inbox());
        pagerAdapter.addFragments(profile);

        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {

        if (previousTab!=viewPager.getCurrentItem()) {
            viewPager.setCurrentItem(previousTab, false);
            bottomBar.selectTabAtPosition(previousTab,true);

        }else{
            finish();
        }

    }




    public static SpotifyService getService() {
        return service;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String token = intent.getStringExtra(EXTRA_TOKEN);
        SpotifyApi api = new SpotifyApi();
        api.setAccessToken(token);
        Log.i("Token",token.toString());
        SpotifyService spotify = api.getService();
        service = api.getService();



        setupViewPager(token);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        //viewPager.setCurrentItem(profileTabId);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                previousTab = viewPager.getCurrentItem();
                viewPager.setCurrentItem(bottomBar.findPositionForTabWithId(tabId));

            }

        });


         /*       spotify.getMe(new SpotifyCallback<UserPrivate>() {
                    @Override
                    public void success(UserPrivate u, Response response) {
                        Log.i("id",""+u.id);

                    }

                    @Override
                    public void failure(SpotifyError error) {
                        Log.i("Error", error.toString());
                    }
                });



        spotify.getMe(new SpotifyCallback<UserPrivate>() {
            @Override
            public void success(UserPrivate u, Response response) {
                Log.i("id",""+u.id);
                List<Image> images = u.images;
                for (Image image : images) {
                    System.out.println(image.url);
                }
                Log.i("","\n");
            }

            @Override
            public void failure(SpotifyError error) {
                Log.i("Error", error.toString());
            }
        });

       spotify.getUser("emthma",new SpotifyCallback<UserPublic>() {
            @Override
            public void success(UserPublic u, Response response) {
                Log.i("id",""+u.id);
                List<Image> images = u.images;
                for (Image image : images) {
                    System.out.println(image.url);
                }
                Log.i("","\n");
            }

            @Override
            public void failure(SpotifyError error) {
                Log.i("Error", error.toString());
            }
        });


        //Search ONLY by tracks
        spotify.searchTracks("Wagon Wheel",new SpotifyCallback<TracksPager>(){
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.e("Tracks", "Could not get tracks");

            }

            @Override
            public void success(TracksPager p, Response response) {
                Log.i("Search by tracks","Wagon Wheel");
                Log.i("","\n");

                Pager<Track> trackPager = p.tracks;
                List<Track> trackList = trackPager.items;
                Log.i("Number of results",""+trackPager.total);
                //Log.i("previous",trackPager.previous);
                Log.i("next",trackPager.next);
                Log.i("Limit",""+trackPager.limit);
                Log.i("","\n");
                for(Track song : trackList){
                    Log.i("Track", song.name);
                    List<ArtistSimple> artistList = song.artists;
                    AlbumSimple album = song.album;
                    List<Image> imageList = album.images;
                    for(ArtistSimple artist : artistList){
                        Log.i("Artist",artist.name);
                    }
                    Log.i("Album",album.name);
                    for(Image image : imageList){
                        Log.i("Album Cover",image.url);
                    }
                    Log.i("Popularity", ""+song.popularity);
                    Log.i("Type", song.type);
                    Log.i("Uri", song.uri);
                    Log.i("href", song.href);
                    Log.i("","\n");
                }
                Log.i("","\n");


            }
        });

        //Search ONLY by artists
        spotify.searchArtists("Darius Rucker",new SpotifyCallback<ArtistsPager>(){
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.e("Artists", "Could not get playlists");

            }

            @Override
            public void success(ArtistsPager p, Response response) {
                Log.i("Search by artists","The Commitments");
                Log.i("","\n");
                Pager<Artist> artistPager = p.artists;
                List<Artist> artistList = artistPager.items;
                Log.i("Number of results",""+artistPager.total);
                //Log.i("previous",artistPager.previous);
                //Log.i("next",artistPager.next);
                Log.i("Limit",""+artistPager.limit);
                Log.i("","\n");
                for(Artist artist : artistList){
                    List<String> genresList = artist.genres;
                    List<Image> imageList = artist.images;
                    Log.i("Artist", artist.name);
                    for(String genre :genresList){
                        Log.i("Genre",genre);
                    }
                    for(Image image : imageList){
                        Log.i("Image",image.url);
                    }
                    Log.i("Type", artist.type);
                    Log.i("Popularity", ""+artist.popularity);
                    Log.i("Uri", artist.uri);
                    Log.i("href", artist.href);
                    Log.i("","\n");
                }
                Log.i("","\n");

            }
        });

        //Search ONLY by Albums
        spotify.searchAlbums("Division Bell",new SpotifyCallback<AlbumsPager>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.e("Albums", "Could not get albums");

            }

            @Override
            public void success(AlbumsPager p, Response response) {
                Log.i("Search by albums","Division Bell");
                Log.i("","\n");
                Pager<AlbumSimple> albumPager = p.albums;
                List<AlbumSimple> albumList = albumPager.items;
                Log.i("Number of results",""+albumPager.total);
                //Log.i("previous",albumPager.previous);
                //Log.i("next",albumPager.next);
                Log.i("Limit",""+albumPager.limit);
                Log.i("","\n");
                for(AlbumSimple album : albumList){
                    List<Image> albumCovers =album.images;
                    Log.i("Album", album.name);
                    for(Image i: albumCovers){
                        Log.i("Image",i.url);
                    }
                    Log.i("Type", album.type);
                    Log.i("Album_Type", album.album_type);
                    Log.i("Uri", album.uri);
                    Log.i("href", album.href);
                    Log.i("","\n");

                }
                Log.i("","\n");


            }
        });


        Map<String, Object> options = new HashMap();
        options.put(SpotifyService.COUNTRY,"US");
        spotify.getCategories(options, new SpotifyCallback<CategoriesPager>(){
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.e("Categories", "Could not get categories");

            }

            @Override
            public void success(CategoriesPager p, Response response) {
                Log.i("","\n");
                Pager<Category> categoryPager= p.categories;
                List<Category>categoryList=categoryPager.items;
                for(Category category :categoryList){
                    List<Image> imageList=category.icons;
                    Log.i("Category",category.name);
                    Log.i("Category id",category.id);
                    for(Image image : imageList){
                        Log.i("Image",image.url);
                    }
                    Log.i("href",category.href);
                    Log.i("","\n");
                }
                Log.i("","\n");
            }

        });


        spotify.getCategory("jazz", options, new SpotifyCallback<Category>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.e("Category", "Could not get category");

            }

            @Override
            public void success(Category p, Response response) {

                Log.i("get categories","Jazz");
                Log.i("","\n");
                Log.i("Category",p.name);
                List<Image> imageList = p.icons;
                for(Image image : imageList){
                    Log.i("Image",image.url);
                }
                Log.i("href",p.href);
                Log.i("","\n");
            }
        });*/

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}