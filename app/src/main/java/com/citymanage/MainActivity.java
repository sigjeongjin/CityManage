package com.citymanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnPushHistoryActivityGo;
    Button wmListActivityGo;
    Button gmListActivityGo;
    Button tmListActivityGo;
    Button smListActivityGo;
    Button settingActivityGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //수질관리 화면으로 이동
        wmListActivityGo = (Button) findViewById(R.id.wmListActivityGo);

        wmListActivityGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WmListActivity.class);
                startActivity(intent);
            }
        });
        // 푸시 이력으로 이동
        btnPushHistoryActivityGo = (Button) findViewById(R.id.pushHistoryActivityGo);

        btnPushHistoryActivityGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PushHistoryActivity.class);
                startActivity(intent);
            }
        });
        //도시가스 관리 화면으로 이동

        settingActivityGo = (Button) findViewById(R.id.settingActivityGo);

        settingActivityGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        //쓰레기통 관리 화면으로 이동
        tmListActivityGo = (Button) findViewById(R.id.tmListActivityGo);

        tmListActivityGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TmListActivity.class);
                startActivity(intent);
            }
        });
        //금연구역 관리 화면으로 이동
        smListActivityGo = (Button) findViewById(R.id.smListActivityGo);

        smListActivityGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SmListActivity.class);
                startActivity(intent);
            }
        });

    }
}






