package com.citymanage.tm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.citymanage.R;
import com.citymanage.sidenavi.SideNaviBaseActivity;
import com.common.Module;
import com.common.repo.SensorInfoRepo;
import com.common.repo.SensorService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.citymanage.R.id.action_settings;

public class TmMapActivity extends SideNaviBaseActivity implements OnMapReadyCallback {

    private static final String TAG = "TmMapActivity";
    SupportMapFragment mapFragment;
    GoogleMap map;

    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    HashMap<String, HashMap<String,Double>> locationList = new HashMap<String, HashMap<String,Double>>();

    List<String> manageIdList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tm_map);
        super.setupToolbar();
        setTitle(R.string.tm_title);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEHOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SensorService service = retrofit.create(SensorService.class);
        final Call<SensorInfoRepo> repos = service.getSensorMapInfoList(Module.getRecordId(getApplicationContext()), "tm");

        repos.enqueue(new Callback<SensorInfoRepo>(){
            @Override
            public void onResponse(Call<SensorInfoRepo> call, Response<SensorInfoRepo> response) {

                SensorInfoRepo sensorInfoRepo = response.body();

                if(sensorInfoRepo != null) {
                    for(int i = 0; i < sensorInfoRepo.getSensorList().size(); i ++ ) {


                        HashMap<String,Double> location = new HashMap<String, Double>();

                        location.put(LATITUDE, Double.parseDouble(sensorInfoRepo.getSensorList().get(i).getLatitude()));
                        location.put(LONGITUDE, Double.parseDouble(sensorInfoRepo.getSensorList().get(i).getLongitude()));

                        locationList.put(sensorInfoRepo.getSensorList().get(i).getManageId().toString(), location);

                        manageIdList.add(sensorInfoRepo.getSensorList().get(i).getManageId());
                    }
                }

                sersorMarker();

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<SensorInfoRepo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "맵 정보를 받아오지 못했습니다.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager().
                       findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);  // 메인 쓰레드에서 호출되어야 메인스레드에서 콜백이 실행
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // sensor 위치 변수값
        final LatLng Sensor = new LatLng(37.573974, 127.023666);
//
//        // marker 표시
//        // market 의 위치, 타이틀, 짧은설명 추가 가능.
//        MarkerOptions marker = new MarkerOptions();
//        marker.position(Sensor); // sensor 위치 설정
//        marker.title("Sensor");   // maker title
//        //marker.snippet("수질");
//        map.addMarker(marker);   // maker click 시 보여줌
//        //map.addMarker(marker).showInfoWindow(); 화면에 바로 보여줌


        //movecamera 좌표를 설정한 곳으로 이동
        //Google 지도는 zoom은 1~23까지 가능
        map.moveCamera(CameraUpdateFactory.newLatLng(Sensor));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));

//        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
//        googleMap.animateCamera(zoom);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // 마커 클릭시 호출되는 콜백 메서드
                Toast.makeText(getApplicationContext(),
                        marker.getTitle() + " 클릭했음"
                        , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), TmInfoActivity.class);
                intent.putExtra("sensorId",marker.getTitle());
                startActivity(intent);
                return false;
            }
        });

    }

    private void sersorMarker() {
        Marker aSensor;

        for(int i =0; i < manageIdList.size(); i ++ ) {

            double latitude = locationList.get(manageIdList.get(i)).get(LATITUDE);
            double longitude = locationList.get(manageIdList.get(i)).get(LONGITUDE);

            LatLng aSensorlocation = new LatLng(latitude, longitude);

            aSensor = map.addMarker(new MarkerOptions()
                    .position(aSensorlocation)
                    .title(manageIdList.get(i))
            );
            aSensor.showInfoWindow();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case action_settings :
                Intent intent = new Intent(getApplicationContext(), TmListActivity.class);
                startActivity(intent);
                finish();
                break;
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.nav_favorite;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }
}
