package com.example.boss.amuletproject;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Test extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<String> exData1,exData2,exData3;
    ipaddress ipaddress = new ipaddress();
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        connect();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng  sydney = new LatLng(Double.parseDouble(exData1.get(0)), Double.parseDouble(exData2.get(0)));
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .snippet(exData3.get(0))
                .title(exData3.get(0)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));



    }



    private void connect() {
        exData1 = new ArrayList<String>();
        exData2 = new ArrayList<String>();
        exData3 = new ArrayList<String>();
        Log.d("ERERERERER123sdsasda","2322332332");

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Intent intent = Test.this.getIntent();
                    if (intent.getExtras() != null) {
                        String IdMarket = intent.getStringExtra("ID_Shop");
                        Log.d("ID", "IdMarket0 : " + IdMarket);

                        URL url = new URL(ipaddress.getIp() + "amuletmarket/JSON_MapToShop?ID_Shop="+IdMarket); //ComSci

//                    URL url = new URL("http://192.168.1.34:8080/amuletmarket/JSON_Market.php"); //Home

                        List<NameValuePair> param = new ArrayList<NameValuePair>();
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost(String.valueOf(url));
                        httppost.setEntity(new UrlEncodedFormEntity(param));


                        //Encoding POST data
                        try {
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
                            }
                                            Log.e("awewaesd ",exData3.get(0));


                        } catch (UnsupportedEncodingException e)
                        {
                            e.printStackTrace();
                        }catch (ClientProtocolException e) {
                            // Log exception
                            e.printStackTrace();
                        } catch (IOException e) {
                            // Log exception
                            e.printStackTrace();
                        }
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


            }
        }.execute();
    }


}
