package com.example.boss.amuletproject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.loopj.android.image.SmartImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FontAmuletFragment extends Fragment {

    ipaddress ipaddress = new ipaddress();
    SmartImageView imageAmuletFont;
    private ProgressDialog progressDialog;
    ArrayList<String> exData1,exData2,exData3;

    public FontAmuletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_font_amulet, container, false);

        exData2 = new ArrayList<String>();
        imageAmuletFont =(SmartImageView)rootView.findViewById(R.id.imageAmuletFont);

        new AsyncTask<Object, Object, JSONObject>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Downloading...");
                progressDialog.show();
            }

            @Override
            protected JSONObject doInBackground(Object... objects) {
                JSONObject exArray1 = null;
                try {
                    Intent intent = getActivity().getIntent();
                    if (intent.getExtras() != null){
                        String id_amulet = intent.getStringExtra("ID_Amulet");
                        URL url = new URL(ipaddress.getIp()+"amuletmarket/JSON_showamulet.php?ID_Amulet="+id_amulet);
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

                        JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                        JSONObject exArray = jsonObject.getJSONObject("Data");
                        exArray1 = exArray.getJSONObject("DataAmulet");

                        for (int i = 0; i < exArray1.length(); i++) {
                            JSONObject jsonObj = exArray1.getJSONObject(String.valueOf(0));
                            exData2.add(jsonObj.getString("ID_Amulet"));

                            Log.d("TTTTTTTT ", "" + exData2.toString().substring(1000));

                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return exArray1;
            }

            @Override
            protected void onPostExecute(final JSONObject exArray1) {
                super.onPostExecute(exArray1);
                progressDialog.dismiss();

                try {
                    Glide.with(getActivity())
                            .load(ipaddress.getIp()+exArray1.getString("ImagePathAmulet1")+exArray1.getString("FrontPicture"))
                            .into(imageAmuletFont);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.execute();


        return rootView;
    }

}
