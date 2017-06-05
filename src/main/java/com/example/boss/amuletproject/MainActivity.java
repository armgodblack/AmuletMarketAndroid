package com.example.boss.amuletproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;

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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public double latitude = 0;
    public double longitude =0;

    ipaddress ipaddress = new ipaddress();
    private ProgressDialog progressDialog;

    Button btnShowLocation;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView back = (ImageView) findViewById(R.id.back);
        ImageView home = (ImageView) findViewById(R.id.home);



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //       Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //               .setAction("Action", null).show();
        //}
        //});




    }
//    public void sendData(){
//        Log.e("test 0 ","Http Post Response:");
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
////                progressDialog = new ProgressDialog(MainActivity.this);
////                progressDialog.setCancelable(false);
////                progressDialog.setMessage("Downloading");
////                progressDialog.show();
//
//
//
//
//            }


//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    Log.e("test 1 ","Http Post Response:");
//                    URL url = new URL(ipaddress.getIp() + "amuletmarket/JSON_Market.php"); //ComSci
////                    URL url = new URL("http://192.168.1.34:8080/amuletmarket/JSON_Market.php"); //Home
//
//                    List<NameValuePair> param = new ArrayList<NameValuePair>();
//
//
//                    HttpClient httpclient = new DefaultHttpClient();
//                    HttpPost httppost = new HttpPost(String.valueOf(url));
//
//                    param.add(new BasicNameValuePair("Latitude", String.valueOf(latitude)));
//
//                    param.add(new BasicNameValuePair("Longitude", String.valueOf(longitude)));
//
//                    httppost.setEntity(new UrlEncodedFormEntity(param));
//
//                    //Encoding POST data
//                    try {
//                        httppost.setEntity(new UrlEncodedFormEntity(param));
//                        HttpResponse response = httpclient.execute(httppost);
//                        String json = EntityUtils.toString(response.getEntity(), "UTF-8");
//                        JSONObject tokener = new JSONObject(json);
//                        JSONArray exArray = tokener.getJSONArray("Data");
//                        Log.d("ddqweew ", "" + exArray.toString().substring(1000));
//                        for (int i = 0; i < exArray.length(); i++) {
//                            JSONObject jsonObj = exArray.getJSONObject(i);
////                            Log.d(jsonObj.getString("id_amuletmarket")+" "+"Latitude", "" + jsonObj.getString("Latitude"));
////                            Log.d(jsonObj.getString("id_amuletmarket")+" "+"Longitude", "" + jsonObj.getString("Longitude")+"\n");
//
//
//
//
//
//                        }
//
//                    } catch (UnsupportedEncodingException e)
//                    {
//                        e.printStackTrace();
//                    }catch (ClientProtocolException e) {
//                        // Log exception
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        // Log exception
//                        e.printStackTrace();
//                    }



//                    URLConnection urlConnection = url.openConnection();
//
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
//                    httpURLConnection.setAllowUserInteraction(false);
//                    httpURLConnection.setInstanceFollowRedirects(true);
//                    httpURLConnection.setRequestMethod("POST");
//
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
//                    while ((line = reader.readLine()) != null) {
//                        stringBuilder.append(line + "\n");
//                    }
//
//                    inputStream.close();
//                    Log.d("JSON Result", stringBuilder.toString());

//                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
//                    JSONArray exArray = jsonObject.getJSONArray("Data");


//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//
//            }
//
//        }.execute();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case 0:
                    return new MarketFragment();
                case 1:
                    return new SearchFragment();
                case 2:
                    return new Top10AmuletFragment();
                case 3:
                    return new Top10ShopFragment();
            }

            return new MarketFragment();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Market";
                case 1:
                    return "Search";
                case 2:
                    return "Top"+"\n"+"Amulet";
                case 3:
                    return "Top"+"\n"+"Shop";
            }
            return null;
        }
    }
}