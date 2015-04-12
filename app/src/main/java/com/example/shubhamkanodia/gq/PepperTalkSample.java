package com.example.shubhamkanodia.gq;

import android.app.Application;

import com.espreccino.peppertalk.PepperTalk;
import com.espreccino.peppertalk.PepperTalkError;

/**
 * Created by Chirag on 12-04-2015.
 */
public class PepperTalkSample extends Application implements
        PepperTalk.ConnectionListener {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void initPepperTalk(String userId) {
        String clientId = getString(R.string.client_id);
        String clientSecret = getString(R.string.client_secret);
        PepperTalk.getInstance(this)
                .init(clientId,
                        clientSecret,
                        userId)
                .inAppNotificationsEnabled(true) // Enable In app notifications
                .notificationStatIcon(android.R.drawable.stat_notify_chat) // Notification icons
                .connectionListener(this)
                .connect();
    }

    @Override
    public void onConnecting(int i) {

    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onConnectionFailed(PepperTalkError pepperTalkError) {

    }
}