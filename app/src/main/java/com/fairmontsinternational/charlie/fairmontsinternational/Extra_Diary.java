package com.fairmontsinternational.charlie.fairmontsinternational;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
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
import com.fairmontsinternational.charlie.fairmontsinternational.Adapters.ExtraDiaryAdapter;
import com.fairmontsinternational.charlie.fairmontsinternational.Classes.BaseUrl;
import com.fairmontsinternational.charlie.fairmontsinternational.Classes.ExtraDiaryClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Extra_Diary extends AppCompatActivity {
    RecyclerView recyclerView, recyclerView2;
    ExtraDiaryAdapter extraDiaryAdapter;
    ExtraDiaryAdapter extraDiaryAdapter1;
    List<ExtraDiaryClass>extraDiaryClasses;
    List<ExtraDiaryClass>extraDiaryClasses1;

    private static String FETCH_URL;
    private static String SEARCH_URL;
    LinearLayout searchlayout;
    TextView previous_reports,close,from_date,to_date,no_reports;
    Button from_button,to_button,filter;
    String admission_no;
    Calendar cal;
    DatePickerDialog datedialog;
    final Calendar mycalendar= Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra__diary);

        Paper.init(this);
        String admission_no;
        admission_no=Paper.book().read("admission_no").toString();
        FETCH_URL = BaseUrl.fetchDiary(admission_no);

        searchlayout=findViewById(R.id.Diary_search_layout);
        previous_reports=findViewById(R.id.Diary_previous_reports);
        close=findViewById(R.id.Diary_search_close);
        from_date=findViewById(R.id.Diary_Start_date_text);
        to_date=findViewById(R.id.Diary_End_date_text);
        from_button=findViewById(R.id.Diary_Start_date_button);
        to_button=findViewById(R.id.Diary_End_date_button);
        no_reports=findViewById(R.id.Diary_no_reports);
        recyclerView=findViewById(R.id.Diary_Recyclerview);
        filter=findViewById(R.id.Diary_filter_dates);

        extraDiaryClasses = new ArrayList<>();
        extraDiaryClasses1 =new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_extra);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView2 = findViewById(R.id.Diary_Recyclerview_all_search);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start_date=from_date.getText().toString().trim();
                String end_date=to_date.getText().toString().trim();
                if(start_date.equals("0000-00-00")){
                    Snackbar.make(view,"Select both dates first!!",Snackbar.LENGTH_LONG).show();
                }else if(end_date.equals("0000-00-00")){
                    Snackbar.make(view,"Select both dates first!!",Snackbar.LENGTH_LONG).show();
                }else{
                    filter();
                }

            }
        });

        final DatePickerDialog.OnDateSetListener date1= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mycalendar.set(Calendar.YEAR,year);
                mycalendar.set(Calendar.MONTH,month);
                mycalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updatefrom();
            }
        };

        final DatePickerDialog.OnDateSetListener date2= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mycalendar.set(Calendar.YEAR,year);
                mycalendar.set(Calendar.MONTH,month);
                mycalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateTo();
            }
        };

        from_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Extra_Diary.this,date1,mycalendar
                        .get(Calendar.YEAR),mycalendar.get(Calendar.MONTH),mycalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        to_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Extra_Diary.this,date2,mycalendar
                        .get(Calendar.YEAR),mycalendar.get(Calendar.MONTH),mycalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        previous_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchlayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.VISIBLE);
                previous_reports.setVisibility(View.GONE);
                no_reports.setVisibility(View.GONE);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchlayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.GONE);
                previous_reports.setVisibility(View.VISIBLE);
                no_reports.setVisibility(View.GONE);
            }
        });


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Diary...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, FETCH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Report");

                            for (int d = 0; d < jsonArray.length(); d++) {
                                JSONObject object = jsonArray.getJSONObject(d);
                                extraDiaryClasses.add(new ExtraDiaryClass("Title: " + object.getString("ExtraCurriculaName"),
                                        "Date: " + object.getString("Date"), object.getString("DiaryId"),object.getString("TeacherComment"),
                                        object.getString("ParentComment")));

                            }
                            extraDiaryAdapter = new ExtraDiaryAdapter(Extra_Diary.this, extraDiaryClasses);
                            recyclerView.setAdapter(extraDiaryAdapter);


                        } catch (JSONException j) {
                            Toast.makeText(getApplicationContext(), j.getMessage(), Toast.LENGTH_SHORT).show();
                            j.printStackTrace();
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

    private void updatefrom() {
        String myformat= "yyyy-MM-dd";
        SimpleDateFormat sdf=new SimpleDateFormat(myformat, Locale.US);
        from_date.setText(sdf.format(mycalendar.getTime()));
    }

    private void updateTo() {
        String format= "yyyy-MM-dd";
        SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.US);
        to_date.setText(sdf.format(mycalendar.getTime()));
    }

    private void filter() {
        recyclerView.setVisibility(View.GONE);
        recyclerView2.setVisibility(View.VISIBLE);

        final ProgressDialog progressDialog=new ProgressDialog(Extra_Diary.this);
        progressDialog.setMessage("Fetching Reports..");
        progressDialog.show();

        String start_date=from_date.getText().toString().trim();
        String end_date=to_date.getText().toString().trim();

        SEARCH_URL=BaseUrl.searchDiary(admission_no,start_date,end_date);
        StringRequest stringRequest2=new StringRequest(Request.Method.GET, SEARCH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        no_reports.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject2=new JSONObject(response);
                            JSONArray jsonArray2=jsonObject2.getJSONArray("Report");

                            for(int i=0 ; i<jsonArray2.length();i++){
                                JSONObject object=jsonArray2.getJSONObject(i);
                                extraDiaryClasses1.add(new ExtraDiaryClass(object.getString("DiaryId"),object.getString("Date")
                                        ,object.getString("ExtraCurriculaName"),object.getString("TeacherComment"),object.getString("ParentComment")));
                            }

                            extraDiaryAdapter1=new  ExtraDiaryAdapter(Extra_Diary.this,extraDiaryClasses1);
                            recyclerView2.setAdapter( extraDiaryAdapter1);

                        } catch (JSONException e) {
                            extraDiaryClasses1.clear();
                            no_reports.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

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

        RequestQueue requestQueue2= Volley.newRequestQueue(this);
        requestQueue2.add(stringRequest2);

    }

}

