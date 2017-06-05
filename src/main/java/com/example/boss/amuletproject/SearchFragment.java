package com.example.boss.amuletproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.util.HashMap;
import java.util.List;


public class SearchFragment extends Fragment {

    private SwipeRefreshLayout mSwipeRefresh;
    SwipeRefreshLayout mSwipeRefreshLayout;

    JSONArray exArray = null;
    ipaddress ipaddress = new ipaddress();
    private String TAG = MarketFragment.class.getSimpleName();

    private ListView ListSearch1;
    ListView ListSearch2;

    private ArrayList<String> exData1, exData2;
    private ProgressDialog progressDialog;
    private EditText edittext;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        ListSearch1 = (ListView) rootView.findViewById(R.id.ListSearch111);
        ListSearch2 = (ListView) rootView.findViewById(R.id.ListSearch111);

        edittext = (EditText) rootView.findViewById(R.id.SearchBox);
        exData1 = new ArrayList<String>();
        exData2 = new ArrayList<String>();


        connect();

        return rootView;
    }

    private void connect() {
        AsyncTask<Void, Void, Void> execute = new AsyncTask<Void, Void, Void>(){

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
                setdata();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, android.R.id.text1, exData1) {
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

                ListSearch1.setAdapter(myAdapter);

                progressDialog.dismiss();

                edittext.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, android.R.id.text1, exData1) {
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
                        ListSearch2.setAdapter(myAdapter);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                        ArrayList<String> new_list = new ArrayList<String>();
                        int textlength = edittext.getText().length();

                        for(int i = 0 ; i < exData1.size() ; i++){
                            try {
                                if(edittext.getText().toString()
                                        .equalsIgnoreCase(exData1.get(i)
                                                .subSequence(0, textlength)
                                                .toString())){
                                    new_list.add(exData1.get(i));

                                }
                            } catch (Exception e) { }
                        }

                        //ListSearch1.setAdapter(null);
                        ListSearch2.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, new_list));
                        ListSearch2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String selectedFromList =(String) (ListSearch2.getItemAtPosition(position));
                                Log.d("sdadxzdzdzd",selectedFromList);
                              int index1 =0;
                                try {
                                    for (int i = 0; i < exArray.length(); i++) {
                                        JSONObject jsonObj = exArray.getJSONObject(i);

                                        if (selectedFromList.equals(jsonObj.getString("NameAmulet1")) ||
                                                selectedFromList.equals(jsonObj.getString("NameAmulet2")) ||
                                                selectedFromList.equals(jsonObj.getString("NameAmulet3")) ||
                                                selectedFromList.equals(jsonObj.getString("NameAmulet4")) ||
                                                selectedFromList.equals(jsonObj.getString("NameAmulet5")) ||
                                                selectedFromList.equals(jsonObj.getString("NameAmulet6")) ||
                                                selectedFromList.equals(jsonObj.getString("NameAmulet7")) ||
                                                selectedFromList.equals(jsonObj.getString("NameAmulet8")) ||
                                                selectedFromList.equals(jsonObj.getString("NameAmulet9")) ||
                                                selectedFromList.equals(jsonObj.getString("NameAmulet10"))) {
                                            index1 = i;
                                            break;
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.e(null, position + " QQQQQQQQQQQ " + exData2.get(index1));
                                Log.e(null, position + " QQQQQQQQQQQ " + exArray.toString());
                                Intent intent = new Intent(getActivity().getApplication(), MainActivity3.class);
                                intent.putExtra("ID_Shop", exData2.get(index1));
                                getActivity().startActivity(intent);
                            }
                        });

                        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, android.R.id.text1, new_list) {
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

                        ListSearch1.setAdapter(myAdapter1);
                    }
                });
                ListSearch1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int index = 0;
                        try {
                            for (int i = 0; i < exArray.length(); i++) {
                                JSONObject jsonObj = exArray.getJSONObject(i);
                                if (exData1.get(position).equals(jsonObj.getString("NameAmulet1")) ||
                                        exData1.get(position).equals(jsonObj.getString("NameAmulet2")) ||
                                        exData1.get(position).equals(jsonObj.getString("NameAmulet3")) ||
                                        exData1.get(position).equals(jsonObj.getString("NameAmulet4")) ||
                                        exData1.get(position).equals(jsonObj.getString("NameAmulet5")) ||
                                        exData1.get(position).equals(jsonObj.getString("NameAmulet6")) ||
                                        exData1.get(position).equals(jsonObj.getString("NameAmulet7")) ||
                                        exData1.get(position).equals(jsonObj.getString("NameAmulet8")) ||
                                        exData1.get(position).equals(jsonObj.getString("NameAmulet9")) ||
                                        exData1.get(position).equals(jsonObj.getString("NameAmulet10"))) {
                                    index = i;
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e(null, position + " wwqwqfdxcxxcv " + exData2.get(index));
                        Log.e(null, position + " wwqwqfdxcxxcv " + exArray.toString());
                        Intent intent = new Intent(getActivity().getApplication(), MainActivity3.class);
                        intent.putExtra("ID_Shop", exData2.get(index));
                        getActivity().startActivity(intent);
                    }
                });

            }
        }.execute();

    }//connect()






    public void setdata(){
        try {
            URL url = new URL(ipaddress.getIp() + "amuletmarket/JSON_search.php");
            URLConnection urlConnection = url.openConnection();

            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
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

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            inputStream.close();
            Log.d("JSON Result", stringBuilder.toString());

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            exArray = jsonObject.getJSONArray("Data");


            for (int i = 0; i < exArray.length(); i++) {
                JSONObject jsonObj = exArray.getJSONObject(i);
                exData1.add(jsonObj.getString("NameAmulet1"));
                exData1.add(jsonObj.getString("NameAmulet2"));
                exData1.add(jsonObj.getString("NameAmulet3"));
                exData1.add(jsonObj.getString("NameAmulet4"));
                exData1.add(jsonObj.getString("NameAmulet5"));
                exData1.add(jsonObj.getString("NameAmulet6"));
                exData1.add(jsonObj.getString("NameAmulet7"));
                exData1.add(jsonObj.getString("NameAmulet8"));
                exData1.add(jsonObj.getString("NameAmulet9"));
                exData1.add(jsonObj.getString("NameAmulet10"));
                exData2.add(jsonObj.getString("ID_Shop"));

            }

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }//setdata()
}