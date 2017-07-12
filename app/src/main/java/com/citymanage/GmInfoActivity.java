package com.citymanage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.citymanage.BaseActivity.TM_INFO_URL;

public class GmInfoActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_gm_info);

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

        RequestQueue rQueue = Volley.newRequestQueue(GmInfoActivity.this);
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
}
