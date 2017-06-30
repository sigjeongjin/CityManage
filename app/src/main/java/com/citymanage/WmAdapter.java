package com.citymanage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by minjeongkim on 2017-06-30.
 */


public class WmAdapter extends BaseAdapter {
    ArrayList<WmItem> items = new ArrayList<WmItem>();
    Context context;

    public WmAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(WmItem item) {
        items.add(item);
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        //WmItemView view = new WmItemView(getApplicationContext());

        WmItemView view = null;

        if (convertView == null) {
            view = new WmItemView(context);
        } else {
            view = (WmItemView) convertView;
        }

        WmItem item = items.get(position);
        view.setSensorId(item.getSensorId());
        view.setAddress(item.getAddress());
        view.setWaterQuality(item.getWaterQuality());
        view.setWaterLevel(item.getWaterLevel());


        return view;
    }
}