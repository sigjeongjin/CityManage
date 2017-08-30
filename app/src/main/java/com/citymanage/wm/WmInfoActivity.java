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
import com.citymanage.wm.repo.WmInfoRepo;
import com.common.repo.SensorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        final Call<WmInfoRepo> repos = service.getWmInfo(sensorId);

        repos.enqueue(new Callback<WmInfoRepo>(){
            @Override
            public void onResponse(Call<WmInfoRepo> call, Response<WmInfoRepo> response) {

                WmInfoRepo wmInfoRepo = response.body();

                if(wmInfoRepo != null) {
                    sensorIdTv.setText(wmInfoRepo.getManageId());
                    locationTv.setText(wmInfoRepo.getLocationName());
                    installDayTv.setText(wmInfoRepo.getInstallationDateTime());
                    waterLevelSensorInfoTv.setText(wmInfoRepo.getWaterLevel());
                    waterQualitySensorInfoTv.setText(wmInfoRepo.getWaterQuality());

                    dialog.dismiss();
                } else {
                    Toast.makeText(WmInfoActivity.this, wmInfoRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<WmInfoRepo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
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