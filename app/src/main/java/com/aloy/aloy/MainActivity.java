package com.aloy.aloy;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.aloy.aloy.Adapters.BottomBarAdapter;
import com.aloy.aloy.Fragments.Feed;
import com.aloy.aloy.Fragments.Inbox;
import com.aloy.aloy.Fragments.Interests;
import com.aloy.aloy.Fragments.Profile;
import com.aloy.aloy.Util.CredentialsHandler;
import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.NoSwipePager;
import com.aloy.aloy.Util.SpotifyHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.AlbumsPager;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.client.Response;


/**
 * Created by Max on 27/09/2017.
 */

public class MainActivity extends AppCompatActivity {

    static final String EXTRA_TOKEN = "EXTRA_TOKEN";
    static final String EXTRA_EXPIRES_AT = "EXTRA_EXPIRES_AT";
    private static final String TAG = LoginActivity.class.getSimpleName();
    static final int profileTabId = 3;
    private static DataHandler dataHandler;
    private static SpotifyHandler spotifyHandler;
    private static FirebaseDatabase database;
    private NoSwipePager viewPager;
    private BottomBarAdapter pagerAdapter;
    private BottomBar bottomBar;
    private int previousTab;
    public static SpotifyService service;
    private static boolean countdownIsRunning;
    private FirebaseAuth mAuth;


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

    public static DataHandler getDataHandler(){
        return dataHandler;
    }

    public static SpotifyHandler getSpotifyHandler() { return spotifyHandler;}

    @Override
    public void onBackPressed() {
        if (previousTab!=viewPager.getCurrentItem()) {
            viewPager.setCurrentItem(previousTab, false);
            bottomBar.selectTabAtPosition(previousTab,true);

        }else{
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String token = intent.getStringExtra(EXTRA_TOKEN);
        long expiresAt=intent.getLongExtra(EXTRA_EXPIRES_AT,3600);
        SpotifyApi api = new SpotifyApi();
        api.setAccessToken(token);
        SpotifyService spotify = api.getService();
        service = api.getService();
        dataHandler = new DataHandler(this);
        spotifyHandler = new SpotifyHandler(service, this);

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

        if(!countdownIsRunning) {
            new CountDownTimer(expiresAt-System.currentTimeMillis()-30000, 1000) {

                public void onTick(long millisUntilFinished) {
                    //Log.i("seconds remaining", "" + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    Log.i("Token", "Refresh");
                    try {
                        new LoginActivity.refreshToAccess().execute().get();
                        /*mAuth.signInWithCustomToken(LoginActivity.firebase_token)
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d("", "signInWithCustomToken:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w("", "signInWithCustomToken:failure", task.getException());
                                        }
                                    }
                                });*/

                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    CredentialsHandler.setAccessToken(getApplicationContext(), LoginActivity.access_token, 3600, TimeUnit.SECONDS);
                    this.start();
                }
            }.start();
            countdownIsRunning = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}