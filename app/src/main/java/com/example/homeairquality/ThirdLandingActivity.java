package com.example.homeairquality;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdLandingActivity extends AppCompatActivity
{
    Button continues;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_start_activity);
        continues = (Button)findViewById(R.id.buttonContinue3);
        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThirdLandingActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
