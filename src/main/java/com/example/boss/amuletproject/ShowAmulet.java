package com.example.boss.amuletproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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



public class ShowAmulet extends AppCompatActivity {
    ipaddress ipaddress = new ipaddress();

    TextView NameLP,Temple,Generation,Province,YearAmulet,Type_Name_Amulet,Name_Owner;
    ArrayList<String> exData1,exData2,exData3;
    SmartImageView imageAmulet1,imageAmulet2;
    ImageView btn_back1;
    Button btn_zoom;
    protected ListView ListAmuletShop;
    private ArrayList<String> DataShop;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showamulet);

        exData2 = new ArrayList<String>();

        btn_back1=(ImageView)findViewById(R.id.btn_back1);
        btn_zoom=(Button)findViewById(R.id.btn_zoom);
        NameLP=(TextView)findViewById(R.id.NameLP);
        Temple=(TextView)findViewById(R.id.Temple);
        Generation=(TextView)findViewById(R.id.Generation);
        Province=(TextView)findViewById(R.id.Province);
        YearAmulet=(TextView)findViewById(R.id.YearAmulet);
        Type_Name_Amulet=(TextView)findViewById(R.id.Type_Name_Amulet);
        Name_Owner=(TextView)findViewById(R.id.Name_Owner);
        imageAmulet1=(SmartImageView) findViewById(R.id.imageAmulet1);
        imageAmulet2=(SmartImageView) findViewById(R.id.imageAmulet2);







        new AsyncTask<Object, Object, JSONObject>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(ShowAmulet.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Downloading...");
                progressDialog.show();
            }

            @Override
            protected JSONObject doInBackground(Object... objects) {
                JSONObject exArray1 = null;
                try {
                    Intent intent = ShowAmulet.this.getIntent();
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
                    NameLP.setText(exArray1.getString("NameLP").toString());
                    Temple.setText(exArray1.getString("Temple").toString());
                    Generation.setText(exArray1.getString("Generation").toString());
                    Province.setText(exArray1.getString("Province").toString());
                    YearAmulet.setText(exArray1.getString("YearAmulet").toString());
                    Type_Name_Amulet.setText(exArray1.getString("Type_Name_Amulet").toString());
                    Name_Owner.setText(exArray1.getString("Name_Owner").toString());

                    Glide.with(ShowAmulet.this)
                            .load(ipaddress.getIp()+exArray1.getString("ImagePathAmulet1")+exArray1.getString("FrontPicture"))
                            .into(imageAmulet1);
                    Glide.with(ShowAmulet.this)
                            .load(ipaddress.getIp()+exArray1.getString("ImagePathAmulet2")+exArray1.getString("BackPicture"))
                            .into(imageAmulet2);

//                    imageAmulet1.setImageUrl(ipaddress.getIp()+exArray1.getString("ImagePathAmulet1")+exArray1.getString("FrontPicture"));
//                    imageAmulet2.setImageUrl(ipaddress.getIp()+exArray1.getString("ImagePathAmulet2")+exArray1.getString("BackPicture"));

                    exData2.add(exArray1.getString("ID_Amulet"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                btn_back1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

                btn_zoom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(ShowAmulet.this,Main5Activity.class);
                        i.putExtra("ID_Amulet", exData2.get(0));
                        ShowAmulet.this.startActivity(i);
                    }
                });


            }
        }.execute();






    }

}
