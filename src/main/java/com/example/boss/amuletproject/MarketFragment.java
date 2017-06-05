package com.example.boss.amuletproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.test.mock.MockPackageManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarketFragment extends Fragment {

    private SwipeRefreshLayout mSwipeRefresh;
    SwipeRefreshLayout mSwipeRefreshLayout;

    ipaddress ipaddress = new ipaddress();


    private ListView json_listview;
    private ArrayList<String> exData,exData2;
    private ProgressDialog progressDialog;

    public double latitude = 0;
    public double longitude =0;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;




    public MarketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_market2, container, false);

        json_listview = (ListView) rootView.findViewById(R.id.list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        exData2 = new ArrayList<String>();

        exData = new ArrayList<String>();
//        exData.add("test1");
//        exData.add("test2");
//        exData.add("test3");
//        exData.add("test4");
//        exData.add("test5");

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                json_listview.postDelayed(new Runnable() {

                    public void run() {
                        Toast.makeText(getActivity(), "Refresh Complete",Toast.LENGTH_LONG).show();
                        connect();
                        mSwipeRefreshLayout.setRefreshing(false);


                    }

                }, 0);

            }
        });

        connect();


        // Inflate the layout for this fragment
        return rootView;
    }

    private void connect() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Downloading");
                progressDialog.show();

                gps = new GPSTracker(getActivity());

                // check if GPS enabled
                if (gps.canGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    //sendData();
                    // \n is for new line
                    Log.d("latitude : " + latitude, "longitude : " + longitude);

                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }


            @Override
            protected Void doInBackground(Void... params) {

                try {
                    Log.e("test 1 ","Http Post Response:");
                    URL url = new URL(ipaddress.getIp() + "amuletmarket/JSON_market.php"); //ComSci
//                    URL url = new URL("http://192.168.1.34:8080/amuletmarket/JSON_Market.php"); //Home

                    List<NameValuePair> param = new ArrayList<NameValuePair>();


                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(String.valueOf(url));

                    param.add(new BasicNameValuePair("Latitude", String.valueOf(latitude)));
                    param.add(new BasicNameValuePair("Longitude", String.valueOf(longitude)));

                    httppost.setEntity(new UrlEncodedFormEntity(param));


                    //Encoding POST data
                    try {
                        httppost.setEntity(new UrlEncodedFormEntity(param));
                        HttpResponse response = httpclient.execute(httppost);
                        String json = EntityUtils.toString(response.getEntity(), "UTF-8");
                        JSONObject tokener = new JSONObject(json);

                        JSONArray exArray = tokener.getJSONArray("Data");

                        Log.d("ddqweew ", "" + exArray.toString().substring(1000));
                            exData.clear();
                            exData2.clear();
                        for (int i = 0; i < exArray.length(); i++) {
                            JSONObject jsonObj = exArray.getJSONObject(i);
                            exData.add(jsonObj.getString("Name_Market")+"\n"+jsonObj.getString("Time")+"                          "+jsonObj.getString("distance").substring(0,3)+"Km.");
                            exData2.add(jsonObj.getString("id_amuletmarket"));

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

//                try {
//                    URL url = new URL(ipaddress.getIp()+"amuletmarket/JSON_Market.php"); //ComSci
//                    //URL url = new URL("http://192.168.1.34:8080/amuletmarket/JSON_Market.php"); //Home
//                    URLConnection urlConnection = url.openConnection();
//
//                    HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
//                    httpURLConnection.setAllowUserInteraction(false);
//                    httpURLConnection.setInstanceFollowRedirects(true);
//                    httpURLConnection.setRequestMethod("GET");
//                    httpURLConnection.connect();
//
//                    InputStream inputStream = null;
//                    if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
//                        inputStream = httpURLConnection.getInputStream();
//
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream
//                            , "iso-8859-1"), 8);
//
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String line = null;
//
//                    while ((line = reader.readLine()) != null){
//                        stringBuilder.append(line + "\n");
//                    }
//
//                    inputStream.close();
//                    Log.d("JSON Result", stringBuilder.toString());
//
//                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
//                    JSONArray exArray = jsonObject.getJSONArray("Data");
//                    try{
//
//                        for(int i=0; i <  exArray.length(); i++) {
//                            JSONObject jsonObj = exArray.getJSONObject(i);
//                            exData.add(jsonObj.getString("Name_Market")+"\n"+jsonObj.getString("Time"));
//                            exData2.add(jsonObj.getString("id_amuletmarket"));
//
//
//                        }
//                    }
//                    catch (JSONException e){
//
//                    }

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
                final ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, android.R.id.text1, exData){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent){
                        // Get the Item from ListView
                        View view = super.getView(position, convertView, parent);

                        // Initialize a TextView for ListView each Item
                        TextView tv = (TextView) view.findViewById(android.R.id.text1);

                        // Set the text color of TextView (ListView Item)
                        tv.setTextColor(Color.WHITE);


                        // Generate ListView Item using TextView
                        return view;
                    }

                };

                json_listview.setAdapter(myAdapter);



                progressDialog.dismiss();



                json_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("", "exData2 : " + exData2.get(position));

                        Intent intent = new Intent(getActivity().getBaseContext(), MainActivity2.class);
                        intent.putExtra("id_amuletmarket", exData2.get(position));
                        getActivity().startActivity(intent);

//                      Bundle bundle = new Bundle();
//                      bundle.putString("id_amuletmarket",exData2.get(position));
//                      ShopFragment shop = new ShopFragment();
//                      shop.setArguments(bundle);
//                   startActivity(intent);
                    }



                });
                Toast.makeText(getActivity(), "ค้นหาตลาดพระสำเร็จ !!",Toast.LENGTH_LONG).show();

            }


        }.execute();
    }


}
