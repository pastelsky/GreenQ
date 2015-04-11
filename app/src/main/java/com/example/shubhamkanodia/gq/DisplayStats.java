package com.example.shubhamkanodia.gq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Chirag on 12-04-2015.
 */
public class DisplayStats extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_stats);
        Intent intent = getIntent();
        String[] myStrings = intent.getStringArrayExtra("strings");

        TextView text = (TextView) findViewById(R.id.text);
        text.setText(myStrings[0] + myStrings[1]);

    }
}
