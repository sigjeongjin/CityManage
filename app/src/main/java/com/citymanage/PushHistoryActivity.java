package com.citymanage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private final static String PUSHHISTORY = "http://192.168.0.230:3000/pushHistory?name=park";
    private final static String LOGIN = "http://192.168.0.230:3000/login";
    private final static String REGISTER = "http://192.168.0.230:3000/register";

    ListView pushHistoryListView;
    PushHistoryAdapter adapter;
    CheckBox wmCheck;

    ProgressDialog dialog;

    String url = PUSHHISTORY;

    int ResultCode;

    List<HashMap<String,String>> pushHistoryList = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_history);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);

                pushHistoryListView = (ListView) findViewById(R.id.pushHistoryListView);
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
        rQueue.add(request);
    }

    void parseJsonData(String jsonString) {
        try {
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
}
