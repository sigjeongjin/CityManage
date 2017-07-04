package com.citymanage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

public class PushHistoryActivity extends BaseActivity {

    //wm : 수질    tm : 쓰레기통   gm : 도시가스   sm : 금연구역
    CheckBox wmCheckBox, tmCheckBox, gmCheckBox, smCheckBox;

    //super 클래스에서 pushhistoryurl 받아오기
    String pushHistoryUrl = PUSH_HISTORY_HOST;

    ListView pushHistoryListView; //통신 후 받은 데이터 표현할 리스트
    PushHistoryAdapter adapter; // 위의 리스트 adapter

    List<HashMap<String,String>> pushHistoryList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_history);

        wmCheckBox = (CheckBox) findViewById(R.id.wmCheckBox);
        tmCheckBox = (CheckBox) findViewById(R.id.tmCheckBox);
        gmCheckBox = (CheckBox) findViewById(R.id.gmCheckBox);
        smCheckBox = (CheckBox) findViewById(R.id.smCheckBox);

        wmCheckBox.setTag("wm");
        tmCheckBox.setTag("tm");
        gmCheckBox.setTag("gm");
        smCheckBox.setTag("sm");

        pushHistoryListView = (ListView) findViewById(R.id.pushHistoryListView);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();

        StringRequest pushHistoryRequest = new StringRequest(pushHistoryUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);
                adapter = new PushHistoryAdapter(getApplicationContext());

                for(int i = 0; i < pushHistoryList.size(); i ++ ) {
                    adapter.addItem(new PushHistoryItem(pushHistoryList.get(i).get("title"),"contents"));
                }
                pushHistoryListView.setAdapter(adapter);
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

        wmCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(wmCheckBox.isChecked()){
                    StringBuilder sb = new StringBuilder(pushHistoryUrl);

//                  추후에 개발 (체크 박스 여러개 눌렸을경우 여러 아이템 동시 조회)
                    sb.append(checkedSettingUrl(sb));

                    dialog = new ProgressDialog(PushHistoryActivity.this);
                    dialog.setMessage("Loading....");
                    dialog.show();

                    StringRequest pushHistoryItemRequest = new StringRequest(sb.toString(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String string) {

                            //통신을 해서 데이터를 받아 오기전에 리스트뷰를 아무것도 없는 상태로 셋팅한다.
                            adapter.clearItemAll(); //어댑터에 셋팅된 아이템 전부 삭제
                            adapter.notifyDataSetChanged(); //어댑터 정보 갱신

                            parseJsonData(string);

                            for(int i = 0; i < pushHistoryList.size(); i ++ ) {
                                adapter.addItem(new PushHistoryItem(pushHistoryList.get(i).get("title"),"contents"));
                            }
                            pushHistoryListView.setAdapter(adapter);

                            pushHistoryListView.deferNotifyDataSetChanged();
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
            }
        });
    }

    //통신 후 json 파싱
    void parseJsonData(String jsonString) {
        try {
            pushHistoryList.clear();

            JSONObject object = new JSONObject(jsonString);

            JSONArray pushHistorArray = object.getJSONArray("pushHistoryList");

            for(int i = 0; i < pushHistorArray.length(); i ++ ) {

                HashMap<String,String> hashTemp = new HashMap<>();

                String title = pushHistorArray.getJSONObject(i).getString("title");
                String contents = pushHistorArray.getJSONObject(i).getString("contents");

                hashTemp.put("title",title);
                hashTemp.put("contents",contents);

                pushHistoryList.add(i,hashTemp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }


    public String checkedSettingUrl(StringBuilder pSb) {
        //wmCheckBox, tmCheckBox, gmCheckBox, smCheckBox
        if(wmCheckBox.isChecked()) {
            tmCheckBox.setChecked(false);
            gmCheckBox.setChecked(false);
            smCheckBox.setChecked(false);
            pSb.append("item=" + wmCheckBox.getTag());
        } else if(tmCheckBox.isChecked()){
            wmCheckBox.setChecked(false);
            gmCheckBox.setChecked(false);
            smCheckBox.setChecked(false);
            pSb.append("item=" + tmCheckBox.getTag());
        } else if(gmCheckBox.isChecked()) {
            wmCheckBox.setChecked(false);
            tmCheckBox.setChecked(false);
            smCheckBox.setChecked(false);
            pSb.append("item=" + gmCheckBox.getTag());
        } else if(smCheckBox.isChecked()) {
            wmCheckBox.setChecked(false);
            tmCheckBox.setChecked(false);
            gmCheckBox.setChecked(false);
            pSb.append("item=" + smCheckBox.getTag());
        }
        return pSb.toString();
    }
}