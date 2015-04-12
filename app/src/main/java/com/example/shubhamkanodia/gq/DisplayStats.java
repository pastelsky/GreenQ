package com.example.shubhamkanodia.gq;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;


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
        getActionBar().setElevation(0);

        getActionBar().setTitle("Results");
        getActionBar().setHomeButtonEnabled(true);



        ValueLineChart mCubicValueLineChart = (ValueLineChart) findViewById(R.id.cubiclinechart);

        ValueLineSeries rspm = new ValueLineSeries();
        rspm.setColor(0xFF56B7F1);

        rspm.addPoint(new ValueLinePoint("Jan", 2.4f));
        rspm.addPoint(new ValueLinePoint("Feb", 3.4f));
        rspm.addPoint(new ValueLinePoint("Mar", .4f));


        mCubicValueLineChart.addSeries(rspm);

        ValueLineSeries no2 = new ValueLineSeries();
        no2.setColor(0xFFB2B7F1);

        no2.addPoint(new ValueLinePoint("Jan", 1.4f));
        no2.addPoint(new ValueLinePoint("Feb", 2.4f));
        no2.addPoint(new ValueLinePoint("Mar", 1.4f));


        mCubicValueLineChart.addSeries(no2);

        ValueLineSeries so2 = new ValueLineSeries();
        so2.setColor(0xFFA2B7F7);

        so2.addPoint(new ValueLinePoint("Jan", 1.4f));
        so2.addPoint(new ValueLinePoint("Feb", 3.6f));
        so2.addPoint(new ValueLinePoint("Mar", 2.4f));


        mCubicValueLineChart.addSeries(so2);
        mCubicValueLineChart.startAnimation();

        TextView gpercent = (TextView) findViewById(R.id.gpercent);
        double finalpercent = Math.round(Float.parseFloat(myStrings[0]));
        gpercent.setText(Double.toString(finalpercent));
        System.out.println(myStrings[0]);

        TextView trees = (TextView) findViewById(R.id.trees);
        trees.setText(myStrings[1]);
        System.out.println(myStrings[1]);


        RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
        rb.setRating(Integer.parseInt(myStrings[0])/20.0f);

    }
}
