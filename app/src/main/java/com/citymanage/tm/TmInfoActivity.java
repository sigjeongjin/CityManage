package com.citymanage.tm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.citymanage.R;
import com.citymanage.sidenavi.SideNaviBaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class TmInfoActivity extends SideNaviBaseActivity {

    final static String SENSORID = "sensorId";

    ProgressDialog dialog;

    TextView sensorIdTv;
    TextView locationTv;
    TextView installDayTv;
    TextView fireSensorInfoTv;
    TextView stinkSensorInfoTv;
    TextView garbageSensorInfoTv;

    String strSensorId;
    String strLocation;
    String installDay;
    String fireSensorInfo;
    String stinkSensorInfo;
    String garbageSensorInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tm_info);
        super.setupToolbar();
        setTitle(R.string.tm_title);

        sensorIdTv          = (TextView) findViewById(R.id.sensorIdTv);
        locationTv          = (TextView) findViewById(R.id.locationTv);
        installDayTv        = (TextView) findViewById(R.id.installDayTv);
        fireSensorInfoTv    = (TextView) findViewById(R.id.fireSensorInfoTv);
        stinkSensorInfoTv   = (TextView) findViewById(R.id.stinkSensorInfoTv);
        garbageSensorInfoTv = (TextView) findViewById(R.id.garbageSensorInfoTv);

        Intent intent = getIntent();
        String sensorId = intent.getStringExtra(SENSORID);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();

        StringBuilder sb = new StringBuilder(TM_INFO_URL);
        sb.append("?sensorId=");
        sb.append(sensorId);

        StringRequest pushHistoryRequest = new StringRequest(sb.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);

                sensorIdTv.setText(strSensorId);
                locationTv.setText(strLocation);
                installDayTv.setText(installDay);
                fireSensorInfoTv.setText(fireSensorInfo);
                stinkSensorInfoTv.setText(stinkSensorInfo);
                garbageSensorInfoTv.setText(garbageSensorInfo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                Log.i("volley error : ",volleyError.toString());
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(TmInfoActivity.this);
        rQueue.add(pushHistoryRequest);
    }

    //통신 후 json 파싱
    void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);

            strSensorId = object.getString("sensorId");
            strLocation = object.getString("addressInfo");
            installDay = object.getString("installDay");
            fireSensorInfo = object.getString("fireSensorInfo");
            stinkSensorInfo = object.getString("stinkSensorInfo");
            garbageSensorInfo = object.getString("garbageSensorInfo");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_actions, menu);
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
