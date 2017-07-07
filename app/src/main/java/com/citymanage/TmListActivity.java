package com.citymanage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TmListActivity extends BaseActivity {

    String resultCode;

    TmListAdapter adapter; // 위의 리스트 adapter
    ListView tmListView;
    EditText streetFindEv;
    Button searchBtn;
    Button tmMapActivityGoBtn;

    List<HashMap<String,String>> mListHashTm = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tm_list);

        tmMapActivityGoBtn = (Button) findViewById(R.id.tmMapActivityGoBtn);
        tmListView = (ListView) findViewById(R.id.tmLv);
        streetFindEv = (EditText) findViewById(R.id.streetFindEv);
        searchBtn = (Button) findViewById(R.id.searchBtn);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();

        StringRequest pushHistoryRequest = new StringRequest(TM_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);
                adapter = new TmListAdapter(getApplicationContext());

                for(int i = 0; i < mListHashTm.size(); i ++ ) {
                    adapter.addItem(new TmListItem(mListHashTm.get(i).get("addressInfo"),
                            mListHashTm.get(i).get("sensorId")));
                }
                tmListView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                Log.i("volley error : ",volleyError.toString());
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(TmListActivity.this);
        rQueue.add(pushHistoryRequest);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder sb = new StringBuilder(TM_LIST_URL);
                String strStreet = streetFindEv.getText().toString();

                Log.i("STRING STREET LENGHT ", String.valueOf(strStreet.length()));

                try {
                    if(strStreet.length() > 0) {
                        sb.append("?find=");
                        sb.append(URLEncoder.encode(streetFindEv.getText().toString(),"UTF-8"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.i("URL TEST", sb.toString());

                StringRequest pushHistoryRequest = new StringRequest(sb.toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {
                        parseJsonData(string);
                        adapter = new TmListAdapter(getApplicationContext());

                        for(int i = 0; i < mListHashTm.size(); i ++ ) {
                            adapter.addItem(new TmListItem(mListHashTm.get(i).get("addressInfo"),
                                    mListHashTm.get(i).get("sensorId")));
                        }
                        tmListView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                        Log.i("volley error : ",volleyError.toString());
                        dialog.dismiss();
                    }
                });

                RequestQueue rQueue = Volley.newRequestQueue(TmListActivity.this);
                rQueue.add(pushHistoryRequest);
            }
        });


        tmMapActivityGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TmMapActivity.class);
                startActivity(intent);
            }
        });
    }

    //통신 후 json 파싱
    void parseJsonData(String jsonString) {
        try {
            mListHashTm.clear();

            JSONObject object = new JSONObject(jsonString);

            JSONArray tmListArray = object.getJSONArray("tmList");

            for(int i = 0; i < tmListArray.length(); i ++ ) {

                HashMap<String,String> hashTemp = new HashMap<>();

                String addressInfo = tmListArray.getJSONObject(i).getString("addressInfo");
                String sensorId = tmListArray.getJSONObject(i).getString("sensorId");

                hashTemp.put("addressInfo",addressInfo);
                hashTemp.put("sensorId",sensorId);

                mListHashTm.add(i,hashTemp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }
}
