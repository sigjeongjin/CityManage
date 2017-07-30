package com.citymanage.sm;

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

public class SmListActivity extends SideNaviBaseActivity {

    final static String SENSORID = "sensorId";

    String resultCode;

    SmListAdapter adapter; // 위의 리스트 adapter
    ListView smListView;
    EditText streetFindEv;
    Button searchBtn;

    List<HashMap<String,String>> mListHashSm = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sm_list);
        super.setupToolbar();
        setTitle(R.string.sm_title);

        smListView = (ListView) findViewById(R.id.smLv);
        streetFindEv = (EditText) findViewById(R.id.streetFindEv);
        searchBtn = (Button) findViewById(R.id.searchBtn);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();

        StringRequest pushHistoryRequest = new StringRequest(SM_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);
                adapter = new SmListAdapter(getApplicationContext());

                for(int i = 0; i < mListHashSm.size(); i ++ ) {
                    adapter.addItem(new SmListItem(mListHashSm.get(i).get("addressInfo"),
                            mListHashSm.get(i).get("sensorId")));
                }
                smListView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                Log.i("volley error : ",volleyError.toString());
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(SmListActivity.this);
        rQueue.add(pushHistoryRequest);

        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog = new ProgressDialog(SmListActivity.this);
                dialog.setMessage("Loading....");
                dialog.show();

                StringBuilder sb = new StringBuilder(SM_LIST_URL);
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
                        adapter = new SmListAdapter(getApplicationContext());

                        for(int i = 0; i < mListHashSm.size(); i ++ ) {
                            adapter.addItem(new SmListItem(mListHashSm.get(i).get("addressInfo"),
                                    mListHashSm.get(i).get("sensorId")));
                        }
                        smListView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                        Log.i("volley error : ",volleyError.toString());
                        dialog.dismiss();
                    }
                });

                RequestQueue rQueue = Volley.newRequestQueue(SmListActivity.this);
                rQueue.add(pushHistoryRequest);
            }
        });

        smListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SmInfoActivity.class);
                intent.putExtra(SENSORID,mListHashSm.get(position).get(SENSORID));
                startActivity(intent);
            }
        });
    }

    //통신 후 json 파싱
    void parseJsonData(String jsonString) {
        try {
            mListHashSm.clear();

            JSONObject object = new JSONObject(jsonString);

            //임시테스트용
//            JSONArray smListArray = object.getJSONArray("smList");
            JSONArray smListArray = object.getJSONArray("smList");

            for(int i = 0; i < smListArray.length(); i ++ ) {

                HashMap<String,String> hashTemp = new HashMap<>();

                String addressInfo = smListArray.getJSONObject(i).getString("addressInfo");
                String sensorId = smListArray.getJSONObject(i).getString("sensorId");

                hashTemp.put("addressInfo",addressInfo);
                hashTemp.put("sensorId",sensorId);

                mListHashSm.add(i,hashTemp);
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
                Intent intent = new Intent(getApplicationContext(), SmMapActivity.class);
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
