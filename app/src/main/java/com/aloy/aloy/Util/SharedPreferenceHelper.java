package com.aloy.aloy.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by madmax on 01/11/2017.
 */

public class SharedPreferenceHelper {

    public static final String PREFERENCES = "AloyPreferences";
    public static final String CURRENT_SPOTIFY_TOKEN_KEY = "CurrentSpotifyToken";
    public static final String CURRENT_USER_SPOTIFY_ID_KEY = "CurrentSpotifyID";
    public static final String CURRENT_USER_SPOTIFY_PROFILE_PIC = "currentSpotifyProfilePic";
    public static final String CURRENT_USER_FIREBASE_ID = "currentFirebaseID";

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

    public void saveFirebaseID(String id) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(CURRENT_USER_FIREBASE_ID, id);
        editor.apply();
    }

    public String getFirebaseID() {
        return mSharedPreferences.getString(CURRENT_USER_FIREBASE_ID, "");
    }

    public String getProfilePicture() {
        return mSharedPreferences.getString(CURRENT_USER_SPOTIFY_PROFILE_PIC, "");
    }

    public String getCurrentUserId() {
        return mSharedPreferences.getString(CURRENT_USER_SPOTIFY_ID_KEY, "");
    }

}