package com.citymanage.favorite;

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

import static com.citymanage.R.id.favoriteHistoryListView;


public class FavoriteActivity extends SideNaviBaseActivity implements View.OnClickListener {

    final static String SENSORID = "sensorId";

    //wm : 수질    tm : 쓰레기통   gm : 도시가스   sm : 금연구역
    CheckBox gWmChk, gTmChk, gGmChk, gSmChk;

    static final String WM = "wm";
    static final String TM = "tm";
    static final String GM = "gm";
    static final String SM = "sm";

    //super 클래스에서 favoritehistoryurl 받아오기
    String gFavoriteHistoryUrl = FAVORITE_HOST; // URL 변경

    ListView gFavoriteHistoryLv; //통신 후 받은 데이터 표현할 리스트
    FavoriteAdapter adapter; // 위의 리스트 adapter

    List<HashMap<String,String>> gListFavoriteHistory = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        super.setupToolbar();
        setTitle(R.string.navigation_favorite);

        /** 체크 박스 셋팅 시작(객체 생성, 체크 박스 태그 생성, 체크 박스 리스너 등록) **/
        gWmChk = (CheckBox) findViewById(R.id.wmCheckBox);
        gTmChk = (CheckBox) findViewById(R.id.tmCheckBox);
        gGmChk = (CheckBox) findViewById(R.id.gmCheckBox);
        gSmChk = (CheckBox) findViewById(R.id.smCheckBox);

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

        gFavoriteHistoryLv = (ListView) findViewById(favoriteHistoryListView);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();

        StringRequest favoriteHistoryRequest = new StringRequest(gFavoriteHistoryUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);
                adapter = new FavoriteAdapter(getApplicationContext());

                for(int i = 0; i < gListFavoriteHistory.size(); i ++ ) {
                    adapter.addItem(new FavoriteItem(gListFavoriteHistory.get(i).get("addressInfo"),
                            gListFavoriteHistory.get(i).get("sensorId"), gListFavoriteHistory.get(i).get("favoriteDescription")));
                }
                gFavoriteHistoryLv.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(FavoriteActivity.this);
        rQueue.add(favoriteHistoryRequest);

        gFavoriteHistoryLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), TmInfoActivity.class);
                intent.putExtra(SENSORID,gListFavoriteHistory.get(position).get(SENSORID));
                startActivity(intent);

            }
        });
    }

    @Override
    public void onClick(View v) {
        dialog = new ProgressDialog(FavoriteActivity.this);
        dialog.setMessage("Loading....");
        dialog.show();

        StringBuilder sb = new StringBuilder(gFavoriteHistoryUrl);

        sb.append(checkedSettingUrl(v.getTag().toString()));

        StringRequest favoriteHistoryItemRequest = new StringRequest(sb.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {

            //통신을 해서 데이터를 받아 오기전에 리스트뷰를 아무것도 없는 상태로 셋팅한다.
            adapter.clearItemAll(); //어댑터에 셋팅된 아이템 전부 삭제
            adapter.notifyDataSetChanged(); //어댑터 정보 갱신

            parseJsonData(string);

            for(int i = 0; i < gListFavoriteHistory.size(); i ++ ) {
                adapter.addItem(new FavoriteItem(gListFavoriteHistory.get(i).get("addressInfo"),
                        gListFavoriteHistory.get(i).get("sensorId"), gListFavoriteHistory.get(i).get("favoriteDescription")));
            }
            gFavoriteHistoryLv.setAdapter(adapter);

            gFavoriteHistoryLv.deferNotifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(FavoriteActivity.this);
        rQueue.add(favoriteHistoryItemRequest);
    }

    //통신 후 json 파싱
    void parseJsonData(String jsonString) {
        try {
            gListFavoriteHistory.clear();

            JSONObject object = new JSONObject(jsonString);

            JSONArray favoriteHistoryArray = object.getJSONArray("favoriteList");

            for(int i = 0; i < favoriteHistoryArray.length(); i ++ ) {

                HashMap<String,String> hashTemp = new HashMap<>();

                String addressInfo = favoriteHistoryArray.getJSONObject(i).getString("addressInfo");
                String sensorId = favoriteHistoryArray.getJSONObject(i).getString("sensorId");
                String favoriteDescription = favoriteHistoryArray.getJSONObject(i).getString("favoriteDescription");

                hashTemp.put("addressInfo",addressInfo);
                hashTemp.put("sensorId",sensorId);
                hashTemp.put("favoriteDescription",favoriteDescription);

                gListFavoriteHistory.add(i,hashTemp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
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
    public boolean providesActivityToolbar() {
        return true;
    }
}