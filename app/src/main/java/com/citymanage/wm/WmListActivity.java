package com.citymanage.wm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.citymanage.R;
import com.citymanage.sidenavi.SideNaviBaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.citymanage.R.id.action_settings;

public class WmListActivity extends SideNaviBaseActivity {

    final static String SENSORID = "sensorId";

    String resultCode;

    WmListAdapter adapter; // 위의 리스트 adapter
    ListView wmListView;
    EditText streetFindEv;
    Button searchBtn;

    List<HashMap<String,String>> mListHashWm = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wm_list);
        super.setupToolbar();
        setTitle(R.string.wm_title);

        wmListView = (ListView) findViewById(R.id.wmLv);
        streetFindEv = (EditText) findViewById(R.id.streetFindEv);
        searchBtn = (Button) findViewById(R.id.searchBtn);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();

        StringRequest pushHistoryRequest = new StringRequest(TM_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);
                adapter = new WmListAdapter(getApplicationContext());

                for(int i = 0; i < mListHashWm.size(); i ++ ) {
                    adapter.addItem(new WmListItem(mListHashWm.get(i).get("addressInfo"),
                            mListHashWm.get(i).get("sensorId")));
                }
                wmListView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                Log.i("volley error : ",volleyError.toString());
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(WmListActivity.this);
        rQueue.add(pushHistoryRequest);

        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog = new ProgressDialog(WmListActivity.this);
                dialog.setMessage("Loading....");
                dialog.show();

                StringBuilder sb = new StringBuilder(TM_LIST_URL);
                String strStreet = streetFindEv.getText().toString();

                try {
                    if(strStreet.length() > 0) {
                        sb.append("?find=");
                        sb.append(URLEncoder.encode(streetFindEv.getText().toString(),"UTF-8"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                StringRequest pushHistoryRequest = new StringRequest(sb.toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {
                        parseJsonData(string);
                        adapter = new WmListAdapter(getApplicationContext());

                        for(int i = 0; i < mListHashWm.size(); i ++ ) {
                            adapter.addItem(new WmListItem(mListHashWm.get(i).get("addressInfo"),
                                    mListHashWm.get(i).get("sensorId")));
                        }
                        wmListView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                        Log.i("volley error : ",volleyError.toString());
                        dialog.dismiss();
                    }
                });

                RequestQueue rQueue = Volley.newRequestQueue(WmListActivity.this);
                rQueue.add(pushHistoryRequest);
            }
        });

        wmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getApplicationContext(), WmInfoActivity.class);
            intent.putExtra(SENSORID,mListHashWm.get(position).get(SENSORID));
            startActivity(intent);

            }
        });
    }

    //통신 후 json 파싱
    void parseJsonData(String jsonString) {
        try {
            mListHashWm.clear();

            JSONObject object = new JSONObject(jsonString);

            JSONArray tmListArray = object.getJSONArray("tmList");

            for(int i = 0; i < tmListArray.length(); i ++ ) {

                HashMap<String,String> hashTemp = new HashMap<>();

                String addressInfo = tmListArray.getJSONObject(i).getString("addressInfo");
                String sensorId = tmListArray.getJSONObject(i).getString("sensorId");

                hashTemp.put("addressInfo",addressInfo);
                hashTemp.put("sensorId",sensorId);

                mListHashWm.add(i,hashTemp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case action_settings :
                Intent intent = new Intent(getApplicationContext(), WmMapActivity.class);
                startActivity(intent);
                finish();
                break;
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
