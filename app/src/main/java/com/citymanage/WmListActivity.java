package com.citymanage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.HashMap;


public class WmListActivity extends AppCompatActivity {
    EditText addressInput;
    TextView textView;

    Button wmMapActivityGo;
    Button searchBtn;

    ListView listView;
    WmAdapter adapter;


    String resultCode;
    String url = "http://192.168.0.229:3000/wmList";
    ProgressDialog dialog;


    ArrayList<HashMap<String, String>> wmArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wm_list);

        searchBtn = (Button) findViewById(R.id.searchBtn);
        addressInput = (EditText) findViewById(R.id.addressInput);
        addressInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {
                searchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Search = addressInput.getText().toString();
                        WmListActivity.this.adapter.getFilter().filter(Search);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable edit) {
            }
        });

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
        adapter = new WmAdapter(this);

        //adapter = new WmAdapter();

        listView = (ListView) findViewById(R.id.listView);

//        dialog = new ProgressDialog(WmListActivity.this);
//        dialog.setMessage("Loading....");
//        dialog.show();


        Log.i("STRING", "TEST");

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                Log.i("onResponse", "onResponse");


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
    }


    void parseJsonData(String jsonString) {

        wmArrayList = new ArrayList<HashMap<String, String>>();
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
                String addressInfo = wmArray.getJSONObject(i).getString("addressInfo");
                String waterQuality = wmArray.getJSONObject(i).getString("waterQuality");
                String waterLevel = wmArray.getJSONObject(i).getString("waterLevel");


                adapter.addItem(new WmItem(sensorId, address, addressInfo, waterQuality, waterLevel));
            }
//            for (int i = 0; i < wmArray.length(); i++) {
//                HashMap<String, String> map = new HashMap<String, String>();
//
//                map.put("sensorId", wmArray.getJSONObject(i).getString("sensorId"));
//                map.put("address", wmArray.getJSONObject(i).getString("address"));
//                map.put("waterQuality", wmArray.getJSONObject(i).getString("waterQuality"));
//                map.put("waterLevel", wmArray.getJSONObject(i).getString("waterLevel"));
//
//                wmArrayList.add(map);
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        dialog.dismiss();
    }

}
