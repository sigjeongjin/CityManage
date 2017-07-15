package com.citymanage.push;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by we25 on 2017-06-26.
 */

public class PushHistoryAdapter extends BaseAdapter {
    ArrayList<PushHistoryItem> items = new ArrayList<PushHistoryItem>();
    Context context;

    public PushHistoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(PushHistoryItem item) {
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
        PushHistoryItemView view = new PushHistoryItemView(context);

        PushHistoryItem item = items.get(position);
        view.setAddressInfo(item.getAddressInfo());
        view.setSensorId(item.getSensorId());
        view.setPushDescripTion(item.getPushDescription());
        return view;
    }
}
