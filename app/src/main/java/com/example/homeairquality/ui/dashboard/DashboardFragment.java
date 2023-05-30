package com.example.homeairquality.ui.dashboard;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.homeairquality.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class DashboardFragment extends Fragment
{
    LineChart chartAQI,chartFlame,chartCarbon;
    LineData lineData;
    LineDataSet lineDataSet;
    ArrayList lineEntriesAqiMonthly,lineEntriesAqiWeekly,lineEntriesFlameMonthly,lineEntriesFlameWeekly,lineEntriesCarbonMonthly,lineEntriesCarbonWeekly;
    Button week, month, clear;
    TextView test;

    //aqi
    List<Double> aqivalueJan = new ArrayList<>();
    List<Double> aqivalueFeb = new ArrayList<>();
    List<Double> aqivalueMar = new ArrayList<>();
    List<Double> aqivalueApr = new ArrayList<>();
    List<Double> aqivalueMay = new ArrayList<>();
    List<Double> aqivalueJun = new ArrayList<>();
    List<Double> aqivalueJuly = new ArrayList<>();
    List<Double> aqivalueAug = new ArrayList<>();
    List<Double> aqivalueSept = new ArrayList<>();
    List<Double> aqivalueOct = new ArrayList<>();
    List<Double> aqivalueNov = new ArrayList<>();
    List<Double> aqivalueDec = new ArrayList<>();
    List<Double> aqivalueWeek1 = new ArrayList<>();
    List<Double> aqivalueWeek2 = new ArrayList<>();
    List<Double> aqivalueWeek3 = new ArrayList<>();
    List<Double> aqivalueWeek4 = new ArrayList<>();
    Double sumJan,sumFeb,sumMar,sumApr,sumMay,sumJun,sumJuly,sumAug,sumSept,sumOct,sumNov,sumDec,sumAqiWeek1,sumAqiWeek2,sumAqiWeek3,sumAqiWeek4;
    Float avgJan,avgFeb,avgMar,avgApr,avgMay,avgJun,avgJuly,avgAug,avgSept,avgOct,avgNov,avgDec,avgAqiWeek1,avgAqiWeek2,avgAqiWeek3,avgAqiWeek4;

    //flammable gas
    List<Double> flamevalueJan = new ArrayList<>();
    List<Double> flamevalueFeb = new ArrayList<>();
    List<Double> flamevalueMar = new ArrayList<>();
    List<Double> flamevalueApr = new ArrayList<>();
    List<Double> flamevalueMay = new ArrayList<>();
    List<Double> flamevalueJun = new ArrayList<>();
    List<Double> flamevalueJuly = new ArrayList<>();
    List<Double> flamevalueAug = new ArrayList<>();
    List<Double> flamevalueSept = new ArrayList<>();
    List<Double> flamevalueOct = new ArrayList<>();
    List<Double> flamevalueNov = new ArrayList<>();
    List<Double> flamevalueDec = new ArrayList<>();
    List<Double> flamevalueWeek1 = new ArrayList<>();
    List<Double> flamevalueWeek2 = new ArrayList<>();
    List<Double> flamevalueWeek3 = new ArrayList<>();
    List<Double> flamevalueWeek4 = new ArrayList<>();
    Double sumflameJan,sumflameFeb,sumflameMar,sumflameApr,sumflameMay,sumflameJun,sumflameJuly,sumflameAug,sumflameSept,sumflameOct,sumflameNov,sumflameDec,sumflameWeek1,sumflameWeek2,sumflameWeek3,sumflameWeek4;
    Float avgflameJan,avgflameFeb,avgflameMar,avgflameApr,avgflameMay,avgflameJun,avgflameJuly,avgflameAug,avgflameSept,avgflameOct,avgflameNov,avgflameDec,avgflameWeek1,avgflameWeek2,avgflameWeek3,avgflameWeek4;

    //carbon monoxide
    List<Double> carbonvalueJan = new ArrayList<>();
    List<Double> carbonvalueFeb = new ArrayList<>();
    List<Double> carbonvalueMar = new ArrayList<>();
    List<Double> carbonvalueApr = new ArrayList<>();
    List<Double> carbonvalueMay = new ArrayList<>();
    List<Double> carbonvalueJun = new ArrayList<>();
    List<Double> carbonvalueJuly = new ArrayList<>();
    List<Double> carbonvalueAug = new ArrayList<>();
    List<Double> carbonvalueSept = new ArrayList<>();
    List<Double> carbonvalueOct = new ArrayList<>();
    List<Double> carbonvalueNov = new ArrayList<>();
    List<Double> carbonvalueDec = new ArrayList<>();
    List<Double> carbonvalueWeek1 = new ArrayList<>();
    List<Double> carbonvalueWeek2 = new ArrayList<>();
    List<Double> carbonvalueWeek3 = new ArrayList<>();
    List<Double> carbonvalueWeek4 = new ArrayList<>();
    Double sumcarbonJan,sumcarbonFeb,sumcarbonMar,sumcarbonApr,sumcarbonMay,sumcarbonJun,sumcarbonJuly,sumcarbonAug,sumcarbonSept,sumcarbonOct,sumcarbonNov,sumcarbonDec,sumcarbonWeek1,sumcarbonWeek2,sumcarbonWeek3,sumcarbonWeek4;
    Float avgcarbonJan,avgcarbonFeb,avgcarbonMar,avgcarbonApr,avgcarbonMay,avgcarbonJun,avgcarbonJuly,avgcarbonAug,avgcarbonSept,avgcarbonOct,avgcarbonNov,avgcarbonDec,avgcarbonWeek1,avgcarbonWeek2,avgcarbonWeek3,avgcarbonWeek4;
    public DashboardFragment(){
        // require a empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        List<String> xAxisValuesMonthly = new ArrayList<>(Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "June","July", "Aug", "Sept", "Oct", "Nov", "Dec"));
        List<String> xAxisValuesWeekly = new ArrayList<>(Arrays.asList("Week 1", "Week 2", "Week 3", "Week 4"));

        test = view.findViewById(R.id.textViewDashboard2);
        chartAQI = (LineChart) view.findViewById(R.id.chartaqi);
        chartFlame = (LineChart) view.findViewById(R.id.chartFlammable);
        chartCarbon = (LineChart) view.findViewById(R.id.chartCarbon);
        month = (Button) view.findViewById(R.id.buttonBackInfo);
        week = (Button) view.findViewById(R.id.buttonSortWeekly);

        //aqichart
        chartAQI.setBackgroundColor(Color.WHITE);
        chartAQI.setTouchEnabled(true);
        chartAQI.setDragEnabled(true);
        chartAQI.setScaleEnabled(true);
        chartAQI.setPinchZoom(true);
        chartAQI.getDescription().setEnabled(false);

        XAxis xAxis = chartAQI.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setTextSize(11);

        YAxis yAxis = chartAQI.getAxisLeft();
        chartAQI.getAxisRight().setEnabled(false);
        yAxis.enableGridDashedLine(10f, 10f, 0f);
        yAxis.setTextSize(8);
        yAxis.setAxisMaximum(500f);
        yAxis.setAxisMinimum(-50f);

        //flamechart
        chartFlame.setBackgroundColor(Color.WHITE);
        chartFlame.setTouchEnabled(true);
        chartFlame.setDragEnabled(true);
        chartFlame.setScaleEnabled(true);
        chartFlame.setPinchZoom(true);
        chartFlame.getDescription().setEnabled(false);

        XAxis xAxis2 = chartFlame.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setGranularity(1f);
        xAxis2.enableGridDashedLine(10f, 10f, 0f);
        xAxis2.setTextSize(11);

        YAxis yAxis2 = chartFlame.getAxisLeft();
        chartFlame.getAxisRight().setEnabled(false);
        yAxis2.enableGridDashedLine(10f, 10f, 0f);
        yAxis2.setTextSize(8);
        yAxis2.setAxisMaximum(500f);
        yAxis2.setAxisMinimum(-50f);

        //carbonchart
        chartCarbon.setBackgroundColor(Color.WHITE);
        chartCarbon.setTouchEnabled(true);
        chartCarbon.setDragEnabled(true);
        chartCarbon.setScaleEnabled(true);
        chartCarbon.setPinchZoom(true);
        chartCarbon.getDescription().setEnabled(false);

        XAxis xAxis3 = chartCarbon.getXAxis();
        xAxis3.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis3.setGranularity(1f);
        xAxis3.enableGridDashedLine(10f, 10f, 0f);

        YAxis yAxis3 = chartCarbon.getAxisLeft();
        chartCarbon.getAxisRight().setEnabled(false);
        yAxis3.enableGridDashedLine(10f, 10f, 0f);
        yAxis3.setTextSize(8);
        yAxis3.setAxisMaximum(500f);
        yAxis3.setAxisMinimum(-50f);

        lineEntriesAqiWeekly = new ArrayList<>();
        lineEntriesAqiMonthly = new ArrayList<>();
        lineEntriesFlameWeekly = new ArrayList<>();
        lineEntriesFlameMonthly = new ArrayList<>();
        lineEntriesCarbonWeekly = new ArrayList<>();
        lineEntriesCarbonMonthly = new ArrayList<>();
        week.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //aqi
                chartAQI.clear();
                chartAQI.invalidate();
                lineEntriesAqiWeekly.clear();
                aqivalueJan.clear();
                aqivalueFeb.clear();
                aqivalueMar.clear();
                aqivalueApr.clear();
                aqivalueMay.clear();
                aqivalueJun.clear();
                aqivalueJuly.clear();
                aqivalueAug.clear();
                aqivalueSept.clear();
                aqivalueOct.clear();
                aqivalueNov.clear();
                aqivalueDec.clear();

                //aqi
                chartFlame.clear();
                chartFlame.invalidate();
                lineEntriesFlameWeekly.clear();
                flamevalueJan.clear();
                flamevalueFeb.clear();
                flamevalueMar.clear();
                flamevalueApr.clear();
                flamevalueMay.clear();
                flamevalueJun.clear();
                flamevalueJuly.clear();
                flamevalueAug.clear();
                flamevalueSept.clear();
                flamevalueOct.clear();
                flamevalueNov.clear();
                flamevalueDec.clear();

                //carbon
                chartCarbon.clear();
                chartCarbon.invalidate();
                lineEntriesCarbonWeekly.clear();
                carbonvalueJan.clear();
                carbonvalueFeb.clear();
                carbonvalueMar.clear();
                carbonvalueApr.clear();
                carbonvalueMay.clear();
                carbonvalueJun.clear();
                carbonvalueJuly.clear();
                carbonvalueAug.clear();
                carbonvalueSept.clear();
                carbonvalueOct.clear();
                carbonvalueNov.clear();
                carbonvalueDec.clear();

                String currentDate = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault()).format(new Date());
                StringTokenizer tokens = new StringTokenizer(currentDate, "-");
                String currentDay = tokens.nextToken();
                String currentMonth = tokens.nextToken();
                String currentYear = tokens.nextToken();

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference followingRef = rootRef.child("history");
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            for (DataSnapshot dSnapshot : ds.getChildren())
                            {
                                String tempdate = dSnapshot.child("Date").getValue(String.class);
                                StringTokenizer tokens2 = new StringTokenizer(tempdate, "-");
                                String day = tokens2.nextToken();
                                Double tempDay = Double.parseDouble(day);
                                String month = tokens2.nextToken();
                                String year = tokens2.nextToken();

                                if(currentYear.equals(year))
                                {
                                    if(currentMonth.equals(month))
                                    {
                                        if(tempDay >= 0 && tempDay <= 7)
                                        {
                                            //aqi
                                            String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                            Double aqi = Double.parseDouble(tempaqi);
                                            aqivalueWeek1.add(aqi);
                                            //flame
                                            String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                            Double flame = Double.parseDouble(tempflame);
                                            flamevalueWeek1.add(flame);
                                            //carbon
                                            String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                            Double carbon = Double.parseDouble(tempcarbon);
                                            carbonvalueWeek1.add(carbon);
                                        }
                                        else if(tempDay >= 8 && tempDay <= 14)
                                        {
                                            //aqi
                                            String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                            Double aqi = Double.parseDouble(tempaqi);
                                            aqivalueWeek2.add(aqi);
                                            //flame
                                            String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                            Double flame = Double.parseDouble(tempflame);
                                            flamevalueWeek2.add(flame);
                                            //carbon
                                            String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                            Double carbon = Double.parseDouble(tempcarbon);
                                            carbonvalueWeek2.add(carbon);
                                        }
                                        else if(tempDay >= 15 && tempDay <= 20)
                                        {
                                            //aqi
                                            String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                            Double aqi = Double.parseDouble(tempaqi);
                                            aqivalueWeek3.add(aqi);
                                            //flame
                                            String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                            Double flame = Double.parseDouble(tempflame);
                                            flamevalueWeek3.add(flame);
                                            //carbon
                                            String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                            Double carbon = Double.parseDouble(tempcarbon);
                                            carbonvalueWeek3.add(carbon);
                                        }
                                        else if(tempDay >= 21 && tempDay <= 31)
                                        {
                                            //aqi
                                            String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                            Double aqi = Double.parseDouble(tempaqi);
                                            aqivalueWeek4.add(aqi);
                                            //flame
                                            String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                            Double flame = Double.parseDouble(tempflame);
                                            flamevalueWeek4.add(flame);
                                            //carbon
                                            String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                            Double carbon = Double.parseDouble(tempcarbon);
                                            carbonvalueWeek4.add(carbon);
                                        }

                                    }
                                }
                            }
                        }
                        //week1
                        //aqi
                        sumAqiWeek1 = 0.0;
                        for(Double value: aqivalueWeek1)
                        {
                            sumAqiWeek1 = (sumAqiWeek1 += value);
                        }
                        sumAqiWeek1 = Double.valueOf(Math.round(sumAqiWeek1/aqivalueWeek1.size()));
                        avgAqiWeek1 = Float.parseFloat(String.valueOf(sumAqiWeek1));
                        //flame
                        sumflameWeek1 = 0.0;
                        for(Double value: flamevalueWeek1)
                        {
                            sumflameWeek1 = (sumflameWeek1 += value);
                        }
                        sumflameWeek1 = Double.valueOf(Math.round(sumflameWeek1/flamevalueWeek1.size()));
                        avgflameWeek1 = Float.parseFloat(String.valueOf(sumflameWeek1));
                        //carbon
                        sumcarbonWeek1 = 0.0;
                        for(Double value: carbonvalueWeek1)
                        {
                            sumcarbonWeek1 = (sumcarbonWeek1 += value);
                        }
                        sumcarbonWeek1 = Double.valueOf(Math.round(sumcarbonWeek1/carbonvalueWeek1.size()));
                        avgcarbonWeek1 = Float.parseFloat(String.valueOf(sumcarbonWeek1));

                        //week2
                        //aqi
                        sumAqiWeek2 = 0.0;
                        for(Double value: aqivalueWeek2)
                        {
                            sumAqiWeek2 = (sumAqiWeek2 += value);
                        }
                        sumAqiWeek2 = Double.valueOf(Math.round(sumAqiWeek2/aqivalueWeek2.size()));
                        avgAqiWeek2 = Float.parseFloat(String.valueOf(sumAqiWeek2));
                        //flame
                        sumflameWeek2 = 0.0;
                        for(Double value: flamevalueWeek2)
                        {
                            sumflameWeek2 = (sumflameWeek2 += value);
                        }
                        sumflameWeek2 = Double.valueOf(Math.round(sumflameWeek2/flamevalueWeek2.size()));
                        avgflameWeek2 = Float.parseFloat(String.valueOf(sumflameWeek2));
                        //carbon
                        sumcarbonWeek2 = 0.0;
                        for(Double value: carbonvalueWeek2)
                        {
                            sumcarbonWeek2 = (sumcarbonWeek2 += value);
                        }
                        sumcarbonWeek2 = Double.valueOf(Math.round(sumcarbonWeek2/carbonvalueWeek2.size()));
                        avgcarbonWeek2 = Float.parseFloat(String.valueOf(sumcarbonWeek2));

                        //week3
                        //aqi
                        sumAqiWeek3 = 0.0;
                        for(Double value: aqivalueWeek3)
                        {
                            sumAqiWeek3 = (sumAqiWeek3 += value);
                        }
                        sumAqiWeek3 = Double.valueOf(Math.round(sumAqiWeek3/aqivalueWeek3.size()));
                        avgAqiWeek3 = Float.parseFloat(String.valueOf(sumAqiWeek3));
                        //flame
                        sumflameWeek3 = 0.0;
                        for(Double value: flamevalueWeek3)
                        {
                            sumflameWeek3 = (sumflameWeek3 += value);
                        }
                        sumflameWeek3 = Double.valueOf(Math.round(sumflameWeek3/flamevalueWeek3.size()));
                        avgflameWeek3 = Float.parseFloat(String.valueOf(sumflameWeek3));
                        //carbon
                        sumcarbonWeek3 = 0.0;
                        for(Double value: carbonvalueWeek3)
                        {
                            sumcarbonWeek3 = (sumcarbonWeek3 += value);
                        }
                        sumcarbonWeek3 = Double.valueOf(Math.round(sumcarbonWeek3/carbonvalueWeek3.size()));
                        avgcarbonWeek3 = Float.parseFloat(String.valueOf(sumcarbonWeek3));

                        //week4
                        //aqi
                        sumAqiWeek4 = 0.0;
                        for(Double value: aqivalueWeek4)
                        {
                            sumAqiWeek4 = (sumAqiWeek4 += value);
                        }
                        sumAqiWeek4 = Double.valueOf(Math.round(sumAqiWeek4/aqivalueWeek4.size()));
                        avgAqiWeek4 = Float.parseFloat(String.valueOf(sumAqiWeek4));
                        //flame
                        sumflameWeek4 = 0.0;
                        for(Double value: flamevalueWeek4)
                        {
                            sumflameWeek4 = (sumflameWeek4 += value);
                        }
                        sumflameWeek4 = Double.valueOf(Math.round(sumflameWeek4/flamevalueWeek4.size()));
                        avgflameWeek4 = Float.parseFloat(String.valueOf(sumflameWeek4));
                        //carbon
                        sumcarbonWeek4 = 0.0;
                        for(Double value: carbonvalueWeek4)
                        {
                            sumcarbonWeek4 = (sumcarbonWeek4 += value);
                        }
                        sumcarbonWeek4 = Double.valueOf(Math.round(sumcarbonWeek4/carbonvalueWeek4.size()));
                        avgcarbonWeek4 = Float.parseFloat(String.valueOf(sumcarbonWeek4));

                        //aqi
                        lineEntriesAqiWeekly.add(new Entry(0, avgAqiWeek1));
                        lineEntriesAqiWeekly.add(new Entry(1, avgAqiWeek2));
                        lineEntriesAqiWeekly.add(new Entry(2, avgAqiWeek3));
                        lineEntriesAqiWeekly.add(new Entry(3, avgAqiWeek4));
                        //flame
                        lineEntriesFlameWeekly.add(new Entry(0, avgflameWeek1));
                        lineEntriesFlameWeekly.add(new Entry(1, avgflameWeek2));
                        lineEntriesFlameWeekly.add(new Entry(2, avgflameWeek3));
                        lineEntriesFlameWeekly.add(new Entry(3, avgflameWeek4));
                        //carbon
                        lineEntriesCarbonWeekly.add(new Entry(0, avgcarbonWeek1));
                        lineEntriesCarbonWeekly.add(new Entry(1, avgcarbonWeek2));
                        lineEntriesCarbonWeekly.add(new Entry(2, avgcarbonWeek3));
                        lineEntriesCarbonWeekly.add(new Entry(3, avgcarbonWeek4));

                        //String setter in x-Axis
                        //aqi
                        chartAQI.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValuesWeekly));
                        lineDataSet = new LineDataSet(lineEntriesAqiWeekly, "Air Quality Index");
                        lineData = new LineData(lineDataSet);
                        lineData.setValueFormatter(new MyValueFormatter());
                        chartAQI.setData(lineData);
                        lineDataSet.setColors(Color.parseColor("#3B96EE"));
                        lineDataSet.setValueTextColor(Color.BLACK);
                        lineDataSet.setValueTextSize(10);
                        chartAQI.invalidate();
                        //flame
                        chartFlame.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValuesWeekly));
                        lineDataSet = new LineDataSet(lineEntriesFlameWeekly, "Flammable Gas");
                        lineData = new LineData(lineDataSet);
                        lineData.setValueFormatter(new MyValueFormatter());
                        chartFlame.setData(lineData);
                        lineDataSet.setColors(Color.parseColor("#3B96EE"));
                        lineDataSet.setValueTextColor(Color.BLACK);
                        lineDataSet.setValueTextSize(10);
                        chartFlame.invalidate();
                        //carbon
                        chartCarbon.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValuesWeekly));
                        lineDataSet = new LineDataSet(lineEntriesCarbonWeekly, "Carbon Monoxide");
                        lineData = new LineData(lineDataSet);
                        lineData.setValueFormatter(new MyValueFormatter());
                        chartCarbon.setData(lineData);
                        lineDataSet.setColors(Color.parseColor("#3B96EE"));
                        lineDataSet.setValueTextColor(Color.BLACK);
                        lineDataSet.setValueTextSize(10);
                        chartCarbon.invalidate();

                        //testing data
                        //Log.d("TAG", String.valueOf("Data read from DB : "+aqivalueWeek2.size()));
                        aqivalueWeek1.clear();aqivalueWeek2.clear();aqivalueWeek3.clear();aqivalueWeek4.clear();
                        flamevalueWeek1.clear();flamevalueWeek2.clear();flamevalueWeek3.clear();flamevalueWeek4.clear();
                        carbonvalueWeek1.clear();carbonvalueWeek2.clear();carbonvalueWeek3.clear();carbonvalueWeek4.clear();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                };
                followingRef.addListenerForSingleValueEvent(valueEventListener);
            }

        });
        month.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //aqi
                chartAQI.clear();
                chartAQI.invalidate();
                lineEntriesAqiMonthly.clear();
                aqivalueWeek1.clear();
                aqivalueWeek2.clear();
                aqivalueWeek3.clear();
                aqivalueWeek4.clear();
                //flame
                chartFlame.clear();
                chartFlame.invalidate();
                lineEntriesFlameMonthly.clear();
                flamevalueWeek1.clear();
                flamevalueWeek2.clear();
                flamevalueWeek3.clear();
                flamevalueWeek4.clear();
                //carbon
                chartCarbon.clear();
                chartCarbon.invalidate();
                lineEntriesCarbonMonthly.clear();
                carbonvalueWeek1.clear();
                carbonvalueWeek2.clear();
                carbonvalueWeek3.clear();
                carbonvalueWeek4.clear();

                String currentDate = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault()).format(new Date());
                StringTokenizer tokens = new StringTokenizer(currentDate, "-");
                String currentDay = tokens.nextToken();
                String currentMonth = tokens.nextToken();
                String currentYear = tokens.nextToken();

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference followingRef = rootRef.child("history");
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        String months = null;
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            for(DataSnapshot dSnapshot : ds.getChildren())
                            {
                                String tempdate = dSnapshot.child("Date").getValue(String.class);
                                StringTokenizer tokens2 = new StringTokenizer(tempdate, "-");
                                String days = tokens2.nextToken();
                                months = tokens2.nextToken();
                                String years = tokens2.nextToken();
                                if(currentYear.equals(years))
                                {
                                    if (months.equals("1")) {
                                        //aqi
                                        String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                        Double aqi = Double.parseDouble(tempaqi);
                                        aqivalueJan.add(aqi);
                                        //flame
                                        String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                        Double flame = Double.parseDouble(tempflame);
                                        flamevalueJan.add(flame);
                                        //carbon
                                        String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                        Double carbon = Double.parseDouble(tempcarbon);
                                        carbonvalueJan.add(carbon);
                                    }
                                    if (months.equals("2")) {
                                        //aqi
                                        String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                        Double aqi = Double.parseDouble(tempaqi);
                                        aqivalueFeb.add(aqi);
                                        //flame
                                        String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                        Double flame = Double.parseDouble(tempflame);
                                        flamevalueFeb.add(flame);
                                        //carbon
                                        String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                        Double carbon = Double.parseDouble(tempcarbon);
                                        carbonvalueFeb.add(carbon);
                                    }
                                    if (months.equals("3")) {
                                        //aqi
                                        String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                        Double aqi = Double.parseDouble(tempaqi);
                                        aqivalueMar.add(aqi);
                                        //flame
                                        String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                        Double flame = Double.parseDouble(tempflame);
                                        flamevalueMar.add(flame);
                                        //carbon
                                        String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                        Double carbon = Double.parseDouble(tempcarbon);
                                        carbonvalueMar.add(carbon);
                                    }
                                    if (months.equals("4")) {
                                        //aqi
                                        String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                        Double aqi = Double.parseDouble(tempaqi);
                                        aqivalueApr.add(aqi);
                                        //flame
                                        String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                        Double flame = Double.parseDouble(tempflame);
                                        flamevalueApr.add(flame);
                                        //carbon
                                        String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                        Double carbon = Double.parseDouble(tempcarbon);
                                        carbonvalueApr.add(carbon);
                                    }
                                    if (months.equals("5")) {
                                        //aqi
                                        String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                        Double aqi = Double.parseDouble(tempaqi);
                                        aqivalueMay.add(aqi);
                                        //flame
                                        String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                        Double flame = Double.parseDouble(tempflame);
                                        flamevalueMay.add(flame);
                                        //carbon
                                        String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                        Double carbon = Double.parseDouble(tempcarbon);
                                        carbonvalueMay.add(carbon);
                                    }
                                    if (months.equals("6")) {
                                        //aqi
                                        String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                        Double aqi = Double.parseDouble(tempaqi);
                                        aqivalueJun.add(aqi);
                                        //flame
                                        String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                        Double flame = Double.parseDouble(tempflame);
                                        flamevalueJun.add(flame);
                                        //carbon
                                        String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                        Double carbon = Double.parseDouble(tempcarbon);
                                        carbonvalueJun.add(carbon);
                                    }
                                    if (months.equals("7")) {
                                        //aqi
                                        String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                        Double aqi = Double.parseDouble(tempaqi);
                                        aqivalueJuly.add(aqi);
                                        //flame
                                        String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                        Double flame = Double.parseDouble(tempflame);
                                        flamevalueJuly.add(flame);
                                        //carbon
                                        String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                        Double carbon = Double.parseDouble(tempcarbon);
                                        carbonvalueJuly.add(carbon);
                                    }
                                    if (months.equals("8")) {
                                        //aqi
                                        String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                        Double aqi = Double.parseDouble(tempaqi);
                                        aqivalueAug.add(aqi);
                                        //flame
                                        String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                        Double flame = Double.parseDouble(tempflame);
                                        flamevalueAug.add(flame);
                                        //carbon
                                        String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                        Double carbon = Double.parseDouble(tempcarbon);
                                        carbonvalueAug.add(carbon);
                                    }
                                    if (months.equals("9")) {
                                        //aqi
                                        String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                        Double aqi = Double.parseDouble(tempaqi);
                                        aqivalueSept.add(aqi);
                                        //flame
                                        String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                        Double flame = Double.parseDouble(tempflame);
                                        flamevalueSept.add(flame);
                                        //carbon
                                        String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                        Double carbon = Double.parseDouble(tempcarbon);
                                        carbonvalueSept.add(carbon);
                                    }
                                    if (months.equals("10")) {
                                        //aqi
                                        String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                        Double aqi = Double.parseDouble(tempaqi);
                                        aqivalueOct.add(aqi);
                                        //flame
                                        String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                        Double flame = Double.parseDouble(tempflame);
                                        flamevalueOct.add(flame);
                                        //carbon
                                        String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                        Double carbon = Double.parseDouble(tempcarbon);
                                        carbonvalueOct.add(carbon);
                                    }
                                    if (months.equals("11")) {
                                        //aqi
                                        String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                        Double aqi = Double.parseDouble(tempaqi);
                                        aqivalueNov.add(aqi);
                                        //flame
                                        String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                        Double flame = Double.parseDouble(tempflame);
                                        flamevalueNov.add(flame);
                                        //carbon
                                        String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                        Double carbon = Double.parseDouble(tempcarbon);
                                        carbonvalueNov.add(carbon);
                                    }
                                    if (months.equals("12")) {
                                        //aqi
                                        String tempaqi = dSnapshot.child("AQI").getValue(String.class);
                                        Double aqi = Double.parseDouble(tempaqi);
                                        aqivalueDec.add(aqi);
                                        //flame
                                        String tempflame = dSnapshot.child("Flammable_Gas").getValue(String.class);
                                        Double flame = Double.parseDouble(tempflame);
                                        flamevalueDec.add(flame);
                                        //carbon
                                        String tempcarbon = dSnapshot.child("Carbon_Monoxide").getValue(String.class);
                                        Double carbon = Double.parseDouble(tempcarbon);
                                        carbonvalueDec.add(carbon);
                                    }
                                }
                            }
                        }


                        //january
                        //aqi
                        sumJan = 0.0;
                        for(Double value: aqivalueJan)
                        {
                            sumJan = (sumJan += value);
                        }
                        sumJan = Double.valueOf(Math.round(sumJan/aqivalueJan.size()));
                        avgJan = Float.parseFloat(String.valueOf(sumJan));
                        //flame
                        sumflameJan = 0.0;
                        for(Double value: flamevalueJan)
                        {
                            sumflameJan = (sumflameJan += value);
                        }
                        sumflameJan = Double.valueOf(Math.round(sumflameJan/flamevalueJan.size()));
                        avgflameJan = Float.parseFloat(String.valueOf(sumflameJan));
                        //carbon
                        sumcarbonJan = 0.0;
                        for(Double value: carbonvalueJan)
                        {
                            sumcarbonJan = (sumcarbonJan += value);
                        }
                        sumcarbonJan = Double.valueOf(Math.round(sumcarbonJan/carbonvalueJan.size()));
                        avgcarbonJan = Float.parseFloat(String.valueOf(sumcarbonJan));

                        //february
                        //aqi
                        sumFeb = 0.0;
                        for(Double value: aqivalueFeb)
                        {
                            sumFeb = (sumFeb += value);
                        }
                        sumFeb = Double.valueOf(Math.round(sumFeb/aqivalueFeb.size()));
                        avgFeb = Float.parseFloat(String.valueOf(sumFeb));
                        //flame
                        sumflameFeb = 0.0;
                        for(Double value: flamevalueFeb)
                        {
                            sumflameFeb = (sumflameFeb += value);
                        }
                        sumflameFeb = Double.valueOf(Math.round(sumflameFeb/flamevalueFeb.size()));
                        avgflameFeb = Float.parseFloat(String.valueOf(sumflameFeb));
                        //carbon
                        sumcarbonFeb = 0.0;
                        for(Double value: carbonvalueFeb)
                        {
                            sumcarbonFeb = (sumcarbonFeb += value);
                        }
                        sumcarbonFeb = Double.valueOf(Math.round(sumcarbonFeb/carbonvalueFeb.size()));
                        avgcarbonFeb = Float.parseFloat(String.valueOf(sumcarbonFeb));

                        //march
                        //aqi
                        sumMar = 0.0;
                        for(Double value: aqivalueMar)
                        {
                            sumMar = (sumMar += value);
                        }
                        sumMar = Double.valueOf(Math.round(sumMar/aqivalueMar.size()));
                        avgMar = Float.parseFloat(String.valueOf(sumMar));
                        //flame
                        sumflameMar = 0.0;
                        for(Double value: flamevalueMar)
                        {
                            sumflameMar = (sumflameMar += value);
                        }
                        sumflameMar = Double.valueOf(Math.round(sumflameMar/flamevalueMar.size()));
                        avgflameMar = Float.parseFloat(String.valueOf(sumflameMar));
                        //carbon
                        sumcarbonMar = 0.0;
                        for(Double value: carbonvalueMar)
                        {
                            sumcarbonMar = (sumcarbonMar += value);
                        }
                        sumcarbonMar = Double.valueOf(Math.round(sumcarbonMar/carbonvalueMar.size()));
                        avgcarbonMar = Float.parseFloat(String.valueOf(sumcarbonMar));

                        //april
                        //aqi
                        sumApr = 0.0;
                        for(Double value: aqivalueApr)
                        {
                            sumApr = (sumApr += value);
                        }
                        sumApr = Double.valueOf(Math.round(sumApr/aqivalueApr.size()));
                        avgApr = Float.parseFloat(String.valueOf(sumApr));
                        //flame
                        sumflameApr = 0.0;
                        for(Double value: flamevalueApr)
                        {
                            sumflameApr = (sumflameApr += value);
                        }
                        sumflameApr = Double.valueOf(Math.round(sumflameApr/flamevalueApr.size()));
                        avgflameApr = Float.parseFloat(String.valueOf(sumflameApr));
                        //carbon
                        sumcarbonApr = 0.0;
                        for(Double value: carbonvalueApr)
                        {
                            sumcarbonApr = (sumcarbonApr += value);
                        }
                        sumcarbonApr = Double.valueOf(Math.round(sumcarbonApr/carbonvalueApr.size()));
                        avgcarbonApr = Float.parseFloat(String.valueOf(sumcarbonApr));

                        //may
                        //aqi
                        sumMay = 0.0;
                        for(Double value: aqivalueMay)
                        {
                            sumMay = (sumMay += value);
                        }
                        sumMay = Double.valueOf(Math.round(sumMay/aqivalueMay.size()));
                        avgMay = Float.parseFloat(String.valueOf(sumMay));
                        //flame
                        sumflameMay = 0.0;
                        for(Double value: flamevalueMay)
                        {
                            sumflameMay = (sumflameMay += value);
                        }
                        sumflameMay = Double.valueOf(Math.round(sumflameMay/flamevalueMay.size()));
                        avgflameMay = Float.parseFloat(String.valueOf(sumflameMay));
                        //carbon
                        sumcarbonMay = 0.0;
                        for(Double value: carbonvalueMay)
                        {
                            sumcarbonMay = (sumcarbonMay += value);
                        }
                        sumcarbonMay = Double.valueOf(Math.round(sumcarbonMay/carbonvalueMay.size()));
                        avgcarbonMay = Float.parseFloat(String.valueOf(sumcarbonMay));

                        //june
                        //aqi
                        sumJun = 0.0;
                        for(Double value: aqivalueJun)
                        {
                            sumJun = (sumJun += value);
                        }
                        sumJun = Double.valueOf(Math.round(sumJun/aqivalueJun.size()));
                        avgJun = Float.parseFloat(String.valueOf(sumJun));
                        //flame
                        sumflameJun = 0.0;
                        for(Double value: flamevalueJun)
                        {
                            sumflameJun = (sumflameJun += value);
                        }
                        sumflameJun = Double.valueOf(Math.round(sumflameJun/flamevalueJun.size()));
                        avgflameJun = Float.parseFloat(String.valueOf(sumflameJun));
                        //carbon
                        sumcarbonJun = 0.0;
                        for(Double value: carbonvalueJun)
                        {
                            sumcarbonJun = (sumcarbonJun += value);
                        }
                        sumcarbonJun = Double.valueOf(Math.round(sumcarbonJun/carbonvalueJun.size()));
                        avgcarbonJun = Float.parseFloat(String.valueOf(sumcarbonJun));

                        //july
                        //aqi
                        sumJuly = 0.0;
                        for(Double value: aqivalueJuly)
                        {
                            sumJuly = (sumJuly += value);
                        }
                        sumJuly = Double.valueOf(Math.round(sumJuly/aqivalueJuly.size()));
                        avgJuly = Float.parseFloat(String.valueOf(sumJuly));
                        //flame
                        sumflameJuly = 0.0;
                        for(Double value: flamevalueJuly)
                        {
                            sumflameJuly = (sumflameJuly += value);
                        }
                        sumflameJuly = Double.valueOf(Math.round(sumflameJuly/flamevalueJuly.size()));
                        avgflameJuly = Float.parseFloat(String.valueOf(sumflameJuly));
                        //carbon
                        sumcarbonJuly = 0.0;
                        for(Double value: carbonvalueJuly)
                        {
                            sumcarbonJuly = (sumcarbonJuly += value);
                        }
                        sumcarbonJuly = Double.valueOf(Math.round(sumcarbonJuly/carbonvalueJuly.size()));
                        avgcarbonJuly = Float.parseFloat(String.valueOf(sumcarbonJuly));

                        //august
                        //aqi
                        sumAug = 0.0;
                        for(Double value: aqivalueAug)
                        {
                            sumAug = (sumAug += value);
                        }
                        sumAug = Double.valueOf(Math.round(sumAug/aqivalueAug.size()));
                        avgAug = Float.parseFloat(String.valueOf(sumAug));
                        //flame
                        sumflameAug = 0.0;
                        for(Double value: flamevalueAug)
                        {
                            sumflameAug = (sumflameAug += value);
                        }
                        sumflameAug = Double.valueOf(Math.round(sumflameAug/flamevalueAug.size()));
                        avgflameAug = Float.parseFloat(String.valueOf(sumflameAug));
                        //carbon
                        sumcarbonAug = 0.0;
                        for(Double value: carbonvalueAug)
                        {
                            sumcarbonAug = (sumcarbonAug += value);
                        }
                        sumcarbonAug = Double.valueOf(Math.round(sumcarbonAug/carbonvalueAug.size()));
                        avgcarbonAug = Float.parseFloat(String.valueOf(sumcarbonAug));

                        //september
                        //aqi
                        sumSept = 0.0;
                        for(Double value: aqivalueSept)
                        {
                            sumSept = (sumSept += value);
                        }
                        sumSept = Double.valueOf(Math.round(sumSept/aqivalueSept.size()));
                        avgSept = Float.parseFloat(String.valueOf(sumSept));
                        //flame
                        sumflameSept = 0.0;
                        for(Double value: flamevalueSept)
                        {
                            sumflameSept = (sumflameSept += value);
                        }
                        sumflameSept = Double.valueOf(Math.round(sumflameSept/flamevalueSept.size()));
                        avgflameSept = Float.parseFloat(String.valueOf(sumflameSept));
                        //carbon
                        sumcarbonSept = 0.0;
                        for(Double value: carbonvalueSept)
                        {
                            sumcarbonSept = (sumcarbonSept += value);
                        }
                        sumcarbonSept = Double.valueOf(Math.round(sumcarbonSept/carbonvalueSept.size()));
                        avgcarbonSept = Float.parseFloat(String.valueOf(sumcarbonSept));

                        //october
                        //aqi
                        sumOct = 0.0;
                        for(Double value: aqivalueOct)
                        {
                            sumOct = (sumOct += value);
                        }
                        sumOct = Double.valueOf(Math.round(sumOct/aqivalueOct.size()));
                        avgOct = Float.parseFloat(String.valueOf(sumOct));
                        //flame
                        sumflameOct = 0.0;
                        for(Double value: flamevalueOct)
                        {
                            sumflameOct = (sumflameOct += value);
                        }
                        sumflameOct = Double.valueOf(Math.round(sumflameOct/flamevalueOct.size()));
                        avgflameOct = Float.parseFloat(String.valueOf(sumflameOct));
                        //carbon
                        sumcarbonOct = 0.0;
                        for(Double value: carbonvalueOct)
                        {
                            sumcarbonOct = (sumcarbonOct += value);
                        }
                        sumcarbonOct = Double.valueOf(Math.round(sumcarbonOct/carbonvalueOct.size()));
                        avgcarbonOct = Float.parseFloat(String.valueOf(sumcarbonOct));

                        //november
                        //aqi
                        sumNov = 0.0;
                        for(Double value: aqivalueNov)
                        {
                            sumNov = (sumNov += value);
                        }
                        sumNov = Double.valueOf(Math.round(sumNov/aqivalueNov.size()));
                        avgNov = Float.parseFloat(String.valueOf(sumNov));
                        //flame
                        sumflameNov = 0.0;
                        for(Double value: flamevalueNov)
                        {
                            sumflameNov = (sumflameNov += value);
                        }
                        sumflameNov = Double.valueOf(Math.round(sumflameNov/flamevalueNov.size()));
                        avgflameNov = Float.parseFloat(String.valueOf(sumflameNov));
                        //carbon
                        sumcarbonNov = 0.0;
                        for(Double value: carbonvalueNov)
                        {
                            sumcarbonNov = (sumcarbonNov += value);
                        }
                        sumcarbonNov = Double.valueOf(Math.round(sumcarbonNov/carbonvalueNov.size()));
                        avgcarbonNov = Float.parseFloat(String.valueOf(sumcarbonNov));

                        //december
                        //aqi
                        sumDec = 0.0;
                        for(Double value: aqivalueDec)
                        {
                            sumDec = (sumDec += value);
                        }
                        sumDec = Double.valueOf(Math.round(sumDec/aqivalueDec.size()));
                        avgDec = Float.parseFloat(String.valueOf(sumDec));
                        //flame
                        sumflameDec = 0.0;
                        for(Double value: flamevalueDec)
                        {
                            sumflameDec = (sumflameDec += value);
                        }
                        sumflameDec = Double.valueOf(Math.round(sumflameDec/flamevalueDec.size()));
                        avgflameDec = Float.parseFloat(String.valueOf(sumflameDec));
                        //carbon
                        sumcarbonDec = 0.0;
                        for(Double value: carbonvalueDec)
                        {
                            sumcarbonDec = (sumcarbonDec += value);
                        }
                        sumcarbonDec = Double.valueOf(Math.round(sumcarbonDec/carbonvalueDec.size()));
                        avgcarbonDec = Float.parseFloat(String.valueOf(sumcarbonDec));

                        //aqi
                        lineEntriesAqiMonthly.add(new Entry(0, avgJan));
                        lineEntriesAqiMonthly.add(new Entry(1, avgFeb));
                        lineEntriesAqiMonthly.add(new Entry(2, avgMar));
                        lineEntriesAqiMonthly.add(new Entry(3, avgApr));
                        lineEntriesAqiMonthly.add(new Entry(4, avgMay));
                        lineEntriesAqiMonthly.add(new Entry(5, avgJun));
                        lineEntriesAqiMonthly.add(new Entry(6, avgJuly));
                        lineEntriesAqiMonthly.add(new Entry(7, avgAug));
                        lineEntriesAqiMonthly.add(new Entry(8, avgSept));
                        lineEntriesAqiMonthly.add(new Entry(9, avgOct));
                        lineEntriesAqiMonthly.add(new Entry(10, avgNov));
                        lineEntriesAqiMonthly.add(new Entry(11, avgDec));
                        //flame
                        lineEntriesFlameMonthly.add(new Entry(0, avgflameJan));
                        lineEntriesFlameMonthly.add(new Entry(1, avgflameFeb));
                        lineEntriesFlameMonthly.add(new Entry(2, avgflameMar));
                        lineEntriesFlameMonthly.add(new Entry(3, avgflameApr));
                        lineEntriesFlameMonthly.add(new Entry(4, avgflameMay));
                        lineEntriesFlameMonthly.add(new Entry(5, avgflameJun));
                        lineEntriesFlameMonthly.add(new Entry(6, avgflameJuly));
                        lineEntriesFlameMonthly.add(new Entry(7, avgflameAug));
                        lineEntriesFlameMonthly.add(new Entry(8, avgflameSept));
                        lineEntriesFlameMonthly.add(new Entry(9, avgflameOct));
                        lineEntriesFlameMonthly.add(new Entry(10, avgflameNov));
                        lineEntriesFlameMonthly.add(new Entry(11, avgflameDec));
                        //carbon
                        lineEntriesCarbonMonthly.add(new Entry(0, avgcarbonJan));
                        lineEntriesCarbonMonthly.add(new Entry(1, avgcarbonFeb));
                        lineEntriesCarbonMonthly.add(new Entry(2, avgcarbonMar));
                        lineEntriesCarbonMonthly.add(new Entry(3, avgcarbonApr));
                        lineEntriesCarbonMonthly.add(new Entry(4, avgcarbonMay));
                        lineEntriesCarbonMonthly.add(new Entry(5, avgcarbonJun));
                        lineEntriesCarbonMonthly.add(new Entry(6, avgcarbonJuly));
                        lineEntriesCarbonMonthly.add(new Entry(7, avgcarbonAug));
                        lineEntriesCarbonMonthly.add(new Entry(8, avgcarbonSept));
                        lineEntriesCarbonMonthly.add(new Entry(9, avgcarbonOct));
                        lineEntriesCarbonMonthly.add(new Entry(10, avgcarbonNov));
                        lineEntriesCarbonMonthly.add(new Entry(11, avgcarbonDec));

                        //String setter in x-Axis
                        //aqi
                        chartAQI.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValuesMonthly));
                        lineDataSet = new LineDataSet(lineEntriesAqiMonthly, "Air Quality Index");
                        lineData = new LineData(lineDataSet);
                        lineData.setValueFormatter(new MyValueFormatter());
                        chartAQI.setData(lineData);
                        lineDataSet.setColors(Color.parseColor("#3B96EE"));
                        lineDataSet.setValueTextColor(Color.BLACK);
                        lineDataSet.setValueTextSize(10);
                        chartAQI.invalidate();
                        //flame
                        chartFlame.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValuesMonthly));
                        lineDataSet = new LineDataSet(lineEntriesFlameMonthly, "Flammable Gas");
                        lineData = new LineData(lineDataSet);
                        lineData.setValueFormatter(new MyValueFormatter());
                        chartFlame.setData(lineData);
                        lineDataSet.setColors(Color.parseColor("#3B96EE"));
                        lineDataSet.setValueTextColor(Color.BLACK);
                        lineDataSet.setValueTextSize(10);
                        chartFlame.invalidate();
                        //carbon
                        chartCarbon.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValuesMonthly));
                        lineDataSet = new LineDataSet(lineEntriesCarbonMonthly, "Carbon Monoxide");
                        lineData = new LineData(lineDataSet);
                        lineData.setValueFormatter(new MyValueFormatter());
                        chartCarbon.setData(lineData);
                        lineDataSet.setColors(Color.parseColor("#3B96EE"));
                        lineDataSet.setValueTextColor(Color.BLACK);
                        lineDataSet.setValueTextSize(10);
                        chartCarbon.invalidate();

                        //Log.d("TAG", String.valueOf("Data read from DB : "+aqivalueNov.size()));

                        aqivalueJan.clear();
                        aqivalueFeb.clear();
                        aqivalueMar.clear();
                        aqivalueApr.clear();
                        aqivalueMay.clear();
                        aqivalueJun.clear();
                        aqivalueJuly.clear();
                        aqivalueAug.clear();
                        aqivalueSept.clear();
                        aqivalueOct.clear();
                        aqivalueNov.clear();
                        aqivalueDec.clear();
                        flamevalueJan.clear();
                        flamevalueFeb.clear();
                        flamevalueMar.clear();
                        flamevalueApr.clear();
                        flamevalueMay.clear();
                        flamevalueJun.clear();
                        flamevalueJuly.clear();
                        flamevalueAug.clear();
                        flamevalueSept.clear();
                        flamevalueOct.clear();
                        flamevalueNov.clear();
                        flamevalueDec.clear();
                        carbonvalueJan.clear();
                        carbonvalueFeb.clear();
                        carbonvalueMar.clear();
                        carbonvalueApr.clear();
                        carbonvalueMay.clear();
                        carbonvalueJun.clear();
                        carbonvalueJuly.clear();
                        carbonvalueAug.clear();
                        carbonvalueSept.clear();
                        carbonvalueOct.clear();
                        carbonvalueNov.clear();
                        carbonvalueDec.clear();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                };
                followingRef.addListenerForSingleValueEvent(valueEventListener);

            }
        });

        clear = (Button) view.findViewById(R.id.buttonClear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query deleteQuery = ref.child("history");

                deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot deleteSnapshot: dataSnapshot.getChildren()) {
                            deleteSnapshot.getRef().removeValue();
                            chartAQI.clear();
                            chartCarbon.clear();
                            chartFlame.clear();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
            }
        });

        return view;
    }

    public class MyValueFormatter extends ValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("#");
        }

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(value);
        }
    }
}