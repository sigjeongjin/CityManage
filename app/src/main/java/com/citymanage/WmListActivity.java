package com.citymanage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WmListActivity extends AppCompatActivity {
    EditText addressInput;
    TextView textView;

    Button wmMapActivityGo;
    ListView listView;
    WmAdapter adapter;

    String resultCode;
    String url = "http://192.168.0.229:3000/wmList";
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wm_list);

        wmMapActivityGo = (Button) findViewById(R.id.wmMapActivityGo);

        wmMapActivityGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WmMapActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        textView = (TextView) findViewById(R.id.textView);
        adapter = new WmAdapter(getApplicationContext());

        listView = (ListView) findViewById(R.id.listView);
//        dialog = new ProgressDialog(WmListActivity.this);
//        dialog.setMessage("Loading....");
//        dialog.show();


        Log.i("STRING","TEST");

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                Log.i("onResponse","onResponse");
                parseJsonData(string);


                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                //dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(WmListActivity.this);
        rQueue.add(request);




        addressInput = (EditText) findViewById(R.id.addressInput);

        Button button = (Button) findViewById(R.id.searchBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = addressInput.getText().toString();
                String mobile = "010-1000-1000";

                //adapter.addItem(new WmItem(name, mobile, age, R.drawable.muji01));
                adapter.notifyDataSetChanged();
            }
        });

        // liseView click --> WminfoActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                WmItem item = (WmItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getAddress(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), WmInfoActivity.class);

                intent.putExtra("sensorId", adapter.items.get(position).sensorId);
                intent.putExtra("address", adapter.items.get(position).address);
                intent.putExtra("waterQuality", adapter.items.get(position).waterQuality);
                intent.putExtra("waterLevel", adapter.items.get(position).waterLevel);

                startActivityForResult(intent, 1001);
            }
        });
    }


    void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);

            Log.i("JSONOBJECT", object.toString());
            Log.d("RESULTCODE", "ListTest");

            resultCode = object.getString("resultCode");

            textView.setText(resultCode);
            JSONArray wmArray = object.getJSONArray("list");

            for(int i = 0 ; i < wmArray.length(); i++) {
                String sensorId = wmArray.getJSONObject(i).getString("sensorId");
                String address = wmArray.getJSONObject(i).getString("address");
                String waterQuality = wmArray.getJSONObject(i).getString("waterQuality");
                String waterLevel = wmArray.getJSONObject(i).getString("waterLevel");

                adapter.addItem(new WmItem(sensorId, address, waterQuality, waterLevel));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        dialog.dismiss();
    }
}
