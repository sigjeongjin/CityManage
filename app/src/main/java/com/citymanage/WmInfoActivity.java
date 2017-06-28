package com.citymanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class WmInfoActivity extends AppCompatActivity {
    TextView addressText;
    TextView waterQualityText;
    TextView waterLevelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wm_info);

        addressText = (TextView) findViewById(R.id.addressText);
        waterQualityText = (TextView) findViewById(R.id.waterQualityText);
        waterLevelText = (TextView) findViewById(R.id.waterLevelText);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        addressText.setText(intent.getStringExtra("address"));
        waterQualityText.setText(intent.getStringExtra("waterQuality"));
        waterLevelText.setText(intent.getStringExtra("waterLevel"));

    }
}
