package com.fairmontsinternational.charlie.fairmontsinternational;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

public class Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Intent intent=getIntent();
        String parentname=intent.getStringExtra("parent_name");
        String phoneno=intent.getStringExtra("phoneNo");
        sendEmail(parentname,phoneno);
    }

    protected void sendEmail(String name, String phone) {

        String[] TO = {"fairmontsinternationalschool@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, name+"'s feedback @Myfairmont");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your message goes here.");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(),"There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
