package com.citymanage.wm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.citymanage.R;
import com.citymanage.sidenavi.SideNaviBaseActivity;
import com.common.Module;
import com.common.repo.SensorInfoRepo;
import com.common.repo.SensorService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.citymanage.wm.WmListActivity.ACTIVITYNAME;

public class WmInfoActivity extends SideNaviBaseActivity {

    final static String SENSORID = "sensorId";

    ProgressDialog dialog;

    TextView sensorIdTv;
    TextView locationTv;
    TextView installDayTv;
    TextView waterLevelSensorInfoTv;
    TextView waterQualitySensorInfoTv;

    String strSensorId;
    String strLocation;
    String installDay;
    String waterLevel;
    String waterQuality;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wm_info);
        super.setupToolbar();
        setTitle(R.string.wm_title);

        sensorIdTv                  = (TextView) findViewById(R.id.sensorIdTv);
        locationTv                  = (TextView) findViewById(R.id.locationTv);
        installDayTv                = (TextView) findViewById(R.id.installDayTv);
        waterLevelSensorInfoTv    = (TextView) findViewById(R.id.waterLevelSensorInfoTv);
        waterQualitySensorInfoTv  = (TextView) findViewById(R.id.waterQualitySensorInfoTv);

        Intent intent = getIntent();
        String sensorId = intent.getStringExtra(SENSORID);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEHOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SensorService service = retrofit.create(SensorService.class);
        final Call<SensorInfoRepo> repos = service.getStateSearchSensorList(Module.getRecordId(getApplicationContext()),ACTIVITYNAME,strSensorId);

        repos.enqueue(new Callback<SensorInfoRepo>(){
            @Override
            public void onResponse(Call<SensorInfoRepo> call, Response<SensorInfoRepo> response) {

                SensorInfoRepo sensorInfoRepo = response.body();

                if(sensorInfoRepo != null) {
                    mListHashWm.clear();
                    adapter = new WmListAdapter(getApplicationContext());

                    for(int i = 0; i < sensorInfoRepo.getSensorList().size(); i ++ ) {

                        HashMap<String, String> hashTemp = new HashMap<>();

                        String addressInfo = sensorInfoRepo.getSensorList().get(i).getLocationName();
                        String sensorId = sensorInfoRepo.getSensorList().get(i).getManageId();

                        hashTemp.put("addressInfo", addressInfo);
                        hashTemp.put("sensorId", sensorId);

                        mListHashWm.add(i, hashTemp);

                        adapter.addItem(new WmListItem(addressInfo, sensorId));
                    }
                    wmListView.setAdapter(adapter);
                    dialog.dismiss();
                } else {
                    Toast.makeText(WmListActivity.this, sensorInfoRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<SensorInfoRepo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
//        StringBuilder sb = new StringBuilder(TM_INFO_URL);
//        sb.append("?sensorId=");
//        sb.append(sensorId);
//
//        StringRequest pushHistoryRequest = new StringRequest(sb.toString(), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String string) {
//                parseJsonData(string);
//
//                sensorIdTv.setText(strSensorId);
//                locationTv.setText(strLocation);
//                installDayTv.setText(installDay);
//                waterLevelSensorInfoTv.setText(waterLevel);
//                waterQualitySensorInfoTv.setText(waterQuality);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
//                Log.i("volley error : ",volleyError.toString());
//                dialog.dismiss();
//            }
//        });
//
//        RequestQueue rQueue = Volley.newRequestQueue(WmInfoActivity.this);
//        rQueue.add(pushHistoryRequest);
    }

    //통신 후 json 파싱
    void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);

            strSensorId = object.getString("sensorId");
            strLocation = object.getString("addressInfo");
            installDay = object.getString("installDay");
            waterLevel = object.getString("waterLevel");
            waterQuality = object.getString("waterQuality");

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
