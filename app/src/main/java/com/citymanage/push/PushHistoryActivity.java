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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.citymanage.R;
import com.citymanage.sidenavi.SideNaviBaseActivity;
import com.citymanage.tm.TmInfoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    //super 클래스에서 pushhistoryurl 받아오기
    String gPushHistoryUrl = PUSH_HISTORY_HOST;

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

        StringRequest pushHistoryRequest = new StringRequest(gPushHistoryUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);
                adapter = new PushHistoryAdapter(getApplicationContext());

                for(int i = 0; i < gListPushHistory.size(); i ++ ) {
                    adapter.addItem(new PushHistoryItem(gListPushHistory.get(i).get("addressInfo"),
                            gListPushHistory.get(i).get("sensorId"), gListPushHistory.get(i).get("pushDescription")));
                }
                gPushHistoryLv.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(PushHistoryActivity.this);
        rQueue.add(pushHistoryRequest);

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

        StringBuilder sb = new StringBuilder(gPushHistoryUrl);

        if(v.getTag().equals(ALL)) {
            allCheckedClickEvent();
        } else {
            sb.append(checkedSettingUrl(v.getTag().toString()));
        }

        StringRequest pushHistoryItemRequest = new StringRequest(sb.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {

            //통신을 해서 데이터를 받아 오기전에 리스트뷰를 아무것도 없는 상태로 셋팅한다.
            adapter.clearItemAll(); //어댑터에 셋팅된 아이템 전부 삭제
            adapter.notifyDataSetChanged(); //어댑터 정보 갱신

            parseJsonData(string);

            for(int i = 0; i < gListPushHistory.size(); i ++ ) {
                adapter.addItem(new PushHistoryItem(gListPushHistory.get(i).get("addressInfo"),
                        gListPushHistory.get(i).get("sensorId"), gListPushHistory.get(i).get("pushDescription")));
            }
            gPushHistoryLv.setAdapter(adapter);

            gPushHistoryLv.deferNotifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(PushHistoryActivity.this);
        rQueue.add(pushHistoryItemRequest);
    }

    //통신 후 json 파싱
    void parseJsonData(String jsonString) {
        try {
            gListPushHistory.clear();

            JSONObject object = new JSONObject(jsonString);

            JSONArray pushHistoryArray = object.getJSONArray("pushHistoryList");

            for(int i = 0; i < pushHistoryArray.length(); i ++ ) {

                HashMap<String,String> hashTemp = new HashMap<>();

                String addressInfo = pushHistoryArray.getJSONObject(i).getString("addressInfo");
                String sensorId = pushHistoryArray.getJSONObject(i).getString("sensorId");
                String pushDescription = pushHistoryArray.getJSONObject(i).getString("pushDescription");

                hashTemp.put("addressInfo",addressInfo);
                hashTemp.put("sensorId",sensorId);
                hashTemp.put("pushDescription",pushDescription);

                gListPushHistory.add(i,hashTemp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }

    public void allCheckedClickEvent() {
        gWmChk.setChecked(false);
        gTmChk.setChecked(false);
        gGmChk.setChecked(false);
        gSmChk.setChecked(false);
    }

    public String checkedSettingUrl(String pCheckBoxTag) {

        StringBuilder rItemSetting = new StringBuilder();
        //gWmChk, gTmChk, gGmChk, gSmChk
        if(pCheckBoxTag.equals("wm")) {
            gTmChk.setChecked(false);
            gGmChk.setChecked(false);
            gSmChk.setChecked(false);
            rItemSetting.append("?item=" + gWmChk.getTag());
        } else if(pCheckBoxTag.equals("tm")){
            gWmChk.setChecked(false);
            gGmChk.setChecked(false);
            gSmChk.setChecked(false);
            rItemSetting.append("?item=" + gTmChk.getTag());
        } else if(pCheckBoxTag.equals("gm")) {
            gWmChk.setChecked(false);
            gTmChk.setChecked(false);
            gSmChk.setChecked(false);
            rItemSetting.append("?item=" + gGmChk.getTag());
        } else if(pCheckBoxTag.equals("sm")) {
            gWmChk.setChecked(false);
            gTmChk.setChecked(false);
            gGmChk.setChecked(false);
            rItemSetting.append("?item=" + gSmChk.getTag());
        }
        return rItemSetting.toString();
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