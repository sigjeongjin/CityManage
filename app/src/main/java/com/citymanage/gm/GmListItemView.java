package com.citymanage.gm;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.citymanage.R;

import static com.citymanage.R.id.addressInfoTv;
import static com.citymanage.R.id.sensorIdTv;

/**
 * Created by we25 on 2017-06-26.
 */

public class GmListItemView extends LinearLayout {
    TextView addressInfo;
    TextView sensorId;

    public GmListItemView(Context context) {
        super(context);
        init(context);
    }

    public GmListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    //정보 구별
    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.gm_list_item, this, true);

        addressInfo = (TextView) findViewById(addressInfoTv);
        sensorId = (TextView) findViewById(sensorIdTv);
    }

    public void setAddressInfo(String pAddressInfo) {
        this.addressInfo.setText(pAddressInfo);
    }

    public void setSensorId(String pSensorId) {
        this.sensorId.setText(pSensorId);
    }
}
