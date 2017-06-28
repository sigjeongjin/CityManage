package com.citymanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnPushHistoryActivityGo;
    Button wmMapActivityGo;
    Button gmMapActivityGo;
    Button tmMapActivityGo;
    Button smMapActivityGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //수질관리 화면으로 이동
        wmMapActivityGo = (Button) findViewById(R.id.wmMapActivityGo);

        wmMapActivityGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WmMapActivity.class);
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
        gmMapActivityGo = (Button) findViewById(R.id.gmMapActivityGo);

        gmMapActivityGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GmMapActivity.class);
                startActivity(intent);
            }
        });
        //쓰레기통 관리 화면으로 이동
//        tmMapActivityGo = (Button) findViewById(R.id.tmMapActivityGo);
//
//        tmMapActivityGo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), TmMapActivity.class);
//                startActivity(intent);
//            }
//        });
        //금연구역 관리 화면으로 이동
//        smMapActivityGo = (Button) findViewById(R.id.smMapActivityGo);
//
//        smMapActivityGo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SmMapActivity.class);
//                startActivity(intent);
//            }
//        });

    }
}


