package com.fairmontsinternational.charlie.fairmontsinternational;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.fairmontsinternational.charlie.fairmontsinternational.Classes.BaseUrl;

public class ForgetPassword extends AppCompatActivity {

    Button SubmitPassword;
    EditText PhoneNumber, NewPassword, ConfirmPassword;
    ImageView showPassword, showConfirmPassword;
    private static String URL;
    int setType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        setType = 1;

        SubmitPassword = findViewById(R.id.UserChangePassword);
        PhoneNumber=findViewById(R.id.phoneNo);
        NewPassword = findViewById(R.id.UsernewPassword);
        ConfirmPassword = findViewById(R.id.UserConfirmNewPassword);
        showPassword = findViewById(R.id.ShowNewPassword);
        showConfirmPassword =findViewById(R.id.ShowConfirmpassword);

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setType==1) {
                    setType = 0;
                    NewPassword.setTransformationMethod(null);
                    if (NewPassword.getText().length() > 0)
                        NewPassword.setSelection(NewPassword.getText().length());
                }else{
                    setType=1;
                    NewPassword.setTransformationMethod(new PasswordTransformationMethod());
                    if(NewPassword.getText().length() > 0)
                        NewPassword.setSelection(NewPassword.getText().length());
                }
            }
        });

        showConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setType==1) {
                    setType = 0;
                    ConfirmPassword.setTransformationMethod(null);
                    if (ConfirmPassword.getText().length() > 0)
                        ConfirmPassword.setSelection(ConfirmPassword.getText().length());
                }else{
                    setType=1;
                    ConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                    if(ConfirmPassword.getText().length() > 0)
                        ConfirmPassword.setSelection(ConfirmPassword.getText().length());
                }
            }
        });

        SubmitPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });



    }
    private void validate() {
        String phoneNum = PhoneNumber.getText().toString().trim();
        String NewPass = NewPassword.getText().toString().trim();
        String ConfirmPass = ConfirmPassword.getText().toString().trim();

        if (phoneNum.isEmpty()){
            PhoneNumber.setError("Enter Phone Number");
        }
        else if(NewPass.isEmpty()){
            NewPassword.setError("Kindly Enter New Password");
        }
        else if (ConfirmPass.isEmpty()){
            ConfirmPassword.setError("Please Re-enter New Password");
        }
        else {
            if(NewPass.equals(ConfirmPass)){
                URL = BaseUrl.forgotPassword(phoneNum, NewPass);
                update();
            }
            else{
                ConfirmPassword.setError("Password don't Match");
            }
        }
    }

    private void update(){
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Updated")){
                Toast.makeText(getApplicationContext(), "New Password Updated Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ForgetPassword.this, LoginActivity.class));
                finish();
                }
                else if(response.contains("Incorrect")){
                    Toast.makeText(getApplicationContext(), "Invalid Phone Number", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgetPassword.this, ForgetPassword.class));
                    finish();
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
        RequestQueue requestQueue = Volley.newRequestQueue(ForgetPassword.this);
        requestQueue.add(request);
    }
}
