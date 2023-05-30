package com.example.homeairquality.ui.moreinfo;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.homeairquality.R;

public class ListAdapter extends BaseAdapter
{
    Context context;
    private final String [] title;
    private final String [] desc;
    private final int [] images;

    public ListAdapter(Context context, String [] title, String [] desc, int [] images){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.title = title;
        this.desc = desc;
        this.images = images;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_info, parent, false);
            viewHolder.textTitle = (TextView) convertView.findViewById(R.id.textTitle);
            viewHolder.textTitle.setTextSize(20);
            viewHolder.textTitle.setTypeface(null, Typeface.BOLD);
            viewHolder.textTitle.setTextColor(Color.BLACK);
            viewHolder.textDesc = (TextView) convertView.findViewById(R.id.textDesc);
            viewHolder.textDesc.setTextSize(15);
            viewHolder.textDesc.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            viewHolder.textDesc.setTextColor(Color.BLACK);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.imageItem);
            viewHolder.icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.textTitle.setText(title[position]);
        viewHolder.textDesc.setText(desc[position]);
        viewHolder.icon.setImageResource(images[position]);

        return convertView;
    }

    private static class ViewHolder {

        TextView textTitle;
        TextView textDesc;
        ImageView icon;

    }

}
