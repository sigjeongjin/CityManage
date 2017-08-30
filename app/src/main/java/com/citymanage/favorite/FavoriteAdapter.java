package com.citymanage.favorite;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by we25 on 2017-06-26.
 */

public class FavoriteAdapter extends BaseAdapter {
    ArrayList<FavoriteItem> items = new ArrayList<FavoriteItem>();
    Context context;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(FavoriteItem item) {
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
        FavoriteItemView view = new FavoriteItemView(context);

        FavoriteItem item = items.get(position);
        view.setAddressInfo(item.getAddressInfo());
        view.setSensorId(item.getSensorId());
        return view;
    }
}
