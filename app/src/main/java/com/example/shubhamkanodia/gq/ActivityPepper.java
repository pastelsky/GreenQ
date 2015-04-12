package com.example.shubhamkanodia.gq;

import android.app.Activity;
import android.os.Bundle;

import com.espreccino.peppertalk.PepperTalk;

/**
 * Created by Chirag on 12-04-2015.
 */
public class ActivityPepper extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PepperTalkSample) getApplicationContext()).initPepperTalk("shubhamsizzles@gmail.com");
        PepperTalk.getInstance(ActivityPepper.this)
                .chatWithParticipant("chiragshenoy@hotmail.com")
                .topicId("We need to work")
                .topicTitle("Let ride!")
                .start();

    }
}
