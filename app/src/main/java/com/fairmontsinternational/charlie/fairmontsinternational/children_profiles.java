package com.fairmontsinternational.charlie.fairmontsinternational;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fairmontsinternational.charlie.fairmontsinternational.Adapters.profileAdapter;
import com.fairmontsinternational.charlie.fairmontsinternational.Classes.BaseUrl;
import com.fairmontsinternational.charlie.fairmontsinternational.Classes.profiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class children_profiles extends AppCompatActivity {
    RecyclerView recyclerView;
    profileAdapter adapter;
    List<profiles> profilesList;
    ProgressBar progressBar;

    private static String FETCH_URL;
    boolean doubleBackToExitPressedOnce = false;

    TextView records;
    Button refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_profiles);

        records=findViewById(R.id.Profiles_no_records_found);
        refresh=findViewById(R.id.Profiles_refresh_list);
        progressBar=findViewById(R.id.profilesProgress);

        records.setVisibility(View.GONE);
        refresh.setVisibility(View.GONE);

        Paper.init(this);
        String Parent_phone= Paper.book().read("Phone_number").toString();
//        FETCH_URL="http://fairmontsinternationalschool.co.ke/fairmontsAPI/fetchprofiles.php?parent_phone="+Parent_phone;
        FETCH_URL= BaseUrl.fetchProfiles(Parent_phone);


        profilesList= new ArrayList<>();
        recyclerView=findViewById(R.id.profiles_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadProfiles();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                records.setVisibility(View.GONE);
                refresh.setVisibility(View.GONE);
                loadProfiles();
            }
        });



    }

    private void loadProfiles() {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, FETCH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("Student");

                            for(int i=0 ; i<jsonArray.length();i++){
                                JSONObject object=jsonArray.getJSONObject(i);
                                profilesList.add(new profiles(object.getString("Name").toLowerCase(),object.getString("Admno")
                                        ,object.getString("CName").toLowerCase()));
                            }

                            adapter=new profileAdapter(children_profiles.this,profilesList);
                            recyclerView.setAdapter(adapter);


                        }catch (JSONException e){
                            Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                records.setVisibility(View.VISIBLE);
                refresh.setVisibility(View.VISIBLE);

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
                }else{
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                }

                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
