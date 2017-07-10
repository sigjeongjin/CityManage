package com.citymanage;

/**
 * Created by minjeongkim on 2017-06-30.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class WmListAdapter extends BaseAdapter {

    ArrayList<WmListItem> items = new ArrayList<WmListItem>();
    Context context;

    public WmListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(WmListItem item) {
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

    public void clearItemAll() {
        this.items.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        WmListItemView view = new WmListItemView(context);

        WmListItem item = items.get(position);
        view.setAddressInfo(item.getAddressInfo());
        view.setSensorId(item.getSensorId());
        return view;
    }
}

