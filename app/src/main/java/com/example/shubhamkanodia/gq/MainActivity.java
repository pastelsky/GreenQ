package com.example.shubhamkanodia.gq;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import com.software.shell.fab.ActionButton;


public class MainActivity extends Activity {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // And then find it within the content view:
        ActionButton actionButton = (ActionButton) findViewById(R.id.action_button);
        actionButton.setImageResource(R.drawable.percent);

        GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getApplicationContext());
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker")
                .draggable(true)
                .snippet("Hello")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {

                Toast.makeText(MainActivity.this, "Dragging Start",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                Toast.makeText(
                        MainActivity.this, "Lat" + map.getMyLocation().getLatitude() + "Long" + map.getMyLocation().getLongitude(),
                        Toast.LENGTH_LONG).show();

            }

            @Override
            public void onMarkerDrag(Marker marker) {
                Toast.makeText(MainActivity.this, "Dragging", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public boolean onMarkerClick(final Marker marker) {

        if (marker.equals(map)) {
            map.getMyLocation();
            double lat = map.getMyLocation().getLatitude();
            Toast.makeText(MainActivity.this,
                    "Current location" + map.getMyLocation().getLatitude(),
                    Toast.LENGTH_SHORT).show();
        }
        return true;
    }


}