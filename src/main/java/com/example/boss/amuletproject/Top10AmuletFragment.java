package com.example.boss.amuletproject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.R.attr.data;


public class Top10AmuletFragment extends Fragment {

    ipaddress ipaddress = new ipaddress();
    ArrayList<String> exData1,exData2,exData3,exData4;
    private ListView json_listview;
    private ProgressDialog progressDialog;

    TextView Textdatetime2;

    public Top10AmuletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top10_amulet, container, false);
        json_listview = (ListView) rootView.findViewById(R.id.listTopAmulet);
        Textdatetime2 =(TextView)rootView.findViewById(R.id.Textdatetime2);

        exData1 = new ArrayList<String>();
        exData2 = new ArrayList<String>();
        exData3 = new ArrayList<String>();
        exData4 = new ArrayList<String>();

        connect();


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
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL(ipaddress.getIp() + "amuletmarket/JSON_top10amulet.php");
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(String.valueOf(url));
                    try{
                        HttpResponse response = httpclient.execute(httppost);
                        String json = EntityUtils.toString(response.getEntity(), "UTF-8");
                        JSONObject tokener = new JSONObject(json);

                        JSONArray exArray = tokener.getJSONArray("Data");

                        for (int i = 0; i < exArray.length(); i++) {
                            JSONObject jsonObj = exArray.getJSONObject(i);
                            exData1.add((i+1)+"  "+jsonObj.getString("NameLP")+" "+jsonObj.getString("Temple")+"\n     "+jsonObj.getString("Generation")+" "+jsonObj.getString("YearAmulet"));
                            exData2.add(jsonObj.getString("ID_Amulet"));

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                final ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, android.R.id.text1, exData1){
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
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                        Intent intent = new Intent(getActivity().getApplication(),ShowAmulet.class);
                        intent.putExtra("ID_Amulet",exData2.get(position));
//                        Log.d("", "exData2 : " + exData2.get(position));
                        getActivity().startActivity(intent);



//                        Intent intent = new Intent(getActivity().getApplication(),MainActivity3.class);
//                        startActivity(intent);
                    }
                });
            }
        }.execute();
        //        -----------------------------------------------------------
        new AsyncTask<Object, Object, JSONObject>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected JSONObject doInBackground(Object... objects) {
                JSONObject exArray1 = null;

                try {

                    URL url = new URL(ipaddress.getIp()+"amuletmarket/JSON_DateThai.php" );
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
                    exArray1 = exArray.getJSONObject("DataDate");


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
                try {
                    Textdatetime2.setText(exArray1.getString("thaidate").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();





    }//connect


}
