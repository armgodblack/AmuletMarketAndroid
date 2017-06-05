package com.example.boss.amuletproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
 * Created by boss on 5/27/2017.
 */

public class ShowShop extends AppCompatActivity {
    ipaddress ipaddress = new ipaddress();

    TextView Name_Shop1,Name_Owner1,Phone1,Line1,Facebook1,MarketProfile1,Detail_Shop1;
    ArrayList<String> exData1,exData2,exData3;
    SmartImageView PicFontShop1,LogoShop1;
    ImageView btn_back2,Mapping1;

    protected ListView ListAmuletShop;
    private ArrayList<String> DataShop;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showshop);

        exData2 = new ArrayList<String>();

        btn_back2=(ImageView)findViewById(R.id.btn_back2);
        Mapping1=(ImageView)findViewById(R.id.Mapping1);

        Name_Owner1=(TextView)findViewById(R.id.Name_Owner1);
        Name_Shop1=(TextView)findViewById(R.id.Name_Shop1);
        Phone1=(TextView)findViewById(R.id.Phone1);
        Line1=(TextView)findViewById(R.id.Line1);
        Facebook1=(TextView)findViewById(R.id.Facebook1);
        MarketProfile1=(TextView)findViewById(R.id.MarketProfile1);
        Detail_Shop1=(TextView)findViewById(R.id.Detail_Shop1);

        PicFontShop1=(SmartImageView)findViewById(R.id.PicFontShop1);
        LogoShop1=(SmartImageView)findViewById(R.id.LogoShop1);

        new AsyncTask<Object, Object, JSONObject>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(ShowShop.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Downloading...");
                progressDialog.show();
            }

            @Override
            protected JSONObject doInBackground(Object... objects) {
                JSONObject exArray1 = null;

                try {
                    Intent intent = ShowShop.this.getIntent();
                    if (intent.getExtras() != null){
                        String id_shop = intent.getStringExtra("ID_Shop");
                        URL url = new URL(ipaddress.getIp()+"amuletmarket/JSON_showshop.php?ID_Shop="+id_shop );
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
                        exArray1 = exArray.getJSONObject("DataShop");

                        for (int i = 0; i < exArray1.length(); i++) {
                            JSONObject jsonObj = exArray1.getJSONObject(String.valueOf(0));
                            exData2.add(jsonObj.getString("ID_Shop"));

                            Log.d("TTTTTTTT ", "" + exArray1.toString().substring(1000));

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
                    Name_Owner1.setText(exArray1.getString("Name_Owner").toString());
                    Name_Shop1.setText(exArray1.getString("Name_Shop").toString());
                    Phone1.setText(exArray1.getString("Phone").toString());
                    Line1.setText(exArray1.getString("Line").toString());
                    Facebook1.setText(exArray1.getString("Facebook").toString());
                    MarketProfile1.setText(exArray1.getString("Name_Market").toString());
                    Detail_Shop1.setText(exArray1.getString("Detail_Shop").toString());

                    PicFontShop1.setImageUrl(ipaddress.getIp()+exArray1.getString("ImageShopPath")+exArray1.getString("Picture_Shop"));
                    LogoShop1.setImageUrl(ipaddress.getIp()+exArray1.getString("ImageLogoPath")+exArray1.getString("Img_Logo"));

                    exData2.add(exArray1.getString("ID_Shop"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Mapping1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(ShowShop.this,MapTopShop.class);
                        i.putExtra("ID_Shop", exData2.get(0));
                        ShowShop.this.startActivity(i);


                    }
                });

                btn_back2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        }.execute();//AsyncTack

    }
}
