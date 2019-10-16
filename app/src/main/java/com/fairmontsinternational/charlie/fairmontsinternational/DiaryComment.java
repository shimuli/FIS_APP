package com.fairmontsinternational.charlie.fairmontsinternational;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.Volley;
import com.fairmontsinternational.charlie.fairmontsinternational.Classes.BaseUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.paperdb.Paper;

public class DiaryComment extends AppCompatActivity {
    Button save;
    TextView text_date,notes,teachers_coments,diary_id;
    EditText comments;
    String admission_no;
    String diaryId;
    private static String FETCH_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_comment);

        save=findViewById(R.id.Comment_saveed);
        text_date=findViewById(R.id.Comment_date_texted);
        notes=findViewById(R.id.Comment_notes_texted);
        teachers_coments=findViewById(R.id.Comment_teacher_texted);
        comments=findViewById(R.id.Comment_texted);
        diary_id=findViewById(R.id.diaryided);

        Intent intent=getIntent();
        final String date=intent.getStringExtra("Date");
        diaryId=intent.getStringExtra("DiaryId");
        String Diary_Title=intent.getStringExtra("ExtraCurriculaName");
        String Tcomment=intent.getStringExtra("TeacherComment");
        String Pcomment=intent.getStringExtra("ParentComment");



        text_date.setText(date);
        notes.setText(Diary_Title);
        teachers_coments.setText(Tcomment);
        comments.setText(Pcomment);

        Paper.init(this);
        admission_no=Paper.book().read("admission_no").toString();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dated=text_date.getText().toString().trim();
                String Comment=comments.getText().toString().trim();
                if(Comment.isEmpty()){
                    Snackbar.make(v,"Write a comment first!!",Snackbar.LENGTH_LONG).show();
                }else{
//
                    //FETCH_URL= BaseUrl.addDiaryComment(diaryId,dated,Comment);
                    // FETCH_URL= FETCH_URL.replaceAll(" " ,"%20");

                    execute();
                }
            }
        });
    }

    private void execute() {

        final ProgressDialog progressDialog=new ProgressDialog(DiaryComment.this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, FETCH_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONArray jsonArray=response.getJSONArray("Comment");
                            String Status=jsonArray.get(0).toString();

                            switch (Status){
                                case"Updated":
                                    Toast.makeText(getApplicationContext(),"Comment Saved!",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(DiaryComment.this,Extra_Diary.class));
                                    finish();
                                    break;
                                case"Failed":
                                    Toast.makeText(getApplicationContext(),"Comment not saved!.",Toast.LENGTH_LONG).show();
                                    break;
                            }

                        } catch (JSONException e) {
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

    private void postcomment() {
        final JsonObjectRequest jsonObjectRequest2= new JsonObjectRequest(Request.Method.GET, FETCH_URL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("Comment");
                    String Status=jsonArray.get(0).toString();
                    switch (Status){
                        case"Updated":
                            Toast.makeText(getApplicationContext(),"Comment Saved!",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(DiaryComment.this,Extra_Diary.class));
                            finish();
                            break;
                        case"Failed":
                            Toast.makeText(getApplicationContext(),"Comment not saved!.",Toast.LENGTH_LONG).show();
                            break;
                    }
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
        jsonObjectRequest2.setRetryPolicy(new DefaultRetryPolicy(
                35000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DiaryComment.this,Extra_Diary.class));
        finish();
    }

}
