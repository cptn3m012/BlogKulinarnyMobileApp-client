package com.example.blogkulinarnymobileapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.blogkulinarnymobileapp.R;

import java.util.ArrayList;
import java.util.List;

public class StarAdapter extends ArrayAdapter<String> {

    List<String> starsList = new ArrayList<>();

    public StarAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_stars_list, parent, false);
        }
        TextView fillStars = convertView.findViewById(R.id.singleStarTextView);
        String item = getItem(position);
        fillStars.setText(item);
        return convertView;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    public List<String> getStars() {
        return starsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_stars_list, parent, false);
        }
        TextView fillStars = convertView.findViewById(R.id.singleStarTextView);
        String item = getItem(position);
        fillStars.setText(item);
        return convertView;
    }
}
