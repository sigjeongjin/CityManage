package com.citymanage;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
*
* @author 박현진
* @version 1.0.0
* @since 2017-06-29 오전 10:59
**/
//implements View.OnClickListener
public class PushHistoryActivity extends BaseActivity  {

    //wm : 수질    tm : 쓰레기통   gm : 도시가스   sm : 금연구역
    CheckBox gWmChk, gTmChk, gGmChk, gSmChk , gAllChk;

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


//        /** 체크 박스 셋팅 시작(객체 생성, 체크 박스 태그 생성, 체크 박스 리스너 등록) **/
//        gAllChk = (CheckBox) findViewById(R.id.allCheckBox);
//        gWmChk = (CheckBox) findViewById(R.id.wmCheckBox);
//        gTmChk = (CheckBox) findViewById(R.id.tmCheckBox);
//        gGmChk = (CheckBox) findViewById(R.id.gmCheckBox);
//        gSmChk = (CheckBox) findViewById(R.id.smCheckBox);
//
//        //모두 검색 하는 체크 박스에 초기 체크 데이터 셋팅
//        gAllChk.setChecked(true);
//
//        gAllChk.setTag(ALL);
//        gWmChk.setTag(WM);
//        gTmChk.setTag(TM);
//        gGmChk.setTag(GM);
//        gSmChk.setTag(SM);
//
//        //인터페이스가 받을 수 있도록 listener 등록
//        gAllChk.setOnClickListener(this);
//        gWmChk.setOnClickListener(this);
//        gTmChk.setOnClickListener(this);
//        gGmChk.setOnClickListener(this);
//        gSmChk.setOnClickListener(this);
//        /** 체크 박스 셋팅 끝(객체 생성, 체크 박스 태그 생성, 체크 박스 리스너 등록) **/
//
//        gPushHistoryLv = (ListView) findViewById(R.id.pushHistoryListView);
//
//        dialog = new ProgressDialog(this);
//        dialog.setMessage("Loading....");
//        dialog.show();
//
//        StringRequest pushHistoryRequest = new StringRequest(gPushHistoryUrl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String string) {
//                parseJsonData(string);
//                adapter = new PushHistoryAdapter(getApplicationContext());
//
//                for(int i = 0; i < gListPushHistory.size(); i ++ ) {
//                    adapter.addItem(new PushHistoryItem(gListPushHistory.get(i).get("addressInfo"),
//                            gListPushHistory.get(i).get("sensorId"), gListPushHistory.get(i).get("pushDescription")));
//                }
//                gPushHistoryLv.setAdapter(adapter);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//
//        RequestQueue rQueue = Volley.newRequestQueue(PushHistoryActivity.this);
//        rQueue.add(pushHistoryRequest);
//    }
//
//    @Override
//    public void onClick(View v) {
//        dialog = new ProgressDialog(PushHistoryActivity.this);
//        dialog.setMessage("Loading....");
//        dialog.show();
//
//        StringBuilder sb = new StringBuilder(gPushHistoryUrl);
//
//        if(v.getTag().equals(ALL)) {
//            Log.i("ALL","ALL");
//            allCheckedClickEvent();
//        } else {
//            sb.append(checkedSettingUrl(v.getTag().toString()));
//        }
//
//        StringRequest pushHistoryItemRequest = new StringRequest(sb.toString(), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String string) {
//
//            //통신을 해서 데이터를 받아 오기전에 리스트뷰를 아무것도 없는 상태로 셋팅한다.
//            adapter.clearItemAll(); //어댑터에 셋팅된 아이템 전부 삭제
//            adapter.notifyDataSetChanged(); //어댑터 정보 갱신
//
//            parseJsonData(string);
//
//            for(int i = 0; i < gListPushHistory.size(); i ++ ) {
//                adapter.addItem(new PushHistoryItem(gListPushHistory.get(i).get("addressInfo"),
//                        gListPushHistory.get(i).get("sensorId"), gListPushHistory.get(i).get("pushDescription")));
//            }
//            gPushHistoryLv.setAdapter(adapter);
//
//            gPushHistoryLv.deferNotifyDataSetChanged();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//            Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
//            dialog.dismiss();
//            }
//        });
//        RequestQueue rQueue = Volley.newRequestQueue(PushHistoryActivity.this);
//        rQueue.add(pushHistoryItemRequest);
//    }
//
//    //통신 후 json 파싱
//    void parseJsonData(String jsonString) {
//        try {
//            gListPushHistory.clear();
//
//            JSONObject object = new JSONObject(jsonString);
//
//            JSONArray pushHistoryArray = object.getJSONArray("pushHistoryList");
//
//            for(int i = 0; i < pushHistoryArray.length(); i ++ ) {
//
//                HashMap<String,String> hashTemp = new HashMap<>();
//
//                String addressInfo = pushHistoryArray.getJSONObject(i).getString("addressInfo");
//                String sensorId = pushHistoryArray.getJSONObject(i).getString("sensorId");
//                String pushDescription = pushHistoryArray.getJSONObject(i).getString("pushDescription");
//
//                hashTemp.put("addressInfo",addressInfo);
//                hashTemp.put("sensorId",sensorId);
//                hashTemp.put("pushDescription",pushDescription);
//
//                gListPushHistory.add(i,hashTemp);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        dialog.dismiss();
//    }
//
//    public void allCheckedClickEvent() {
//        gWmChk.setChecked(false);
//        gTmChk.setChecked(false);
//        gGmChk.setChecked(false);
//        gSmChk.setChecked(false);
//    }
//
//    public String checkedSettingUrl(String pCheckBoxTag) {
//
//        gAllChk.setChecked(false);
//
//        StringBuilder rItemSetting = new StringBuilder();
//        //gWmChk, gTmChk, gGmChk, gSmChk
//        if(pCheckBoxTag.equals("wm")) {
//            gTmChk.setChecked(false);
//            gGmChk.setChecked(false);
//            gSmChk.setChecked(false);
//            rItemSetting.append("?item=" + gWmChk.getTag());
//        } else if(pCheckBoxTag.equals("tm")){
//            gWmChk.setChecked(false);
//            gGmChk.setChecked(false);
//            gSmChk.setChecked(false);
//            rItemSetting.append("?item=" + gTmChk.getTag());
//        } else if(pCheckBoxTag.equals("gm")) {
//            gWmChk.setChecked(false);
//            gTmChk.setChecked(false);
//            gSmChk.setChecked(false);
//            rItemSetting.append("?item=" + gGmChk.getTag());
//        } else if(pCheckBoxTag.equals("sm")) {
//            gWmChk.setChecked(false);
//            gTmChk.setChecked(false);
//            gGmChk.setChecked(false);
//            rItemSetting.append("?item=" + gSmChk.getTag());
//        }
//        return rItemSetting.toString();
    }
}