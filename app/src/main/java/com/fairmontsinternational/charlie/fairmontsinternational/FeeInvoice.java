package com.fairmontsinternational.charlie.fairmontsinternational;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.fairmontsinternational.charlie.fairmontsinternational.Adapters.FeeInvoiceAdapter;
import com.fairmontsinternational.charlie.fairmontsinternational.Adapters.feesAdapter;
import com.fairmontsinternational.charlie.fairmontsinternational.Classes.BaseUrl;
import com.fairmontsinternational.charlie.fairmontsinternational.Classes.InvoiceFee;
import com.fairmontsinternational.charlie.fairmontsinternational.Classes.feesClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.paperdb.Paper;

public class FeeInvoice extends AppCompatActivity {

    RecyclerView recyclerView;
    FeeInvoiceAdapter adapter;
    List<InvoiceFee> feesClassList;
    TextView balance,total_invoiced, total_paid;
    TextView StudentID;
    public static String INVOICETOTALS_URL;
    private static String FETCHINVOICE_URL;
    private static String UID_URL;
    String student_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_invoice);

        balance=findViewById(R.id.inFees_balance);
        total_invoiced=findViewById(R.id.inFees_total_invoiced);
        total_paid=findViewById(R.id.inFees_total_paid);
        StudentID = findViewById(R.id.SUID);

        Paper.init(this);
        String admission_no;
        admission_no=Paper.book().read("admission_no").toString();
        INVOICETOTALS_URL= BaseUrl.fetchfeesinvoicetotals(admission_no);
        FETCHINVOICE_URL= BaseUrl.fetchfeeinvoice(admission_no);
        UID_URL = BaseUrl.fetchUID(admission_no);

        try{
            JSONObject obj = new JSONObject(UID_URL);
            JSONObject Student = obj.getJSONObject("FeeInvoice");
            student_id = Student.getString("StudentID");

            StudentID.setText(student_id);
        }catch(JSONException e){
            e.printStackTrace();
        }



        fetchfeebalance();

        feesClassList = new ArrayList<>();
        recyclerView=findViewById(R.id.infees_logs_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching fees records....");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, FETCHINVOICE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("FeeInvoice");

                            for(int i=0 ; i<jsonArray.length();i++){
                                JSONObject object=jsonArray.getJSONObject(i);
                                feesClassList.add(new InvoiceFee("PackageName: "+object.getString("PackageName"),"Payments Ksh:"+object.getString("Payments")
                                        ,"Paid Ksh: "+object.getString("Payments")
                                        ,"Balance Ksh: "+object.getString("Balance")+" "+object.getString("BalanceBf")
                                           ,"Balance Ksh:" +object.getString("Balance")));
                            }

                            adapter=new  FeeInvoiceAdapter(FeeInvoice.this,feesClassList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
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

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


    private void fetchfeebalance() {
        final JsonObjectRequest jsonObjectRequest2= new JsonObjectRequest(Request.Method.GET, INVOICETOTALS_URL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray2= response.getJSONArray("child");
                    String Balance= jsonArray2.get(0).toString();
                    String Total_invoiced= jsonArray2.get(1).toString();
                    String Total_paid= jsonArray2.get(2).toString();
                    balance.setText("KES "+Balance.replaceAll("\\.0*$", ""));
                    total_invoiced.setText("KES "+Total_invoiced.replaceAll("\\.0*$", ""));
                    total_paid.setText("KES "+Total_paid.replaceAll("\\.0*$", ""));
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
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest2);
    }

   /* private void StudentUID(){
        final JsonObjectRequest MyjsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                UID_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray MyJsonArray = response.getJSONArray("FeeInvoice");
                    student_id= MyJsonArray.get(0).toString();

                    StudentID.setText(student_id);
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
        MyjsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(MyjsonObjectRequest);
    } */

}
