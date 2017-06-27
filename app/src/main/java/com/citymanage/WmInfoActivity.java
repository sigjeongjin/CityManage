package com.citymanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class WmInfoActivity extends AppCompatActivity {
    TextView textView;
    TextView textView2;
    TextView textView3;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wm_info);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        imageView = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        textView.setText(intent.getStringExtra("name"));
        textView2.setText(intent.getStringExtra("mobile"));
        //textView3.setText(intent.getIntExtra("age",0));
        imageView.setImageResource(intent.getIntExtra("resId", 0));


    }
}
