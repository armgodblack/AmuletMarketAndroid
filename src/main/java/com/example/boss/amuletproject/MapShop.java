package com.example.boss.amuletproject;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;



public class MapShop extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    ArrayList<String> exData1,exData2,exData3,exData4;
    ipaddress ipaddress = new ipaddress();
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapshop);
        connect();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        connect();
    }

    private void connect() {
        exData1 = new ArrayList<String>(); //Lat
        exData2 = new ArrayList<String>(); //Log
        exData3 = new ArrayList<String>(); //NameMarket
        exData4 = new ArrayList<String>();//NameShop
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Intent intent = MapShop.this.getIntent();
                    if (intent.getExtras() != null) {
                        String id_shop = intent.getStringExtra("ID_Shop");
                        Log.d("ID", "IdMarket1 : " + id_shop);

                        URL url = new URL(ipaddress.getIp() + "amuletmarket/JSON_MapToShop?ID_Shop="+id_shop);

                        List<NameValuePair> param = new ArrayList<NameValuePair>();
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost(String.valueOf(url));
                        httppost.setEntity(new UrlEncodedFormEntity(param));


                        //Encoding POST data
                        httppost.setEntity(new UrlEncodedFormEntity(param));
                        HttpResponse response = httpclient.execute(httppost);
                        String json = EntityUtils.toString(response.getEntity(), "UTF-8");
                        JSONObject tokener = new JSONObject(json);

                        JSONArray exArray = tokener.getJSONArray("Data");

                        for (int i = 0; i < exArray.length(); i++) {
                            JSONObject jsonObj = exArray.getJSONObject(i);

                            exData1.add(jsonObj.getString("Latitude"));
                            exData2.add(jsonObj.getString("Longitude"));
                            exData3.add(jsonObj.getString("Name_Market"));
                            exData4.add(jsonObj.getString("Name_Shop"));
                        }
                                Log.e("awewaesd ",exData1.get(0));



                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                for (int i = 0; i < exData1.size(); i++) {

                    LatLng SSS = new LatLng(Double.parseDouble(exData1.get(i)), Double.parseDouble(exData2.get(i)));
                    mMap.addMarker(new MarkerOptions().position(SSS).snippet(exData4.get(0)).title(exData3.get(i)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SSS,15));

                }

            }
        }.execute();
    }

}
