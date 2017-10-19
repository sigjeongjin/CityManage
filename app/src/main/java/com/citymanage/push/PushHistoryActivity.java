package com.citymanage.push;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.citymanage.R;
import com.citymanage.push.repo.PushHistoryInfoRepo;
import com.citymanage.push.repo.PushService;
import com.citymanage.sidenavi.SideNaviBaseActivity;
import com.citymanage.tm.TmInfoActivity;
import com.common.Module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 *
 * @author 박현진
 * @version 1.0.0
 * @since 2017-06-29 오전 10:59
 **/

public class PushHistoryActivity extends SideNaviBaseActivity implements View.OnClickListener {

    final static String SENSORID = "sensorId";

    //wm : 수질    tm : 쓰레기통   gm : 도시가스   sm : 금연구역
    CheckBox gWmChk, gTmChk, gGmChk, gSmChk;

    static final String WM = "wm";
    static final String TM = "tm";
    static final String GM = "gm";
    static final String SM = "sm";
    static final String ALL = "all";

    ListView gPushHistoryLv; //통신 후 받은 데이터 표현할 리스트
    PushHistoryAdapter adapter; // 위의 리스트 adapter

    List<HashMap<String,String>> gListPushHistory = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_history);
        super.setupToolbar();
        setTitle(R.string.navigation_push_history);

        /** 체크 박스 셋팅 시작(객체 생성, 체크 박스 태그 생성, 체크 박스 리스너 등록) **/
        gWmChk = (CheckBox) findViewById(R.id.wmCheckBox);
        gTmChk = (CheckBox) findViewById(R.id.tmCheckBox);
        gGmChk = (CheckBox) findViewById(R.id.gmCheckBox);
        gSmChk = (CheckBox) findViewById(R.id.smCheckBox);

        //모두 검색 하는 체크 박스에 초기 체크 데이터 셋팅

        gWmChk.setTag(WM);
        gTmChk.setTag(TM);
        gGmChk.setTag(GM);
        gSmChk.setTag(SM);

        //인터페이스가 받을 수 있도록 listener 등록
        gWmChk.setOnClickListener(this);
        gTmChk.setOnClickListener(this);
        gGmChk.setOnClickListener(this);
        gSmChk.setOnClickListener(this);
        /** 체크 박스 셋팅 끝(객체 생성, 체크 박스 태그 생성, 체크 박스 리스너 등록) **/

        gWmChk.setChecked(true);

        gPushHistoryLv = (ListView) findViewById(R.id.pushHistoryListView);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEHOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PushService service = retrofit.create(PushService.class);
        final Call<PushHistoryInfoRepo> repos = service.getPushHistoryList(Module.getRecordId(getApplicationContext()),"wm");

        repos.enqueue(new Callback<PushHistoryInfoRepo>(){
            @Override
            public void onResponse(Call<PushHistoryInfoRepo> call, Response<PushHistoryInfoRepo> response) {

                PushHistoryInfoRepo pushInfo = response.body();

                if(pushInfo != null) {
                    gListPushHistory.clear();

                    adapter = new PushHistoryAdapter(getApplicationContext());

                    for(int i = 0; i < pushInfo.getPushHistoryList().size(); i ++ ) {

                        HashMap<String,String> hashTemp = new HashMap<>();

                        String addressInfo = pushInfo.getPushHistoryList().get(i).getLocationName();
                        String sensorId = pushInfo.getPushHistoryList().get(i).getManageId();
                        String pushSendTime = pushInfo.getPushHistoryList().get(i).getPushSendTime();

                        hashTemp.put("addressInfo",addressInfo);
                        hashTemp.put("sensorId",sensorId);
                        hashTemp.put("pushSendTime",pushSendTime);

                        gListPushHistory.add(i,hashTemp);

                        adapter.addItem(new PushHistoryItem(addressInfo,sensorId,pushSendTime));
                    }

                    gPushHistoryLv.setAdapter(adapter);

                } else {
                    Toast.makeText(PushHistoryActivity.this, pushInfo.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<PushHistoryInfoRepo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        gPushHistoryLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), TmInfoActivity.class);
                intent.putExtra(SENSORID,gListPushHistory.get(position).get(SENSORID));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        dialog = new ProgressDialog(PushHistoryActivity.this);
        dialog.setMessage("Loading....");
        dialog.show();

        checkedSettingUrl(v.getTag().toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEHOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CheckBox temp = (CheckBox)v.findViewWithTag(v.getTag());

        if(!temp.isChecked()){
            dialog.dismiss();
            return;
        }

        PushService service = retrofit.create(PushService.class);
        final Call<PushHistoryInfoRepo> repos = service.getPushHistoryList(Module.getRecordId(getApplicationContext()),v.getTag().toString());

        repos.enqueue(new Callback<PushHistoryInfoRepo>(){
            @Override
            public void onResponse(Call<PushHistoryInfoRepo> call, Response<PushHistoryInfoRepo> response) {

                PushHistoryInfoRepo pushInfo = response.body();

                adapter.clearItemAll(); //어댑터에 셋팅된 아이템 전부 삭제
                adapter.notifyDataSetChanged(); //어댑터 정보 갱신

                if(pushInfo != null) {
                    gListPushHistory.clear();

                    adapter = new PushHistoryAdapter(getApplicationContext());

                    for(int i = 0; i < pushInfo.getPushHistoryList().size(); i ++ ) {

                        HashMap<String,String> hashTemp = new HashMap<>();

                        String addressInfo = pushInfo.getPushHistoryList().get(i).getLocationName();
                        String sensorId = pushInfo.getPushHistoryList().get(i).getManageId();
                        String pushSendTime = pushInfo.getPushHistoryList().get(i).getPushSendTime();

                        hashTemp.put("addressInfo",addressInfo);
                        hashTemp.put("sensorId",sensorId);
                        hashTemp.put("pushSendTime",pushSendTime);

                        gListPushHistory.add(i,hashTemp);

                        adapter.addItem(new PushHistoryItem(addressInfo,sensorId,pushSendTime));
                    }

                    gPushHistoryLv.setAdapter(adapter);

                } else {
                    Toast.makeText(PushHistoryActivity.this, pushInfo.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<PushHistoryInfoRepo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    public void allCheckedClickEvent() {
        gWmChk.setChecked(false);
        gTmChk.setChecked(false);
        gGmChk.setChecked(false);
        gSmChk.setChecked(false);
    }

    public void checkedSettingUrl(String pCheckBoxTag) {

        //gWmChk, gTmChk, gGmChk, gSmChk
        if(pCheckBoxTag.equals("wm")) {
            gTmChk.setChecked(false);
            gGmChk.setChecked(false);
            gSmChk.setChecked(false);
        } else if(pCheckBoxTag.equals("tm")){
            gWmChk.setChecked(false);
            gGmChk.setChecked(false);
            gSmChk.setChecked(false);
        } else if(pCheckBoxTag.equals("gm")) {
            gWmChk.setChecked(false);
            gTmChk.setChecked(false);
            gSmChk.setChecked(false);
        } else if(pCheckBoxTag.equals("sm")) {
            gWmChk.setChecked(false);
            gTmChk.setChecked(false);
            gGmChk.setChecked(false);
        }
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
    protected int getSelfNavDrawerItem() {
        return R.id.nav_favorite;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }
}