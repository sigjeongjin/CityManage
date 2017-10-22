package com.citymanage.tm;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.citymanage.R;
import com.citymanage.sidenavi.SideNaviBaseActivity;
import com.citymanage.tm.repo.TmInfoRepo;
import com.common.Module;
import com.common.repo.SensorInfoRepo;
import com.common.repo.SensorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.citymanage.R.id.action_settings;

public class TmInfoActivity extends SideNaviBaseActivity {

    final static String SENSORID = "sensorId";

    ProgressDialog dialog;

    TextView sensorIdTv;
    TextView locationTv;
    TextView installDayTv;
    TextView fireSensorInfoTv;
    TextView stinkSensorInfoTv;
    TextView generousTv;
    TextView lockStatusTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tm_info);
        super.setupToolbar();
        setTitle(R.string.tm_title);

        sensorIdTv          = (TextView) findViewById(R.id.sensorIdTv);
        locationTv          = (TextView) findViewById(R.id.locationTv);
        installDayTv        = (TextView) findViewById(R.id.installDayTv);
        fireSensorInfoTv   = (TextView) findViewById(R.id.fireSensorInfoTv);
        stinkSensorInfoTv  = (TextView) findViewById(R.id.stinkSensorInfoTv);
        generousTv          = (TextView) findViewById(R.id.generousTv);
        lockStatusTv        = (TextView) findViewById(R.id.lockStatusTv);


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
                    fireSensorInfoTv.setText(tmInfoRepo.getFlameDetection());
                    stinkSensorInfoTv.setText(tmInfoRepo.getStink());
                    generousTv.setText(tmInfoRepo.getGenerous());
                    lockStatusTv.setText(tmInfoRepo.getLockStatus());

                    dialog.dismiss();
                } else {
                    Toast.makeText(TmInfoActivity.this, tmInfoRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<TmInfoRepo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "네트워크 연결을 확인해 주세요.", Toast.LENGTH_SHORT).show();
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
            case action_settings :

                final MenuItem menuItem = item;

                DialogInterface.OnClickListener favoritesConfirm = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASEHOST)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        String memberId = Module.getRecordId(getApplicationContext());
                        String manageId = sensorIdTv.getText().toString();

                        SensorService service = retrofit.create(SensorService.class);
                        final Call<SensorInfoRepo> repos = service.getFavoritesWhether(memberId, manageId);

                        repos.enqueue(new Callback<SensorInfoRepo>() {
                            @Override
                            public void onResponse(Call<SensorInfoRepo> call, Response<SensorInfoRepo> response) {
                                SensorInfoRepo sensorInfoRepo = response.body();

                                if (response.isSuccessful()) {
                                    if (sensorInfoRepo.getResultCode().equals("200")) {


                                    } else if (sensorInfoRepo.getResultCode().equals("400")) {

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<SensorInfoRepo> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                                Log.e("TmInfoActivity DEBUG : ", t.getMessage());
                            }
                        });

                        menuItem.setIcon(R.drawable.btn_favorite_ov);
                    }
                };

                DialogInterface.OnClickListener favoritesCancel = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        menuItem.setIcon(R.drawable.btn_favorite_v);
                    }
                };

                new AlertDialog.Builder(TmInfoActivity.this)
                        .setTitle("즐겨찾기로 설정하시겠습니까?")
                        .setPositiveButton("확인",favoritesConfirm)
                        .setNegativeButton("취소",favoritesCancel)
                        .show();

                break;
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean providesActivityToolbar() {return true;}
}
