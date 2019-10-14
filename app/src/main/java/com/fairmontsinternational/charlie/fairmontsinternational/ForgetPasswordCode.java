package com.fairmontsinternational.charlie.fairmontsinternational;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgetPasswordCode extends AppCompatActivity {
    Button SubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_code);
        SubmitButton = findViewById(R.id.btn_submit);

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgetPasswordCode.this,ForgetPassword.class));
            }
        });
    }
}
