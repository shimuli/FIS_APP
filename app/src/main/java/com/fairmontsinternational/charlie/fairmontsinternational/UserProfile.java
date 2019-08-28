package com.fairmontsinternational.charlie.fairmontsinternational;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.fairmontsinternational.charlie.fairmontsinternational.Classes.BaseUrl;

import io.paperdb.Paper;

public class UserProfile extends AppCompatActivity {
    TextView name, phone;
    EditText oldpassword,newpassword,confirmpassword;
    ImageView showoldpass;
    Button updatePass;
    String phone_no;
    private static String URL;
    int setType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        setType=1;

        oldpassword=findViewById(R.id.UserOldPassword);
        newpassword=findViewById(R.id.UserNewPassword);
        confirmpassword=findViewById(R.id.UserConfirmPassword);
        showoldpass=findViewById(R.id.UserShowOldPassword);
        updatePass=findViewById(R.id.UserChangePass);

        Intent intent=getIntent();
        String parent_name=intent.getStringExtra("parent_name");
        phone_no=intent.getStringExtra("phoneNo");

        name=findViewById(R.id.UserPname);
        phone=findViewById(R.id.UserPhone);
        name.setText(parent_name);
        phone.setText(phone_no);

        updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        showoldpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setType==1) {
                    setType = 0;
                    oldpassword.setTransformationMethod(null);
                    if (oldpassword.getText().length() > 0)
                        oldpassword.setSelection(oldpassword.getText().length());
                }else{
                    setType=1;
                    oldpassword.setTransformationMethod(new PasswordTransformationMethod());
                    if(oldpassword.getText().length() > 0)
                        oldpassword.setSelection(oldpassword.getText().length());
                }
            }
        });
    }

    private void validate() {
        String old_pass=oldpassword.getText().toString().trim();
        String new_pass=newpassword.getText().toString().trim();
        String confirm_pass=confirmpassword.getText().toString().trim();

        if(old_pass.isEmpty()){
            oldpassword.setError("Kindly enter your current password");
        }else if(new_pass.isEmpty()){
            newpassword.setError("Kindly enter your new password");
        }else if(confirm_pass.isEmpty()){
            confirmpassword.setError("Kindly enter your new password");
        }else{
            if(new_pass.equals(confirm_pass)){
//                URL="http://fairmontsinternationalschool.co.ke/fairmontsAPI/updatepassword.php?phone_no="+phone_no+"&new_password="+new_pass+"&old_password="+old_pass;
                URL= BaseUrl.updatePassword(phone_no,new_pass,old_pass);
                update();
            }else{
                confirmpassword.setError("Passwords don't match!");
            }
        }
    }

    private void update() {
        StringRequest request=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                finish();
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
        RequestQueue requestQueue= Volley.newRequestQueue(UserProfile.this);
        requestQueue.add(request);
    }
}
