package com.example.homeairquality;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = app_preferences.edit();
        boolean isFirstTime = app_preferences.getBoolean("isFirstTime", true);
        if(isFirstTime)
        {
            Intent i = new Intent(this,FirstLandingActivity.class);
            startActivity(i);
            editor.putBoolean("isFirstTime", false);
            editor.apply();
            finish();
        }
        else
        {
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            editor.putBoolean("isFirstTime", false);
            editor.apply();
            finish();
        }
    }
}

