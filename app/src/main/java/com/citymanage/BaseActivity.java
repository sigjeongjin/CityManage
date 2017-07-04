package com.citymanage;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by we25 on 2017-06-30.
 */

public class BaseActivity extends AppCompatActivity {

    public static  final String HOST = "http://192.168.0.230:3000";
    public static  final String LOGIN = "http://192.168.0.230:3000/login";
    public static  final String REGISTER = "http://192.168.0.230:3000/register";
    public static  final String PUSH_HISTORY_HOST = "http://192.168.0.230:3000/pushHistory";
    public ProgressDialog dialog; //프로그레스바 다이얼로그
    int ResultCode; //response 응답코드 변수
    String url = ""; //요청 url 셋팅 변수

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            // Do Code Here

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
