package com.example.homeairquality.ui.moreinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.homeairquality.R;


public class MoreInfoFragment extends Fragment
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

    String[] desc = {"The Air Quality Index (AQI) is used for reporting daily air quality. It tells you how clean or polluted your air is, and what associated health effects might be a concern for you.",
            "The rising number of indoor air pollutants has made breathing fresh, clean air next to impossible. The causes of indoor air pollution have left everyone worried about their health.",
            "Air pollution is a risk for all-cause mortality as well as specific diseases. The specific disease outcomes most strongly linked with exposure to air pollution include stroke and cataract",
            "The sky turned grey and the air started smelling of soot in Delhi last week. We were told that the AQI levels had gone past the dangerous level. What is AQI? What does it indicate?",
            "Healthy and clean air allows us to take a deep breath and sleep soundly. We spend approx. 90% of our time indoors. However, according to the WHO, the air inside is 2-5 times more polluted than the outside air.",
            "This three-minute video describes how indoor air quality impacts your lungs, as well as providing practical tips for improving the indoor air quality in your home."};

    ListView lView;

    ListAdapter lAdapter;

    public MoreInfoFragment(){
        // require a empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_moreinfo, container, false);

        lView = (ListView) view.findViewById(R.id.infoList);

        lAdapter = new ListAdapter(getActivity(), title, desc, images);

        lView.setAdapter(lAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(getActivity(), title[i]+" "+ i , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),FullInfoActivity.class);
                intent.putExtra("id",i);
                startActivity(intent);
            }
        });
        return view;
    }


}