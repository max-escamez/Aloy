package com.aloy.aloy.Util;

/**
 * Created by Max on 29/09/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.TimeUnit;

public class CredentialsHandler {

    private static final String ACCESS_TOKEN_NAME = "webapi.credentials.access_token";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String EXPIRES_AT = "expires_at";

    public static void setAccessToken(Context context, String token, long expiresIn, TimeUnit unit) {
        Context appContext = context.getApplicationContext();
        long now = System.currentTimeMillis();
        long expiresAt = now + unit.toMillis(expiresIn);
        SharedPreferences sharedPref = getSharedPreferences(appContext);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ACCESS_TOKEN, token);
        editor.putLong(EXPIRES_AT, expiresAt);
        editor.apply();
    }

    public static String getAccessToken(Context context) {
        Context appContext = context.getApplicationContext();
        SharedPreferences sharedPref = getSharedPreferences(appContext);
        String token = sharedPref.getString(ACCESS_TOKEN, null);
        long expiresAt = sharedPref.getLong(EXPIRES_AT, 0L);
        if (token == null || expiresAt < System.currentTimeMillis()) {
            return null;
        }
        return token;
    }

    public static long getExpiresAt(Context context) {
        Context appContext = context.getApplicationContext();
        SharedPreferences sharedPref = getSharedPreferences(appContext);
        long expiresAt = sharedPref.getLong(EXPIRES_AT, 0L);
        return expiresAt;
    }

    private static SharedPreferences getSharedPreferences(Context appContext) {
        return appContext.getSharedPreferences(ACCESS_TOKEN_NAME, Context.MODE_PRIVATE);
    }


}