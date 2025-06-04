package com.yamatoapps.shiftscheduler.Manager.ui.dashboard;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.yamatoapps.shiftscheduler.R;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends ArrayAdapter<MenuItem> {
    public MenuAdapter(@NonNull Context context, ArrayList<MenuItem> menuItems) {
        super(context, 0, menuItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MenuItem menuItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_card, parent, false);
        }

        TextView tvMenuName =(TextView) convertView.findViewById(R.id.tvMenuName);
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivMenuImage);
        parent.setBackgroundResource(R.drawable.rounded_all_orange);
        tvMenuName.setText(menuItem.menu_name);
        Picasso.get().load(menuItem.menu_image_int).into(ivImage);
        convertView.setOnClickListener(menuItem.onclickListener);
        return convertView;
    }
}
