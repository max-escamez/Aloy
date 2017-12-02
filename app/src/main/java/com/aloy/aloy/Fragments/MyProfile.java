package com.aloy.aloy.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aloy.aloy.Adapters.InboxAdapter;
import com.aloy.aloy.MainActivity;
import com.aloy.aloy.R;
import com.aloy.aloy.Util.AchievementsHandler;
import com.aloy.aloy.Util.SharedPreferenceHelper;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile extends Fragment {

    //SpotifyService service = MainActivity.getService();
    //String username = service.getMe().id;

    private SharedPreferenceHelper mSharedPreferenceHelper;

    public MyProfile(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);

        mSharedPreferenceHelper = new SharedPreferenceHelper(getContext());
        final AchievementsHandler mAchievementsHandler = new AchievementsHandler(getContext(),mSharedPreferenceHelper.getCurrentUserId());
        TextView username = (TextView) profileView.findViewById(R.id.username);
        CircleImageView profilePicture = (CircleImageView) profileView.findViewById(R.id.profilePicture);
        CircleImageView achievement1 = (CircleImageView) profileView.findViewById(R.id.achievement_1);
        CircleImageView achievement2 = (CircleImageView) profileView.findViewById(R.id.achievement_2);
        CircleImageView achievement3 = (CircleImageView) profileView.findViewById(R.id.achievement_3);
        CircleImageView achievement4 = (CircleImageView) profileView.findViewById(R.id.achievement_4);
        CircleImageView achievement5 = (CircleImageView) profileView.findViewById(R.id.achievement_5);
        CircleImageView achievement6 = (CircleImageView) profileView.findViewById(R.id.achievement_6);
        CircleImageView achievement7 = (CircleImageView) profileView.findViewById(R.id.achievement_7);
        CircleImageView achievement8 = (CircleImageView) profileView.findViewById(R.id.achievement_8);
        CircleImageView achievement9 = (CircleImageView) profileView.findViewById(R.id.achievement_9);
        CircleImageView achievement10 = (CircleImageView) profileView.findViewById(R.id.achievement_10);


        if(mSharedPreferenceHelper.getCurrentUserName().equals("")){
            username.setText(mSharedPreferenceHelper.getCurrentUserId());
        }else{
            username.setText(mSharedPreferenceHelper.getCurrentUserName());
        }
        //dataHandler.getUrl(question.getPic(),holder.profilePic,context);

        Picasso.with(getContext()).load(mSharedPreferenceHelper.getProfilePicture()).into(profilePicture);
        mAchievementsHandler.getAchievements(achievement1,"questions");
        mAchievementsHandler.getAchievements(achievement2,"answers");
        mAchievementsHandler.getAchievements(achievement3,"requests");
        mAchievementsHandler.getAchievements(achievement4,"answersVIP");
        mAchievementsHandler.getAchievements(achievement5,"upvotesVIP");
        mAchievementsHandler.getAchievements(achievement6,"followersTOP");
        mAchievementsHandler.getAchievements(achievement7,"answersTOP");
        mAchievementsHandler.getAchievements(achievement8,"upvotesTOP");
        mAchievementsHandler.getAchievements(achievement9,"requestsVIP");
        mAchievementsHandler.getAchievements(achievement10,"followersVIP");

        ViewPager profileViewPager = (ViewPager) profileView.findViewById(R.id.profile_view_pager);
        setupViewPager(profileViewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) profileView.findViewById(R.id.profile_tabs);
        tabs.setupWithViewPager(profileViewPager);


        achievement1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"questions");
            }
        });

        achievement2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"answers");
            }
        });

        achievement3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"requests");
            }
        });

        achievement4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"answersVIP");
            }
        });
        achievement5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"upvotesVIP");
            }
        });
        achievement6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"followersTOP");
            }
        });

        achievement7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"answersTOP");
            }
        });

        achievement8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"upvotesTOP");
            }
        });

        achievement9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"requestsVIP");
            }
        });
        achievement10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAchievementsHandler.displayAchievement(getActivity(),"followersVIP");
            }
        });

        return profileView;
    }

    private void setupViewPager(ViewPager viewPager) {

        Bundle questionArgs = new Bundle();
        questionArgs.putString("userId", mSharedPreferenceHelper.getCurrentUserId());
        questionArgs.putString("type","questions");
        Fragment questions = new IndexedFeed();
        questions.setArguments(questionArgs);


        Bundle answersArgs = new Bundle();
        answersArgs.putString("userId", mSharedPreferenceHelper.getCurrentUserId());
        answersArgs.putString("type","answers");
        Fragment answers = new IndexedFeed();
        answers.setArguments(answersArgs);

        InboxAdapter profileAdapter = new InboxAdapter(getChildFragmentManager());
        profileAdapter.addFragments(questions,"Questions");
        profileAdapter.addFragments(answers,"Answers");
        viewPager.setAdapter(profileAdapter);
    }

    /*search_bar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

                        Thread t = new Thread(new Runnable() {
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
                        t.start();


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
        });*/



}
