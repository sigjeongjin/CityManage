package com.citymanage.wm;

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
import com.citymanage.favorite.repo.FavoritesInfoRepo;
import com.citymanage.favorite.repo.FavoritesService;
import com.citymanage.sidenavi.SideNaviBaseActivity;
import com.citymanage.wm.repo.WmInfoRepo;
import com.common.Module;
import com.common.repo.SensorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.citymanage.R.drawable.btn_favorite_ov;
import static com.citymanage.R.drawable.btn_favorite_v;
import static com.citymanage.R.id.action_settings;

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

    Menu menu; // 초기 즐겨찾기 아이콘을 셋팅 하기 위해 저장하는 변수
    String menuIconClickState = "Y"; //즐겨찾기 여부 저장 변수

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
        final Call<WmInfoRepo> repos = service.getWmInfo(sensorId, Module.getRecordId(getApplicationContext()));

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

                    MenuItem favoritesIcon = menu.findItem(R.id.action_settings);

                    if(wmInfoRepo.getBookmark().equals("Y")) {
                        favoritesIcon.setIcon(btn_favorite_ov);
                        menuIconClickState = "Y";
                    } else {
                        favoritesIcon.setIcon(btn_favorite_v);
                        menuIconClickState = "N";
                    }

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
        this.menu = menu;
        getMenuInflater().inflate(R.menu.favorite_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case action_settings :
                final MenuItem menuItem = item;

                //즐겨찾기 해제 로직
                if(menuIconClickState.equals("Y")) {
                    DialogInterface.OnClickListener favoritesConfirm = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(BASEHOST)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            String memberId = Module.getRecordId(getApplicationContext());
                            String manageId = sensorIdTv.getText().toString();

                            FavoritesService service = retrofit.create(FavoritesService.class);
                            final Call<FavoritesInfoRepo> repos = service.setFavoritesRelease(memberId, manageId);

                            repos.enqueue(new Callback<FavoritesInfoRepo>() {
                                @Override
                                public void onResponse(Call<FavoritesInfoRepo> call, Response<FavoritesInfoRepo> response) {
                                    FavoritesInfoRepo favoritesInfoRepo = response.body();

                                    if (response.isSuccessful()) {
                                        if (favoritesInfoRepo.getResultCode().equals("200")) {
                                            Toast.makeText(getApplicationContext(), favoritesInfoRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                                            menuItem.setIcon(btn_favorite_v);
                                            menuIconClickState = "N";
                                        } else if (favoritesInfoRepo.getResultCode().equals("400")) {
                                            Toast.makeText(getApplicationContext(), favoritesInfoRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<FavoritesInfoRepo> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                                    Log.e("TmInfoActivity DEBUG : ", t.getMessage());
                                }
                            });
                        }
                    };

                    DialogInterface.OnClickListener favoritesCancel = new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    };

                    new AlertDialog.Builder(WmInfoActivity.this)
                            .setTitle("즐겨찾기를 해제 하시겠습니까?")
                            .setPositiveButton("확인",favoritesConfirm)
                            .setNegativeButton("취소",favoritesCancel)
                            .show();
                } else { // 즐겨 찾기 설정 로직

                    DialogInterface.OnClickListener favoritesConfirm = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(BASEHOST)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            String memberId = Module.getRecordId(getApplicationContext());
                            String manageId = sensorIdTv.getText().toString();

                            FavoritesService service = retrofit.create(FavoritesService.class);
                            final Call<FavoritesInfoRepo> repos = service.setFavoritesRegister(memberId, manageId);
                            Log.e("fv register DEBUG : ", "즐겨찾기등록");
                            repos.enqueue(new Callback<FavoritesInfoRepo>() {
                                @Override
                                public void onResponse(Call<FavoritesInfoRepo> call, Response<FavoritesInfoRepo> response) {
                                    FavoritesInfoRepo favoritesInfoRepo = response.body();

                                    if (response.isSuccessful()) {
                                        if (favoritesInfoRepo.getResultCode().equals("200")) {
                                            menuItem.setIcon(btn_favorite_ov);
                                            Toast.makeText(getApplicationContext(), favoritesInfoRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                                            menuIconClickState = "Y";
                                        } else if (favoritesInfoRepo.getResultCode().equals("400")) {
                                            Toast.makeText(getApplicationContext(), favoritesInfoRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<FavoritesInfoRepo> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                                    Log.e("TmInfoActivity DEBUG : ", t.getMessage());
                                }
                            });
                        }
                    };

                    DialogInterface.OnClickListener favoritesCancel = new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    };

                    new AlertDialog.Builder(WmInfoActivity.this)
                            .setTitle("즐겨찾기로 설정 하시겠습니까?")
                            .setPositiveButton("확인",favoritesConfirm)
                            .setNegativeButton("취소",favoritesCancel)
                            .show();
                }
                break;
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