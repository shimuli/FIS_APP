package com.fairmontsinternational.charlie.fairmontsinternational;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
import com.android.volley.toolbox.Volley;
import com.fairmontsinternational.charlie.fairmontsinternational.Classes.BaseUrl;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {


    public static String LOGIN_URL;
    MaterialEditText Phone, Password;
    Button Btnlogin;
    TextView CreateAccount;
    ProgressBar progressBar;
    ImageView show;
    int setType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);

        setType=1;
        setContentView(R.layout.activity_login);
        Phone=findViewById(R.id.LoginPhoneNo);
        Password=findViewById(R.id.LoginPassword);
        Btnlogin=findViewById(R.id.login_button);
        CreateAccount=findViewById(R.id.login_create_account);
        show=findViewById(R.id.LoginShowPassword);
        progressBar=findViewById(R.id.loginprogress);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setType==1) {
                    setType = 0;
                    Password.setTransformationMethod(null);
                    if (Password.getText().length() > 0)
                        Password.setSelection(Password.getText().length());
                }else{
                        setType=1;
                        Password.setTransformationMethod(new PasswordTransformationMethod());
                        if(Password.getText().length() > 0)
                            Password.setSelection(Password.getText().length());
                    }
                }
        });

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,CreatePassword.class));
                finish();
            }
        });

        Btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void validate() {
        String phone_no, password;
        phone_no= Phone.getText().toString().trim();
        password=Password.getText().toString().trim();
        if(phone_no.isEmpty()){
            Phone.setError("Email is Required");
        }else if(password.isEmpty()){
            Password.setError("Password is Required");
        }else{
//            LOGIN_URL="http://fairmontsinternationalschool.co.ke/fairmontsAPI/login.php?phone_no="+phone_no+"&password="+password+"";
            LOGIN_URL= BaseUrl.login(phone_no,password);
            login();
        }
    }

    private void login() {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.VISIBLE);


        final JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, LOGIN_URL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                try{
                    JSONArray jsonArray=response.getJSONArray("User");
//                    String parent_id=jsonArray.get(0).toString();
//                    Paper.book().write("Parent_id",parent_id);
                    Paper.book().write("Phone_number",Phone.getText().toString());
                    Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,children_profiles.class));
                    finish();
                }catch(JSONException e){
                    Toast.makeText(getApplicationContext(),"Login Failed! Try again",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

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
    }
}
