package com.example.boss.amuletproject;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
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


public class map_shop extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    ipaddress ipaddress = new ipaddress();
    ArrayList<String> exData1,exData2,exData3;

    public map_shop() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        String IdMarket = intent.getStringExtra("id_amuletmarket");
        Log.d("id_amuletmarket55555555","ssss"+IdMarket);

        View rootView = inflater.inflate(R.layout.fragment_map_shop, container, false);


        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map5);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        connect();
    }

    private void connect() {
        exData1 = new ArrayList<String>();
        exData2 = new ArrayList<String>();
        exData3 = new ArrayList<String>();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Intent intent = getActivity().getIntent();
                    if (intent.getExtras() != null) {
                        String IdMarket = intent.getStringExtra("id_amuletmarket");
                        Log.d("ID", "IdMarket : " + IdMarket);

                        URL url = new URL(ipaddress.getIp() + "amuletmarket/JSON_addshop.php?MarketAmuletShop="+IdMarket); //ComSci

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
                for (int i = 0; i < exData1.size(); i++) {

                    LatLng SSS = new LatLng(Double.parseDouble(exData1.get(i)), Double.parseDouble(exData2.get(i)));
                    mMap.addMarker(new MarkerOptions().position(SSS).title(exData3.get(i)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SSS,15));

                }
            }
        }.execute();






    }
}
