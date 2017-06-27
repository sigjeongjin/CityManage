package com.citymanage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

public class PushHistoryActivity extends AppCompatActivity {

    ListView pushHistoryListView;
    PushHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_history);

        String url = "";
        HttpConnect httpConnect = new HttpConnect();
        httpConnect.execute();

        Log.d("STRING",httpConnect.jsonReturnObject().toString());

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
}
