package com.fairmontsinternational.charlie.fairmontsinternational;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.paperdb.Paper;

public class splashscreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    ProgressBar mProgressbar;
    String Parent_phone;
    private static  String FETCH_URL = "http://www.fairmontsinternationalschool.co.ke/fairmontsAPI/url.php";
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        prefs = getSharedPreferences("com.example.charlie.fairmontsinternational", MODE_PRIVATE);

        Paper.init(this);
        fetchurl();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean("firstrun", true)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String empty="";
                    Paper.book().write("Phone_number",empty);
                    startActivity(new Intent(splashscreen.this,Introduction.class));
                    finish();
                }
            },SPLASH_TIME_OUT);
            prefs.edit().putBoolean("firstrun", false).apply();
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkSession();
                }
            },SPLASH_TIME_OUT);
        }
    }

    private void checkSession() {
        Parent_phone=Paper.book().read("Phone_number").toString();
        if(Parent_phone.isEmpty()){
            startActivity(new Intent(splashscreen.this,LoginActivity.class));
            finish();
        }else{
            startActivity(new Intent(splashscreen.this,children_profiles.class));
            finish();
        }
    }

    private void fetchurl() {
        final JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, FETCH_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("User");
                            Paper.book().write("Main_url",jsonArray.get(0).toString());

                            //test platform
                            //String mainUrl="http://192.168.1.11:75/";
                            //Paper.book().write("Main_url",mainUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Invalid Credentials! Please try again!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }else{
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }
}
