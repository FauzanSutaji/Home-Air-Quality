package com.example.homeairquality.ui.moreinfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.homeairquality.MainActivity;
import com.example.homeairquality.R;

public class FullInfoActivity extends Activity
{
    int[] images = {R.drawable.whatisindoorairquality,
            R.drawable.sourcesofindoorairpollution,
            R.drawable.healtheffectsofairpollution,
            R.drawable.aqiwhatisit,
            R.drawable.causesofindoorairpollution,
            R.drawable.indoorairqualityimpactshealth};

    String[] title = {"What is Air Quality Index?",
            "Main Causes of Air Pollution",
            "Health Impacts of Air Pollution",
            "AQI? What is it?",
            "Causes of Indoor Air Pollution",
            "Indoor Air Quality Impacts Health"};

    String[] desc = {"The Air Quality Index (AQI) is used for reporting daily air quality. It tells you how clean or polluted your air is, and what associated health effects might be a concern for you.\n\nHow does the AQI work?\n\nThink of the AQI as a yardstick that runs from 0 to 500. The higher the AQI value, the greater the level of air pollution and the greater the health concern. For example, an AQI value of 50 or below represents good air quality, while an AQI value over 300 represents hazardous air quality.",
            "The rising number of indoor air pollutants has made breathing fresh, clean air next to impossible. The causes of indoor air pollution have left everyone worried about their health.\n\nToxic products, inadequate ventilation, high temperature and humidity are a few of the primary causes of indoor air pollution in our homes.\n\n1. Asbestos\n2. Formaldehyde\n3. Radon\n4. Tobacco Smoke\n5. Biological Pollutants\n6. Wood stoves, space heaters, water heaters, fireplaces and dryers, the fuel-burning combustion appliances\n7. Varnishes, paints, and certain cleaning household products",
            "Air pollution is a risk for all-cause mortality as well as specific diseases. The specific disease outcomes most strongly linked with exposure to air pollution include stroke and cataract\n\nIf contaminants such as animal dander, dust mites or other bacteria get into the home, there will also be some serious effects from them. You will start to experience asthma symptoms, throat irritation, flu, and other types of infectious diseases.\n\nThe gas/wood stoves, heaters used in home produce hazardous nitrogen dioxide and carbon monoxide that can cause respiratory infections and damage and irritation to the lungs.",
            "The sky turned grey and the air started smelling of soot in Delhi last week. We were told that the AQI levels had gone past the dangerous level. What is AQI? What does it indicate?",
            "Healthy and clean air allows us to take a deep breath and sleep soundly. We spend approx. 90% of our time indoors. However, according to the WHO, the air inside is 2-5 times more polluted than the outside air.",
            "This three-minute video describes how indoor air quality impacts your lungs, as well as providing practical tips for improving the indoor air quality in your home."};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_info_page);

        Intent i = getIntent();
        int position = i.getExtras().getInt("id");
        ListAdapter listAdapter = new ListAdapter(FullInfoActivity.this, title, desc, images);
        ImageView imageView = (ImageView) findViewById(R.id.imageInfo);
        ImageView backButton = (ImageView) findViewById(R.id.buttonBackInfo);
        TextView textTitle = (TextView) findViewById(R.id.textTitleFull);
        TextView textDesc = (TextView) findViewById(R.id.textDescFull);
        textTitle.setText(title[position]);
        textTitle.setTextSize(25);
        textTitle.setTypeface(null, Typeface.BOLD);
        textTitle.setTextColor(Color.BLACK);
        textDesc.setText(desc[position]);
        textDesc.setTextSize(18);
        textDesc.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        textDesc.setTextColor(Color.BLACK);
        imageView.setImageResource(images[position]);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplication(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });
    }
}
