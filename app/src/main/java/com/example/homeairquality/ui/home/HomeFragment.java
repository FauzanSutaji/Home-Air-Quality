package com.example.homeairquality.ui.home;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;


import com.example.homeairquality.R;
import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class HomeFragment extends Fragment{

    TextView aqiDesc,carbonDesc,flameDesc,home;
    TextView aqiValue,carbonValue,flameValue;
    ArcGauge AQIGauge,FlameGauge,CarbonGauge;
    com.ekn.gruzer.gaugelibrary.Range Range_1,Range_2,Range_3,Range_4,Range_5,Range_6;
    boolean delays = false;
    public int counter;
    CountDownTimer timer;
    String url1 = "https://www.geeksforgeeks.org/wp-content/uploads/gfg_200X200-1.png";
    String url2 = "https://www.sparetheair.com/assets/aqi/AQMD%20-%20AQI%20Chart%20Updates%20-%20AQI%20Ozone%20Chart%20-%20SMAQMD-0722-73%20-%20Hi-Res%20-%20FINAL.png";
    String url3 = "https://bizzbucket.co/wp-content/uploads/2020/08/Life-in-The-Metro-Blog-Title-22.png";

    public HomeFragment(){
        // require a empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        delays = false;

        home = view.findViewById(R.id.textViewHome1);
        aqiValue = view.findViewById(R.id.textViewAQI2);
        carbonValue = view.findViewById(R.id.textViewCarbon2);
        flameValue = view.findViewById(R.id.textViewFlame2);
        aqiDesc = view.findViewById(R.id.textViewAQI3);
        carbonDesc = view.findViewById(R.id.textViewCarbon3);
        flameDesc = view.findViewById(R.id.textViewFlame3);


        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        SliderView sliderView = view.findViewById(R.id.imageSlider);

        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(R.drawable.whatisindoorairquality));
        sliderDataArrayList.add(new SliderData(R.drawable.sourcesofindoorairpollution));
        sliderDataArrayList.add(new SliderData(R.drawable.healtheffectsofairpollution));

        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(getActivity(), sliderDataArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);


        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();

        //testing arc gauge
        AQIGauge = view.findViewById(R.id.aqiGauge);
        FlameGauge = view.findViewById(R.id.flammableGauge);
        CarbonGauge = view.findViewById(R.id.carbonGauge);

        //declaring range for Gauge chart
        Range_1 = new com.ekn.gruzer.gaugelibrary.Range();
        Range_2 = new com.ekn.gruzer.gaugelibrary.Range();
        Range_3 = new com.ekn.gruzer.gaugelibrary.Range();
        Range_4 = new com.ekn.gruzer.gaugelibrary.Range();
        Range_5 = new com.ekn.gruzer.gaugelibrary.Range();
        Range_6 = new com.ekn.gruzer.gaugelibrary.Range();


        //setting the value of range (AQI)
        Range_1.setFrom(0);Range_1.setTo(50);Range_1.setColor(Color.GREEN);
        Range_2.setFrom(51);Range_2.setTo(100);Range_2.setColor(Color.parseColor("#00FF00"));
        Range_3.setFrom(101);Range_3.setTo(200);Range_3.setColor(Color.YELLOW);
        Range_4.setFrom(201);Range_4.setTo(300);Range_4.setColor(Color.parseColor("#FFA500"));
        Range_5.setFrom(301);Range_5.setTo(400);Range_5.setColor(Color.RED);
        Range_6.setFrom(401);Range_6.setTo(500);Range_6.setColor(Color.parseColor("#800000"));

        AQIGauge.setMinValue(0);
        AQIGauge.setMaxValue(500);

        FlameGauge.setMinValue(0);
        FlameGauge.setMaxValue(500);

        CarbonGauge.setMinValue(0);
        CarbonGauge.setMaxValue(500);

        AQIGauge.addRange(Range_1);AQIGauge.addRange(Range_2);AQIGauge.addRange(Range_3);AQIGauge.addRange(Range_4);AQIGauge.addRange(Range_5);AQIGauge.addRange(Range_6);
        FlameGauge.addRange(Range_1);FlameGauge.addRange(Range_2);FlameGauge.addRange(Range_3);FlameGauge.addRange(Range_4);FlameGauge.addRange(Range_5);FlameGauge.addRange(Range_6);
        CarbonGauge.addRange(Range_1);CarbonGauge.addRange(Range_2);CarbonGauge.addRange(Range_3);CarbonGauge.addRange(Range_4);CarbonGauge.addRange(Range_5);CarbonGauge.addRange(Range_6);

            //setting value for the AQI from database(arcgauge)
            Query checkAQI = FirebaseDatabase.getInstance().getReference("current_AQI_value");
            checkAQI.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    if (snapshot.exists())
                    {

                        String value = snapshot.getValue(String.class);
                        Double test = Double.parseDouble(value);
                        AQIGauge.setValue(test);

                        aqiValue.setText(value + " PPM");
                        if (test == 0.0 || test <= 50.0)
                        {
                            aqiDesc.setText("Good");
                        }
                        else if (test == 51.0 || test <= 100.0)
                        {
                            aqiDesc.setText("Satisfactory");
                        }
                        else if (test == 101.0 || test <= 200.0)
                        {
                            aqiDesc.setText("Moderate");
                        }
                        else if (test == 201.0 || test <= 300.0)
                        {
                            aqiDesc.setText("Poor");
                        }
                        else if (test == 301.0 || test <= 400.0)
                        {
                            aqiDesc.setText("Harmful");
                        }
                        else if (test >= 401.0)
                        {
                            aqiDesc.setText("Dangerous");
                        }

                        //make a notification when AQI reaches dangerous level
                        if (test >= 301.0)
                        {
                            if(delays == false)
                            {
                                delays = true;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    showNotification();
                                }
                                new CountDownTimer(30000, 1000){
                                    public void onTick(long millisUntilFinished)
                                    {
                                        //home.setText(String.valueOf(counter));
                                        //counter++;
                                    }
                                    public  void onFinish(){
                                        delays = false;
                                    }
                                }.start();
                            }

                        }

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Some error on fetching data for AQI value occured", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        //setting value for the Flame from database(arcgauge)
        Query checkFlame = FirebaseDatabase.getInstance().getReference("current_Flammable_Gas_value");
        checkFlame.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String value = snapshot.getValue(String.class);
                    Double test = Double.parseDouble(value);
                    FlameGauge.setValue(test);
                    flameValue.setText(value+" PPM");
                    if(test == 0.0 || test<= 50.0)
                    {
                        flameDesc.setText("Good");
                    }
                    else if(test == 51.0 || test<= 100.0)
                    {
                        flameDesc.setText("Satisfactory");
                    }
                    else if(test == 101.0 || test<= 200.0)
                    {
                        flameDesc.setText("Moderate");
                    }
                    else if(test == 201.0 || test<= 300.0)
                    {
                        flameDesc.setText("Poor");
                    }
                    else if(test == 301.0 || test<= 400.0)
                    {
                        flameDesc.setText("Harmful");
                    }
                    else if(test >= 401.0)
                    {
                        flameDesc.setText("Dangerous");
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Some error on fetching data for flammable gas value occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //setting value for the Carbon from database(arcgauge)
        Query checkCarbon = FirebaseDatabase.getInstance().getReference("current_Carbon_Monoxide_value");
        checkCarbon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String value = snapshot.getValue(String.class);
                    Double test = Double.parseDouble(value);
                    CarbonGauge.setValue(test);
                    carbonValue.setText(value+" PPM");
                    if(test == 0.0 || test<= 50.0)
                    {
                        carbonDesc.setText("Good");
                    }
                    else if(test == 51.0 || test<= 100.0)
                    {
                        carbonDesc.setText("Satisfactory");
                    }
                    else if(test == 101.0 || test<= 200.0)
                    {
                        carbonDesc.setText("Moderate");
                    }
                    else if(test == 201.0 || test<= 300.0)
                    {
                        carbonDesc.setText("Poor");
                    }
                    else if(test == 301.0 || test<= 400.0)
                    {
                        carbonDesc.setText("Harmful");
                    }
                    else if(test >= 401.0)
                    {
                        carbonDesc.setText("Dangerous");
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Some error on fetching data for carbon monoxide value occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showNotification()
    {
        Uri sound = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.alarm);

        Intent activityIntent = new Intent(getContext(), HomeFragment.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getContext(),
                0, activityIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity(), "default_notification_channel_id" )
                .setSmallIcon(R.drawable.airtracklogo)
                .setContentTitle( "Danger!!!" )
                .setSound(sound)
                .setContentIntent(contentIntent)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setAutoCancel(false)
                .setContentText( "Your home air quality had reaches dangerous level.\nPlease improve your home ventilation.\n\nAQI: "+ aqiValue.getText().toString() +" - "+ aqiDesc.getText().toString() +".") ;
        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context. NOTIFICATION_SERVICE );
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                    .setUsage(AudioAttributes. USAGE_ALARM )
                    .build() ;
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( "NOTIFICATION_CHANNEL_ID" , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color. RED ) ;
            notificationChannel.enableVibration( true ) ;
            notificationChannel.setVibrationPattern( new long []{ 500 , 500 , 500 , 500 , 500 , 500 , 500 , 500 , 500 }) ;
            notificationChannel.setSound(sound , audioAttributes) ;
            mBuilder.setChannelId( "NOTIFICATION_CHANNEL_ID" ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () ,
                mBuilder.build()) ;
    }

}