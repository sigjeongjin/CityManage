package com.citymanage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class PushHistoryActivity extends AppCompatActivity {

    private final static String HOST = "http://192.168.0.230:3000";
    private final static String LOGIN = "http://192.168.0.230:3000/login";
    private final static String REGISTER = "http://192.168.0.230:3000/register";

    String pushHistoryUrl = ApiUrlList.getPushHistoryUrl();

    ProgressDialog dialog;

    ListView pushHistoryListView;
    PushHistoryAdapter adapter;

    CheckBox wmCheckBox, tmCheckBox, gmCheckBox, smCheckBox;

    int ResultCode;

    List<HashMap<String,String>> pushHistoryList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_history);

        wmCheckBox = (CheckBox) findViewById(R.id.wmCheckBox);
        tmCheckBox = (CheckBox) findViewById(R.id.tmCheckBox);
        gmCheckBox = (CheckBox) findViewById(R.id.gmCheckBox);
        smCheckBox = (CheckBox) findViewById(R.id.smCheckBox);

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

                    sb.append("?item=wm");

                    dialog = new ProgressDialog(PushHistoryActivity.this);
                    dialog.setMessage("Loading....");
                    dialog.show();

                    StringRequest pushHistoryItemRequest = new StringRequest(sb.toString(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String string) {
                            adapter.clearItemAll();
                            adapter.notifyDataSetChanged();

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

    public void checkBoxIsChecked(CheckBox pCheckBox) {

    }
}