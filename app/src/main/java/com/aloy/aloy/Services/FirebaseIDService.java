package com.aloy.aloy.Services;

import android.util.Log;

import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.SharedPreferenceHelper;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by madmax on 2017-12-02.
 */

public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";
    private SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        sharedPreferenceHelper=new SharedPreferenceHelper(getBaseContext());
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if(!sharedPreferenceHelper.getCurrentUserId().isEmpty()) {
            FirebaseDatabase.getInstance().getReference("users").child(sharedPreferenceHelper.getCurrentUserId()).child("notificationTokens").child(refreshedToken).setValue("true");
        }
        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }
}