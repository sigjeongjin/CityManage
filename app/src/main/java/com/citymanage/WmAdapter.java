package com.citymanage;

/**
 * Created by minjeongkim on 2017-06-30.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

public class WmAdapter extends BaseAdapter implements Filterable {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<WmItem> listViewItemList = new ArrayList<WmItem>();
    ArrayList<WmItem> filteredItemList = listViewItemList;


    public WmAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return filteredItemList.size();
    }

    public void addItem(WmItem item) {
        filteredItemList.add(item);
    }

    @Override
    public Object getItem(int position) {
        return filteredItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        //WmItemView view = new WmItemView(context);

        final int pos = position;
        final Context context = viewGroup.getContext();

        WmItemView view = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.wm_item, viewGroup, false);

            view = new WmItemView(context);
        } else {
            view = (WmItemView) convertView;
        }

        WmItem wmItemList = filteredItemList.get(position);
        view.setSensorId(wmItemList.getSensorId());
        view.setAddress(wmItemList.getAddress());
        view.setAddressInfo(wmItemList.getAddressInfo());
        view.setWaterQuality(wmItemList.getWaterQuality());
        view.setWaterLevel(wmItemList.getWaterLevel());

        return view;
    }

    Filter listFilter;

    @Override
    public Filter getFilter() {
        if(listFilter == null) {
            listFilter = new ListFilter();
        }
        return listFilter;
    }

    private class ListFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = listViewItemList;
                results.count = listViewItemList.size();
            } else {

                ArrayList<WmItem> itemList = new ArrayList<WmItem>();

                for (WmItem item  : listViewItemList) {
                    if(item.getAddressInfo().toUpperCase().contains(constraint.toString().toUpperCase()))
                    {
                        itemList.add(item);
                    }
                }

                results.values = itemList;
                results.count = itemList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItemList = (ArrayList<WmItem>) results.values;

            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}

