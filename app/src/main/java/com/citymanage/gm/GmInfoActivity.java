package com.citymanage.gm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.citymanage.R;
import com.citymanage.gm.repo.GmInfoRepo;
import com.citymanage.sidenavi.SideNaviBaseActivity;
import com.common.Module;
import com.common.repo.SensorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GmInfoActivity extends SideNaviBaseActivity {

    final static String SENSORID = "sensorId";

    ProgressDialog dialog;

    TextView sensorIdTv;
    TextView locationTv;
    TextView installDayTv;
    TextView gasDensitySensorInfoTv;
    TextView shockDetectionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gm_info);
        super.setupToolbar();
        setTitle(R.string.gm_title);

        sensorIdTv              = (TextView) findViewById(R.id.sensorIdTv);
        locationTv              = (TextView) findViewById(R.id.locationTv);
        installDayTv            = (TextView) findViewById(R.id.installDayTv);
        gasDensitySensorInfoTv  = (TextView) findViewById(R.id.gasDensitySensorInfoTv);
        shockDetectionTv       = (TextView) findViewById(R.id.shockDetectionTv);

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
        final Call<GmInfoRepo> repos = service.getGmInfo(sensorId, Module.getRecordId(getApplicationContext()));

        repos.enqueue(new Callback<GmInfoRepo>(){
            @Override
            public void onResponse(Call<GmInfoRepo> call, Response<GmInfoRepo> response) {

                GmInfoRepo gmInfoRepo = response.body();

                if(gmInfoRepo != null) {
                    sensorIdTv.setText(gmInfoRepo.getManageId());
                    locationTv.setText(gmInfoRepo.getLocationName());
                    installDayTv.setText(gmInfoRepo.getInstallationDateTime());
                    gasDensitySensorInfoTv.setText(gmInfoRepo.getGasDensity());
                    shockDetectionTv.setText(gmInfoRepo.getShockDetection());

                    dialog.dismiss();
                } else {
                    Toast.makeText(GmInfoActivity.this, gmInfoRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<GmInfoRepo> call, Throwable t) {
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
