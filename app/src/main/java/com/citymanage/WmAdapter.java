package com.citymanage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

/**
 * Created by minjeongkim on 2017-06-30.
 */


public class WmAdapter extends BaseAdapter implements Filterable {
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
        WmItemView view = new WmItemView(context);
        //final Object object = WmItem.get(position);


//        WmItemView view = null;

//        if (convertView == null) {
//            view = new WmItemView(context);
//        } else {
//            view = (WmItemView) convertView;
//        }

        WmItem item = items.get(position);
        view.setSensorId(item.getSensorId());
        view.setAddress(item.getAddress());
        view.setWaterQuality(item.getWaterQuality());
        view.setWaterLevel(item.getWaterLevel());

        return view;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    Filter addressFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            // TODO Auto-generated method stub

            ArrayList<String> temp_list = new ArrayList<String>();

            Wm = (ArrayList<WmItem>) results.values; // 2) result 된 데이터를 받아온다

            for(int i=0; i<call.size(); i++){

                temp_list.add(call.get(i).getCall_name());


            }
    }

}