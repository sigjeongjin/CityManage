package com.citymanage.tm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.citymanage.R;
import com.citymanage.sidenavi.SideNaviBaseActivity;
import com.citymanage.tm.repo.TmInfoRepo;
import com.citymanage.wm.repo.WmInfoRepo;
import com.common.repo.SensorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.citymanage.R.id.waterLevelSensorInfoTv;
import static com.citymanage.R.id.waterQualitySensorInfoTv;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEHOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SensorService service = retrofit.create(SensorService.class);
        final Call<TmInfoRepo> repos = service.getTmInfo(sensorId);

        repos.enqueue(new Callback<TmInfoRepo>(){
            @Override
            public void onResponse(Call<TmInfoRepo> call, Response<TmInfoRepo> response) {

                TmInfoRepo tmInfoRepo = response.body();

                if(tmInfoRepo != null) {
                    sensorIdTv.setText(tmInfoRepo.getManageId());
                    locationTv.setText(tmInfoRepo.getLocationName());
                    installDayTv.setText(tmInfoRepo.getInstallationDateTime());
                    waterLevelSensorInfoTv.setText(tmInfoRepo.getWaterLevel());
                    waterQualitySensorInfoTv.setText(tmInfoRepo.getWaterQuality());

                    dialog.dismiss();
                } else {
                    Toast.makeText(TmInfoActivity.this, wmInfoRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<TmInfoRepo> call, Throwable t) {
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
