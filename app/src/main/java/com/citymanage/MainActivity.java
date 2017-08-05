package com.citymanage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.citymanage.favorite.FavoriteActivity;
import com.citymanage.gm.GmListActivity;
import com.citymanage.member.LoginActivity;
import com.citymanage.push.PushHistoryActivity;
import com.citymanage.setting.SettingActivity;
import com.citymanage.sidenavi.SideNaviBaseActivity;
import com.citymanage.sm.SmListActivity;
import com.citymanage.tm.TmListActivity;
import com.citymanage.wm.WmListActivity;

public class MainActivity extends SideNaviBaseActivity{

    Button wmListActivityGo;
    Button gmListActivityGo;
    Button tmListActivityGo;
    Button smListActivityGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.setupToolbar();

        //수질관리 화면으로 이동
        gmListActivityGo = (Button) findViewById(R.id.gmListActivityGo);

        gmListActivityGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GmListActivity.class);
                startActivity(intent);
            }
        });

        //수질관리 화면으로 이동
        wmListActivityGo = (Button) findViewById(R.id.wmListActivityGo);

        wmListActivityGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WmListActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    @Override
    public void goToNavDrawerItem(int item) {
        switch (item) {
            case R.id.nav_favorite:
                startActivity(new Intent(this, FavoriteActivity.class));
                break;
            case R.id.nav_pushHistory:
                startActivity(new Intent(this, PushHistoryActivity.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.nav_wm:
                startActivity(new Intent(this, WmListActivity.class));
                break;
            case R.id.nav_tm:
                startActivity(new Intent(this, TmListActivity.class));
                break;
            case R.id.nav_gm:
                startActivity(new Intent(this, GmListActivity.class));
                break;
            case R.id.nav_sm:
                startActivity(new Intent(this, SmListActivity.class));
                break;
            case R.id.nav_logout:
                DialogInterface.OnClickListener logoutListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveLoginActivity();
                    }
                };

                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                new AlertDialog.Builder(this)
                        .setTitle("정말 로그아웃 하시겠습니까?")
                        .setPositiveButton("로그아웃", logoutListener)
                        .setNegativeButton("취소", cancelListener)
                        .show();
                break;

        }
    }

    //로그아웃시 화면 이동 함수
    public void moveLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("logout", "logout");
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        createCloseAlertDialog();
    }
    private void createCloseAlertDialog() {
        DialogInterface.OnClickListener loginMoveListener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                intent.putExtra("logout","logout");
                startActivity(intent);
                finish();
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("로그인 화면으로 돌아가시겠습니까?")
                .setPositiveButton("이동",loginMoveListener)
                .setNegativeButton("취소",cancelListener)
                .show();
    }
}






