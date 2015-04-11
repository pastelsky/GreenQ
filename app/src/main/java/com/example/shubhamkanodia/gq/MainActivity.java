package com.example.shubhamkanodia.gq;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mbanje.kurt.fabbutton.FabButton;


public class MainActivity extends Activity {

    HttpClient client;
    String URL = "https://greenquotient.herokuapp.com/process.php?";
    JSONObject json;

    String number_of_trees_to_be_planted;

    GoogleMap map;
    ArrayList<Double> a;
    Marker mMarker;
    Boolean checked = false;
    String greeniness;
    FabButton indeterminate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new DefaultHttpClient();

        ActionBar ab = getActionBar();
        ab.setTitle("Green Quotient");

        a = new ArrayList<>();
        // And then find it within the content view:

        indeterminate = (FabButton) findViewById(R.id.indeterminate);

        indeterminate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIndeterminateProgress();

                new ApiCall().execute();
            }
        });

//        this.getActionBar().hide();

        GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getApplicationContext());
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        ab.setSubtitle(getMyLocationAddress(latitude, longitude));

        getActionBar().setDisplayHomeAsUpEnabled(true);


        a.add(latitude);
        a.add(longitude);

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMarker = map.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title("Marker")
                .draggable(true)
                .snippet("Hello")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

        moveToCurrentLocation(new LatLng(latitude, longitude));
        checked = true;

        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {

//                Toast.makeText(MainActivity.this, "Dragging Start",
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

//                Toast.makeText(
//                        MainActivity.this, "Lat" + map.getMyLocation().getLatitude() + "Long" + map.getMyLocation().getLongitude(),
//                        Toast.LENGTH_LONG).show();

                LatLng position = marker.getPosition(); //
//                a.remove(0);
//                a.remove(1);

                getActionBar().setSubtitle(getMyLocationAddress(position.latitude, position.longitude));

                a.add(position.latitude);
                a.add(position.longitude);

            }

            @Override
            public void onMarkerDrag(Marker marker) {
//                Toast.makeText(MainActivity.this, "Dragging", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void startIndeterminateProgress() {
        indeterminate.showProgress(true);
    }

    //Json Part
    public JSONObject get_entire_json() throws IOException, JSONException {

//        StringBuilder url = new StringBuilder(URL + email + PASSWORD);
        StringBuilder url = new StringBuilder(URL + "lat=" + a.get(0) + "&lng=" + a.get(1));
        HttpGet get = new HttpGet(url.toString());
        HttpResponse r = client.execute(get);

        int status = r.getStatusLine().getStatusCode();

        if (status == 200) {
            HttpEntity e = r.getEntity();
            String data = EntityUtils.toString(e);
            JSONObject full_json = new JSONObject(data);

            return full_json;
        } else {
            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            return null;

        }
    }

    public class ApiCall extends AsyncTask<String, Integer, String> {
        /**
         * progress dialog to show user that the backup is processing.
         */


        int i = 0;

        //Only getting the list of the subjects and getting basic info
        @Override
        protected String doInBackground(String... params) {
            try {
                json = get_entire_json();
                try {
                    greeniness = json.getString("greeniness");
                    number_of_trees_to_be_planted = json.getString("number_of_trees_to_be_planted");

//                    //Get only marks Objects
//                    string_marks = json.getString("marks");
//                    b.putString("marks", string_marks);
//                    JSONObject json_marks = new JSONObject(string_marks);
//
//                    //Get names of the objects dynamically
//                    Iterator keys = json_marks.keys();
//
//                    while (keys.hasNext()) {
//                        // loop to get the dynamic key
//
//                        String currentDynamicKey = (String) keys.next();
//                        subjectList.add(i, currentDynamicKey);
//                        i++;
//                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

//                name = json_basic_info.getString("name");
//                usn = json_basic_info.getString("usn");
//
//                Context context = getActivity();
//                AppPrefs appPrefs = new AppPrefs(context);
//                appPrefs.setUsn_saved(usn);
//
//                b.putStringArrayList("names_of_subjects", subjectList);
//                System.out.println(subjectList);
//
//                //
//                b.putString("all_marks", string_marks);

                return null;

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(MainActivity.this, greeniness, Toast.LENGTH_SHORT).show();
            indeterminate.showProgress(false);

            String[] myStrings = new String[]{greeniness, number_of_trees_to_be_planted};

            Intent a = new Intent(MainActivity.this, DisplayStats.class);
            a.putExtra("strings", myStrings);
            startActivity(a);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

    public void getLatLongFromAddress(String youraddress) {
        String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
                youraddress + "&sensor=false";
        HttpGet httpGet = new HttpGet(uri);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            double lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            double lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            a.clear();
            a.add(lat);
            a.add(lng);

            Log.d("latitude", "" + lat);
            Log.d("longitude", "" + lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getMyLocationAddress(Double lat, Double lng) {

        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

        try {

            //Place your latitude and longitude
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);

            if (addresses != null) {

                Address fetchedAddress = addresses.get(0);
                StringBuilder strAddress = new StringBuilder();

                for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                }
                try {
                    getActionBar().setSubtitle(strAddress.toString());
                    return strAddress.toString();
                } catch (Exception e) {

                }

            } else
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Could not get address..!", Toast.LENGTH_LONG).show();
        }
        return "";
    }


    //Async
    private class Read extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";

            String whole_address = "";
            for (int i = 0; i < urls.length; i++) {
                whole_address += urls[i];
                whole_address = whole_address.replace(' ', '+');


//                getActionBar().setSubtitle(whole_address);

            }

            try {
                getLatLongFromAddress(whole_address);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            moveToCurrentLocation(new LatLng(a.get(0), a.get(1)));
        }
    }

    //End Async


    //Animate
    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = map.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }
    //End


    private void moveToCurrentLocation(LatLng currentLocation) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


        if (checked)
            animateMarker(mMarker, currentLocation, false);

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

    /**
     * Callback function
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /** Create an option menu from res/menu/items.xml */
        getMenuInflater().inflate(R.menu.items, menu);
        /** Get the action view of the menu item whose id is search */
        View v = (View) menu.findItem(R.id.search).getActionView();

        /** Get the edit text from the action view */
        EditText txtSearch = (EditText) v.findViewById(R.id.txt_search);
        txtSearch.setFocusable(true);

        /** Setting an action listener */
        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Toast.makeText(getBaseContext(), "Search : " + v.getText(), Toast.LENGTH_SHORT).show();

                new Read().execute(v.getText().toString());
                return false;
            }
        });

        super.onCreateOptionsMenu(menu);
        return true;
    }
}