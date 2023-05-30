package com.example.homeairquality;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstLandingActivity extends AppCompatActivity
{
    Button skip,continues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_start_activity);
        continues = (Button)findViewById(R.id.buttonContinue1);
        skip = (Button)findViewById(R.id.buttonskip1);
        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstLandingActivity.this,SecondLandingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstLandingActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
