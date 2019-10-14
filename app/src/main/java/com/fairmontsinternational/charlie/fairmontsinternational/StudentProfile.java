package com.fairmontsinternational.charlie.fairmontsinternational;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class StudentProfile extends AppCompatActivity {

    TextView studentName,system_enrolled,level,fee,gender,studentNumber,dob,parent_name,other_parent_name,parent_id_no,other_parent_id_no,
            parent_phone,other_parent_phone,guardian_name,guardian_id_no,guardian_contact,home_address;
    String student_names, student_level,student_system,student_gender,student_dob,student_parent_name,student_other_parent_name,student_parent_id_no,
            student_other_parent_id_no,student_parent_phone,student_other_parent_phone,student_guardian_name,student_guardian_id_no,
            student_guardian_contact,student_home_address;

    TextView ProviderName, ProviderPhone, ContactPerson, HomeLocation, HomeAddress, HomeDesc, ItemDate, ItemName, ItemQuantity,
            DocName, DocDesc,  student_UID;

    String providerName, providerPhone, contactPerson, homeLocation, homeAddress, homeDesc, itemDate, itemName, itemQuantity,
            docName, docDesc;
    public static String FETCH_URL, HEALTH_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        dob=findViewById(R.id.Profile_student_dob);
        parent_name=findViewById(R.id.Profile_student_parent_name);
        other_parent_name=findViewById(R.id.Profile_student_other_parent_name);
        parent_id_no=findViewById(R.id.Profile_student_parent_id_no);
        other_parent_id_no=findViewById(R.id.Profile_student_other_parent_id_no);
        parent_phone=findViewById(R.id.Profile_student_parent_phone);
        other_parent_phone=findViewById(R.id.Profile_student_other_parent_phone);
        guardian_name=findViewById(R.id.Profile_student_guardian_name);
        guardian_id_no=findViewById(R.id.Profile_student_guardian_id);
        guardian_contact=findViewById(R.id.Profile_student_guardian_contact);
        home_address=findViewById(R.id.Profile_student_home_address);
        studentName=findViewById(R.id.Profile_student_Name);
        system_enrolled=findViewById(R.id.Profile_student_system);
        level=findViewById(R.id.Profile_student_level);
        gender=findViewById(R.id.Profile_student_gender);
        studentNumber=findViewById(R.id.Profile_student_admission_no);
        student_UID = findViewById(R.id.Profile_SUID);
        ProviderName= findViewById(R.id.Profile_provider_name);
        ProviderPhone= findViewById(R.id.Profile_provider_phone);
        ContactPerson = findViewById(R.id.Profile_contact_person_provider);
        HomeLocation = findViewById(R.id.Profile_home_location);
        HomeAddress = findViewById(R.id.Profile_home_address);
        HomeDesc = findViewById(R.id.Profile_home_desc);
        ItemDate = findViewById(R.id.Profile_item_date);
        ItemName = findViewById(R.id.Profile_item_name);
        ItemQuantity = findViewById(R.id.Profile_item_quantity);
        DocName = findViewById(R.id.Profile_doc_name);
        DocDesc = findViewById(R.id.Profile_doc_desc);

        Paper.init(this);
        String admission_no=Paper.book().read("admission_no").toString();
//        FETCH_URL="http://fairmontsinternationalschool.co.ke/fairmontsAPI/fetchsingleprofile.php?admission_no="+admission_no;
        HEALTH_URL = BaseUrl.fetchHealthDetails(admission_no);
        FETCH_URL= BaseUrl.fetchsingleprofile(admission_no);


        HealthDetails();

        //  Waiting for APIend point to complete the fetching of student SUID

        //     Paper.init(this);
        //     String Student_id = Paper.book().read("Student_id").toString();
        //     FETCH_URL = BaseUrl.fetchsingleprofileID(Student_id);

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching student profiles.");
        progressDialog.show();




        final JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, FETCH_URL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    JSONArray jsonArray= response.getJSONArray("child");
                    student_names= jsonArray.get(0).toString();
                    student_level= jsonArray.get(1).toString();
                    student_system= jsonArray.get(2).toString();
                    student_gender= jsonArray.get(3).toString();
                    student_dob=jsonArray.get(4).toString();
                    student_parent_name=jsonArray.get(5).toString();
                    student_other_parent_name=jsonArray.get(6).toString();
                    student_parent_id_no=jsonArray.get(7).toString();
                    student_other_parent_id_no=jsonArray.get(8).toString();
                    student_parent_phone=jsonArray.get(9).toString();
                    student_other_parent_phone=jsonArray.get(10).toString();
                    student_guardian_name=jsonArray.get(11).toString();
                    student_guardian_contact=jsonArray.get(12).toString();
                    student_guardian_id_no=jsonArray.get(13).toString();
                    student_home_address=jsonArray.get(14).toString();

                    //  No End Points
                    /* providerName = jsonArray.get(15).toString();
                    providerPhone = jsonArray.get(16).toString();
                    contactPerson = jsonArray.get(17).toString();
                    homeLocation = jsonArray.get(18).toString();
                    homeAddress = jsonArray.get(19).toString();
                    homeDesc = jsonArray.get(20).toString();
                    itemDate = jsonArray.get(21).toString();
                    itemName = jsonArray.get(22).toString();
                    itemQuantity = jsonArray.get(23).toString();
                    docName =jsonArray.get(24).toString();
                    docDesc = jsonArray.get(25).toString(); */

                    parent_name.setText(student_parent_name);
                    other_parent_name.setText(student_other_parent_name);
                    parent_id_no.setText(student_parent_id_no);
                    other_parent_id_no.setText(student_other_parent_id_no);
                    parent_phone.setText(student_parent_phone);
                    other_parent_phone.setText(student_other_parent_phone);
                    guardian_name.setText(student_guardian_name);
                    guardian_contact.setText(student_guardian_contact);
                    guardian_id_no.setText(student_guardian_id_no);
                    home_address.setText(student_home_address);
                    studentName.setText(student_names);
                    system_enrolled.setText(student_system);
                    level.setText(student_level);
                    gender.setText(student_gender);
                    dob.setText(student_dob);
                    HomeLocation.setText(homeLocation);
                    HomeAddress.setText(homeAddress);
                    HomeDesc.setText(homeDesc);
                    ItemDate.setText(itemDate);
                    ItemName.setText(itemName);
                    ItemQuantity.setText(itemQuantity);
                    DocName.setText(docName);
                    DocDesc.setText(docDesc);

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
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

        studentNumber.setText(admission_no);
    }

    private void HealthDetails(){
        final JsonObjectRequest jsonObjectRequest1= new JsonObjectRequest(Request.Method.GET,HEALTH_URL ,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray= response.getJSONArray("Student");

                    providerName = jsonArray.get(3).toString();
                    providerPhone = jsonArray.get(5).toString();
                    contactPerson = jsonArray.get(6).toString();

                    ProviderName.setText(providerName);
                    ProviderPhone.setText(providerPhone);
                    ContactPerson.setText(contactPerson);


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

        jsonObjectRequest1.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue1= Volley.newRequestQueue(this);
        requestQueue1.add(jsonObjectRequest1);
    }

}
