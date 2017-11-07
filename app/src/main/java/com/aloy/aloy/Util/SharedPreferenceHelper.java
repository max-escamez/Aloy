package com.aloy.aloy.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by madmax on 01/11/2017.
 */

public class SharedPreferenceHelper {

    private static final String PREFERENCES = "AloyPreferences";
    private static final String CURRENT_SPOTIFY_TOKEN_KEY = "CurrentSpotifyToken";
    private static final String CURRENT_USER_SPOTIFY_ID_KEY = "CurrentSpotifyID";
    private static final String CURRENT_USER_SPOTIFY_NAME_KEY = "CurrentSpotifyName";
    private static final String CURRENT_USER_SPOTIFY_PROFILE_PIC = "currentSpotifyProfilePic";

    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public SharedPreferenceHelper(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREFERENCES, 0);
    }

    public void saveSpotifyToken(String token) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(CURRENT_SPOTIFY_TOKEN_KEY, token);
        editor.apply();
    }

    public void saveCurrentUserId(String id) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(CURRENT_USER_SPOTIFY_ID_KEY, id);
        editor.apply();
    }

    public void saveProfilePicture(String id) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(CURRENT_USER_SPOTIFY_PROFILE_PIC, id);
        editor.apply();
    }

    public String getCurrentSpotifyToken() {
        return mSharedPreferences.getString(CURRENT_SPOTIFY_TOKEN_KEY, "");
    }

    public void saveName(String id) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(CURRENT_USER_SPOTIFY_NAME_KEY, id);
        editor.apply();
    }

    public String getProfilePicture() {
        return mSharedPreferences.getString(CURRENT_USER_SPOTIFY_PROFILE_PIC, "");
    }

    public String getCurrentUserName(){
        return mSharedPreferences.getString(CURRENT_USER_SPOTIFY_NAME_KEY, "");
    }

    public String getCurrentUserId() {
        return mSharedPreferences.getString(CURRENT_USER_SPOTIFY_ID_KEY, "");
    }

}
