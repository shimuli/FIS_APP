package com.fairmontsinternational.charlie.fairmontsinternational;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fairmontsinternational.charlie.fairmontsinternational.Adapters.NotificationAdapter;
import com.fairmontsinternational.charlie.fairmontsinternational.Classes.BaseUrl;
import com.fairmontsinternational.charlie.fairmontsinternational.Classes.NotificationClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;
import pl.droidsonroids.gif.GifImageView;


public class NotificationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NotificationAdapter adapter;
    List<NotificationClass> notificationClasses;
    TextView noNotifications;
    GifImageView ImageView;


    private static String FETCH_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifictions);

        Paper.init(this);

        String today=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        FETCH_URL = BaseUrl.notifications(today);

        notificationClasses = new ArrayList<>();
        recyclerView = findViewById(R.id.note_view);
        noNotifications = findViewById(R.id.empty_view);
        ImageView = findViewById(R.id.gif_View);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Notifications...");
        progressDialog.show();


            StringRequest stringRequest = new StringRequest(Request.Method.GET, FETCH_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("Notification");



                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    notificationClasses.add(new NotificationClass
                                            (object.getString("NotificationFrom"), object.getString("Title"),
                                                    object.getString("Message")));
                                }

                                adapter = new NotificationAdapter(NotificationActivity.this, notificationClasses);
                                recyclerView.setAdapter(adapter);

                                    if (notificationClasses.isEmpty()){
                                        recyclerView.setVisibility(View.GONE);
                                        noNotifications.setVisibility(View.VISIBLE);
                                        ImageView.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        noNotifications.setVisibility(View.GONE);
                                        ImageView.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);


                            }
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    noNotifications.setVisibility(View.VISIBLE);

                    String message = null;
                    if (error instanceof NetworkError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (error instanceof ServerError) {
                        message = "The server could not be found. Please try again after some time!!";
                    } else if (error instanceof AuthFailureError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (error instanceof ParseError) {
                        message = "Parsing error! Please try again after some time!!";
                    } else if (error instanceof NoConnectionError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (error instanceof TimeoutError) {
                        message = "Connection TimeOut! Please check your internet connection.";
                    }
                    else {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }






    }

