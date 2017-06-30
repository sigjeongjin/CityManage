package com.citymanage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WmItemView extends LinearLayout {
    TextView sensorIdText;
    TextView addressText;
    TextView waterQualityText;
    TextView waterLevelText;

    public WmItemView(Context context) {
        super(context);
        init(context);
    }

    public WmItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        // 컨텐츠가 보여질 xml을 내가만든 xml로 설정하기 위해 inflater을 설정
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.wm_item, this, true);

        sensorIdText = (TextView) findViewById(R.id.sensorIdText);
        addressText = (TextView) findViewById(R.id.addressText);
        waterQualityText = (TextView) findViewById(R.id.waterQualityText);
        waterLevelText = (TextView) findViewById(R.id.waterLevelText);
    }

    public void setSensorId(String address) {
        sensorIdText.setText(address);
    }

    public void setAddress(String address) {
        addressText.setText(address);
    }

    public void setWaterQuality(String waterQuality) {
        waterQualityText.setText(waterQuality);
    }

    public void setWaterLevel(String waterLevel) {
        waterLevelText.setText(waterLevel);
    }

    /*
    public void setTest(int test) {
        textView.setText(String.valueOf(test));
    }
    */
}