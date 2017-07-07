package com.citymanage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.citymanage.R.id.addressInfoTv;
import static com.citymanage.R.id.favoriteDescriptionTv;
import static com.citymanage.R.id.sensorIdTv;

/**
 * Created by we25 on 2017-06-26.
 */

public class FavoriteHistoryItemView extends LinearLayout {
    TextView addressInfo;
    TextView sensorId;
    TextView favoriteDescription;

    public FavoriteHistoryItemView(Context context) {
        super(context);
        init(context);
    }

    public FavoriteHistoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.favorite_history_item, this, true);

        addressInfo = (TextView) findViewById(addressInfoTv);
        sensorId = (TextView) findViewById(sensorIdTv);
        favoriteDescription = (TextView) findViewById(favoriteDescriptionTv);
    }

    public void setAddressInfo(String pAddressInfo) {
        this.addressInfo.setText(pAddressInfo);
    }

    public void setSensorId(String pSensorId) {
        this.sensorId.setText(pSensorId);
    }

    public void setFavoriteDescripTion(String pFavoriteDescription) {
        this.favoriteDescription.setText(pFavoriteDescription);
    }
}
