package com.arm.amuletmarketandroid;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarketFragment extends Fragment {

    private ListView json_listview;
    private ArrayList<String> exData;
    private ProgressDialog progressDialog;

    public MarketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_market, container, false);

        json_listview = (ListView) rootView.findViewById(R.id.json_listview);

        exData = new ArrayList<String>();
//        exData.add("test1");
//        exData.add("test2");
//        exData.add("test3");
//        exData.add("test4");
//        exData.add("test5");

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Downloading");
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL("http://192.168.1.43:8080/amuletmarket/JSON_addshop.php");

                    URLConnection urlConnection = url.openConnection();

                    HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
                    httpURLConnection.setAllowUserInteraction(false);
                    httpURLConnection.setInstanceFollowRedirects(true);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();

                    InputStream inputStream = null;
                    if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                        inputStream = httpURLConnection.getInputStream();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream
                        , "iso-8859-1"), 8);

                        StringBuilder stringBuilder = new StringBuilder();
                        String line = null;

                        while ((line = reader.readLine()) != null){
                            stringBuilder.append(line + "\n");
                        }

                    inputStream.close();
                    Log.d("JSON Result", stringBuilder.toString());

                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    JSONArray exArray = jsonObject.getJSONArray("Data");

                    for(int i=0; i <  exArray.length(); i++) {
                        JSONObject jsonObj = exArray.getJSONObject(i);
                        exData.add(jsonObj.getString("Name_Shop"));
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
                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, android.R.id.text1, exData);
                json_listview.setAdapter(myAdapter);
                progressDialog.dismiss();
            }
        }.execute();



        // Inflate the layout for this fragment
        return rootView;
    }

}
