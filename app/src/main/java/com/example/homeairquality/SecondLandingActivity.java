package com.example.homeairquality;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SecondLandingActivity extends AppCompatActivity
{
    Button skip,continues;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_start_activity);
        continues = (Button)findViewById(R.id.buttonContinue2);
        skip = (Button)findViewById(R.id.buttonskip2);
        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondLandingActivity.this,ThirdLandingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondLandingActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
