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

import org.json.JSONException;
import org.json.JSONObject;

public class PushHistoryActivity extends AppCompatActivity {

    private final static String HOST = "http://192.168.0.230:3000";
    private final static String PUSHHISTORY = "http://192.168.0.230:3000/pushHistory";
    private final static String LOGIN = "http://192.168.0.230:3000/login";
    private final static String REGISTER = "http://192.168.0.230:3000/register";

    ListView pushHistoryListView;
    PushHistoryAdapter adapter;
    CheckBox wmCheck;

    ProgressDialog dialog;

    String url = PUSHHISTORY;

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
            String resultCode = object.getString("resultCode");

            Log.d("RESULTCODE", resultCode);

            int a = Integer.parseInt(resultCode);



//            JSONObject object = new JSONObject(jsonString);
//            JSONArray fruitsArray = object.getJSONArray("fruits");
//            ArrayList al = new ArrayList();
//
//            for(int i = 0; i < fruitsArray.length(); ++i) {
//                al.add(fruitsArray.getString(i));
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }

//        pushHistoryListView = (ListView) findViewById(R.id.pushHistoryListView);
//
//        adapter = new PushHistoryAdapter(getApplicationContext());
//
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//        adapter.addItem(new PushHistoryItem("title","contents"));
//
//        pushHistoryListView.setAdapter(adapter);

}
