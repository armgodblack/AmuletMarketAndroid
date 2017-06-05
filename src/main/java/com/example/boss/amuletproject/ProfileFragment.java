package com.example.boss.amuletproject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.camera2.params.Face;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.target.Target;

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
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.image.SmartImageView;

import static com.example.boss.amuletproject.JSONParser.json;
import static com.example.boss.amuletproject.R.id.imageAmuletNo1;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    ipaddress ipaddress = new ipaddress();
    private String TAG = MarketFragment.class.getSimpleName();

    TextView Phone,Name_Owner,Name_Shop,Line,Facebook,Detail_Shop,MarketProfile;
    TextView NameAmuletNo1,GenAmuletNo1;
    TextView NameAmuletNo2,GenAmuletNo2;
    TextView NameAmuletNo3,GenAmuletNo3;
    TextView NameAmuletNo4,GenAmuletNo4;
    TextView NameAmuletNo5,GenAmuletNo5;
    TextView NameAmuletNo6,GenAmuletNo6;
    TextView NameAmuletNo7,GenAmuletNo7;
    TextView NameAmuletNo8,GenAmuletNo8;
    TextView NameAmuletNo9,GenAmuletNo9;
    TextView NameAmuletNo10,GenAmuletNo10;

    ImageView Mapping;
    ArrayList<String> exData1,exData2,exData3;

    SmartImageView imageAmuletNo1,imageAmuletNo2,imageAmuletNo3,imageAmuletNo4,imageAmuletNo5,
                   imageAmuletNo6,imageAmuletNo7,imageAmuletNo8,imageAmuletNo9,imageAmuletNo10,
                   PicFontShop,LogoShop;

    private ImageView photofontshop,bartop10;
    protected ListView ListAmuletShop;
    private ArrayList<String> DataShop;
    private ProgressDialog progressDialog;



    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        exData2 = new ArrayList<String>();

        View rootView = inflater.inflate(R.layout.profile_activity, container, false);


        Name_Shop = (TextView) rootView.findViewById(R.id.Name_Shop);
        Name_Owner = (TextView) rootView.findViewById(R.id.Name_Owner);
        Phone = (TextView) rootView.findViewById(R.id.Phone);
        Line = (TextView) rootView.findViewById(R.id.Line);
        Facebook = (TextView) rootView.findViewById(R.id.Facebook);
        Detail_Shop = (TextView) rootView.findViewById(R.id.Detail_Shop);
        MarketProfile = (TextView) rootView.findViewById(R.id.MarketProfile);

        NameAmuletNo1= (TextView) rootView.findViewById(R.id.NameAmuletNo1);
        GenAmuletNo1=(TextView) rootView.findViewById(R.id.GenAmuletNo1);

        NameAmuletNo2= (TextView) rootView.findViewById(R.id.NameAmuletNo2);
        GenAmuletNo2=(TextView) rootView.findViewById(R.id.GenAmuletNo2);

        NameAmuletNo3= (TextView) rootView.findViewById(R.id.NameAmuletNo3);
        GenAmuletNo3=(TextView) rootView.findViewById(R.id.GenAmuletNo3);

        NameAmuletNo4= (TextView) rootView.findViewById(R.id.NameAmuletNo4);
        GenAmuletNo4=(TextView) rootView.findViewById(R.id.GenAmuletNo4);

        NameAmuletNo5= (TextView) rootView.findViewById(R.id.NameAmuletNo5);
        GenAmuletNo5=(TextView) rootView.findViewById(R.id.GenAmuletNo5);

        NameAmuletNo6= (TextView) rootView.findViewById(R.id.NameAmuletNo6);
        GenAmuletNo6=(TextView) rootView.findViewById(R.id.GenAmuletNo6);

        NameAmuletNo7= (TextView) rootView.findViewById(R.id.NameAmuletNo7);
        GenAmuletNo7=(TextView) rootView.findViewById(R.id.GenAmuletNo7);

        NameAmuletNo8= (TextView) rootView.findViewById(R.id.NameAmuletNo8);
        GenAmuletNo8=(TextView) rootView.findViewById(R.id.GenAmuletNo8);

        NameAmuletNo9= (TextView) rootView.findViewById(R.id.NameAmuletNo9);
        GenAmuletNo9=(TextView) rootView.findViewById(R.id.GenAmuletNo9);

        NameAmuletNo10= (TextView) rootView.findViewById(R.id.NameAmuletNo10);
        GenAmuletNo10=(TextView) rootView.findViewById(R.id.GenAmuletNo10);

        PicFontShop =(SmartImageView) rootView.findViewById(R.id.PicFontShop);
        LogoShop = (SmartImageView) rootView.findViewById(R.id.LogoShop);

        imageAmuletNo1 =(SmartImageView) rootView.findViewById(R.id.imageAmuletNo1);
        imageAmuletNo2 =(SmartImageView) rootView.findViewById(R.id.imageAmuletNo2);
        imageAmuletNo3 =(SmartImageView) rootView.findViewById(R.id.imageAmuletNo3);
        imageAmuletNo4 =(SmartImageView) rootView.findViewById(R.id.imageAmuletNo4);
        imageAmuletNo5 =(SmartImageView) rootView.findViewById(R.id.imageAmuletNo5);
        imageAmuletNo6 =(SmartImageView) rootView.findViewById(R.id.imageAmuletNo6);
        imageAmuletNo7 =(SmartImageView) rootView.findViewById(R.id.imageAmuletNo7);
        imageAmuletNo8 =(SmartImageView) rootView.findViewById(R.id.imageAmuletNo8);
        imageAmuletNo9 =(SmartImageView) rootView.findViewById(R.id.imageAmuletNo9);
        imageAmuletNo10 =(SmartImageView) rootView.findViewById(R.id.imageAmuletNo10);

        Mapping = (ImageView)rootView.findViewById(R.id.Mapping);

        DataShop = new ArrayList<String>();




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
            protected JSONObject doInBackground(Object... voids) {
                JSONObject exArray1 = null;
                try{
                    Intent intent = getActivity().getIntent();
                    if (intent.getExtras() != null){
                        String idprofile = intent.getStringExtra("ID_Shop");
                        Log.d("","ID_Shop : "+idprofile);

                        URL url = new URL(ipaddress.getIp()+"amuletmarket/JSON_shop.php?ID_Shop="+idprofile);

//                    Log.d("URL",url.toString());

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


//                        JSONObject jsonObject = new JSONObject(json);

                        JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                        JSONObject exArray = jsonObject.getJSONObject("Data");
                        exArray1 = exArray.getJSONObject("Dataprofile");
                        exArray1 = exArray.getJSONObject("PicAmulet");
//                        exArray1 = exArray.getJSONObject("Market");


                        for (int i = 0; i < exArray1.length(); i++) {
                            JSONObject jsonObj = exArray1.getJSONObject(String.valueOf(0));
                            exData2.add(jsonObj.getString("id_amuletmarket"));
                            Log.e("WWWWWWWWWWWWWWW"+exArray1.toString(),"");



                        }






                    }

                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e){
                    e.printStackTrace();
                }

                return exArray1;
            }
            @Override
            protected void onPostExecute(final JSONObject exArray1 ) {
                Log.d("POST","Result"+exArray1.toString());
                super.onPostExecute(exArray1);
                progressDialog.dismiss();
                try{
                    Name_Shop.setText(exArray1.getString("Name_Shop").toString());
                    Name_Owner.setText(exArray1.getString("Name_Owner").toString());
                    Phone.setText(exArray1.getString("Phone").toString());
                    Line.setText(exArray1.getString("Line").toString());
                    Facebook.setText(exArray1.getString("Facebook").toString());
                    Detail_Shop.setText(exArray1.getString("Detail_Shop"));
                    MarketProfile.setText(exArray1.getString("Name_Market").toString());

                    NameAmuletNo1.setText(exArray1.getString("NameAmulet1"));
                    GenAmuletNo1.setText(exArray1.getString("GenAmulet1"));

                    NameAmuletNo2.setText(exArray1.getString("NameAmulet2"));
                    GenAmuletNo2.setText(exArray1.getString("GenAmulet2"));

                    NameAmuletNo3.setText(exArray1.getString("NameAmulet3"));
                    GenAmuletNo3.setText(exArray1.getString("GenAmulet3"));

                    NameAmuletNo4.setText(exArray1.getString("NameAmulet4"));
                    GenAmuletNo4.setText(exArray1.getString("GenAmulet4"));

                    NameAmuletNo5.setText(exArray1.getString("NameAmulet5"));
                    GenAmuletNo5.setText(exArray1.getString("GenAmulet5"));

                    NameAmuletNo6.setText(exArray1.getString("NameAmulet6"));
                    GenAmuletNo6.setText(exArray1.getString("GenAmulet6"));

                    NameAmuletNo7.setText(exArray1.getString("NameAmulet7"));
                    GenAmuletNo7.setText(exArray1.getString("GenAmulet7"));

                    NameAmuletNo8.setText(exArray1.getString("NameAmulet8"));
                    GenAmuletNo8.setText(exArray1.getString("GenAmulet8"));

                    NameAmuletNo9.setText(exArray1.getString("NameAmulet9"));
                    GenAmuletNo9.setText(exArray1.getString("GenAmulet9"));

                    NameAmuletNo10.setText(exArray1.getString("NameAmulet10"));
                    GenAmuletNo10.setText(exArray1.getString("GenAmulet10"));

                    Glide.with(getContext())
                            .load(ipaddress.getIp()+exArray1.getString("ImageShopPath")+exArray1.getString("Picture_Shop"))
                            .into(PicFontShop);
                    Glide.with(getContext())
                            .load(ipaddress.getIp()+exArray1.getString("ImageLogoPath")+exArray1.getString("Img_Logo"))
                            .into(LogoShop);

//                    PicFontShop.setImageUrl(ipaddress.getIp()+exArray1.getString("ImageShopPath")+exArray1.getString("Picture_Shop"));
//                    LogoShop.setImageUrl(ipaddress.getIp()+exArray1.getString("ImageLogoPath")+exArray1.getString("Img_Logo"));

                    Glide.with(getContext())
                            .load(ipaddress.getIp()+exArray1.getString("ImagePath1")+exArray1.getString("ImgAmulet1"))
                            .into(imageAmuletNo1);
                    Glide.with(getContext())
                            .load(ipaddress.getIp()+exArray1.getString("ImagePath2")+exArray1.getString("ImgAmulet2"))
                            .into(imageAmuletNo2);
                    Glide.with(getContext())
                            .load(ipaddress.getIp()+exArray1.getString("ImagePath3")+exArray1.getString("ImgAmulet3"))
                            .into(imageAmuletNo3);
                    Glide.with(getContext())
                            .load(ipaddress.getIp()+exArray1.getString("ImagePath4")+exArray1.getString("ImgAmulet4"))
                            .into(imageAmuletNo4);
                    Glide.with(getContext())
                            .load(ipaddress.getIp()+exArray1.getString("ImagePath5")+exArray1.getString("ImgAmulet5"))
                            .into(imageAmuletNo5);
                    Glide.with(getContext())
                            .load(ipaddress.getIp()+exArray1.getString("ImagePath6")+exArray1.getString("ImgAmulet6"))
                            .into(imageAmuletNo6);
                    Glide.with(getContext())
                            .load(ipaddress.getIp()+exArray1.getString("ImagePath7")+exArray1.getString("ImgAmulet7"))
                            .into(imageAmuletNo7);
                    Glide.with(getContext())
                            .load(ipaddress.getIp()+exArray1.getString("ImagePath8")+exArray1.getString("ImgAmulet8"))
                            .into(imageAmuletNo8);
                    Glide.with(getContext())
                            .load(ipaddress.getIp()+exArray1.getString("ImagePath9")+exArray1.getString("ImgAmulet9"))
                            .into(imageAmuletNo9);
                    Glide.with(getContext())
                            .load(ipaddress.getIp()+exArray1.getString("ImagePath10")+exArray1.getString("ImgAmulet10"))
                            .into(imageAmuletNo10);

//                    imageAmuletNo1.setImageUrl(ipaddress.getIp()+exArray1.getString("ImagePath1")+exArray1.getString("ImgAmulet1"));
//                    imageAmuletNo2.setImageUrl(ipaddress.getIp()+exArray1.getString("ImagePath2")+exArray1.getString("ImgAmulet2"));
//                    imageAmuletNo3.setImageUrl(ipaddress.getIp()+exArray1.getString("ImagePath3")+exArray1.getString("ImgAmulet3"));
//                    imageAmuletNo4.setImageUrl(ipaddress.getIp()+exArray1.getString("ImagePath4")+exArray1.getString("ImgAmulet4"));
//                    imageAmuletNo5.setImageUrl(ipaddress.getIp()+exArray1.getString("ImagePath5")+exArray1.getString("ImgAmulet5"));
//                    imageAmuletNo6.setImageUrl(ipaddress.getIp()+exArray1.getString("ImagePath6")+exArray1.getString("ImgAmulet6"));
//                    imageAmuletNo7.setImageUrl(ipaddress.getIp()+exArray1.getString("ImagePath7")+exArray1.getString("ImgAmulet7"));
//                    imageAmuletNo8.setImageUrl(ipaddress.getIp()+exArray1.getString("ImagePath8")+exArray1.getString("ImgAmulet8"));
//                    imageAmuletNo9.setImageUrl(ipaddress.getIp()+exArray1.getString("ImagePath9")+exArray1.getString("ImgAmulet9"));
//                    imageAmuletNo10.setImageUrl(ipaddress.getIp()+exArray1.getString("ImagePath10")+exArray1.getString("ImgAmulet10"));

                    Log.d("TTTTTTTT ", "" + exArray1.toString().substring(1000));

                    exData2.add(exArray1.getString("ID_Shop"));
                    Log.d("BBBBBB"+exData2.toString(),"");

                }catch (JSONException e){
                    e.printStackTrace();
                }
                Mapping.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(getActivity(),Main4Activity.class);
                        i.putExtra("ID_Shop", exData2.get(0));
                        getActivity().startActivity(i);


                    }
                });
            }

        }.execute();

        return rootView;
    }

}