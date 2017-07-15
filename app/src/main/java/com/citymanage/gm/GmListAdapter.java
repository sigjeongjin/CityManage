package com.citymanage.gm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by we25 on 2017-06-26.
 */

public class GmListAdapter extends BaseAdapter {
    ArrayList<GmListItem> items = new ArrayList<GmListItem>();
    Context context;

    public GmListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(GmListItem item) {
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
        GmListItemView view = new GmListItemView(context);

        GmListItem item = items.get(position);
        view.setAddressInfo(item.getAddressInfo());
        view.setSensorId(item.getSensorId());
        return view;
    }
}
