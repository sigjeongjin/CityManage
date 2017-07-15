package com.citymanage.tm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.citymanage.wm.WmListItem;
import com.citymanage.wm.WmListItemView;

import java.util.ArrayList;

/**
 * Created by we25 on 2017-06-26.
 */

public class TmListAdapter extends BaseAdapter {
    ArrayList<WmListItem> items = new ArrayList<WmListItem>();
    Context context;

    public TmListAdapter(Context context) {
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
