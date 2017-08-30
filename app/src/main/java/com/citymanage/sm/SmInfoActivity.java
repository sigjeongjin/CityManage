package com.citymanage.sm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.citymanage.R;
import com.citymanage.sidenavi.SideNaviBaseActivity;
import com.citymanage.sm.repo.SmInfoRepo;
import com.common.repo.SensorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SmInfoActivity extends SideNaviBaseActivity {

    final static String SENSORID = "sensorId";

    ProgressDialog dialog;

    TextView sensorIdTv;
    TextView locationTv;
    TextView installDayTv;
    TextView fireSensorInfoTv;
    TextView smokeSensorInfoTv;


    String strSensorId;
    String strLocation;
    String installDay;
    String fireSensorInfo;
    String smokeSensorInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sm_info);
        super.setupToolbar();
        setTitle(R.string.sm_title);

        sensorIdTv          = (TextView) findViewById(R.id.sensorIdTv);
        locationTv          = (TextView) findViewById(R.id.locationTv);
        installDayTv        = (TextView) findViewById(R.id.installDayTv);
        fireSensorInfoTv    = (TextView) findViewById(R.id.fireSensorInfoTv);
        smokeSensorInfoTv   = (TextView) findViewById(R.id.smokeSensorInfoTv);


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
        final Call<SmInfoRepo> repos = service.getSmInfo(sensorId);

        repos.enqueue(new Callback<SmInfoRepo>(){
            @Override
            public void onResponse(Call<SmInfoRepo> call, Response<SmInfoRepo> response) {

                SmInfoRepo smInfoRepo = response.body();

                if(smInfoRepo != null) {
                    sensorIdTv.setText(smInfoRepo.getManageId());
                    locationTv.setText(smInfoRepo.getLocationName());
                    installDayTv.setText(smInfoRepo.getInstallationDateTime());
                    fireSensorInfoTv.setText(smInfoRepo.getFlameDetection());
                    smokeSensorInfoTv.setText(smInfoRepo.getSmokeDetection());

                    dialog.dismiss();
                } else {
                    Toast.makeText(SmInfoActivity.this, smInfoRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<SmInfoRepo> call, Throwable t) {
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
