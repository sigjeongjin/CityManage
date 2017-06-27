package com.citymanage;

import android.support.v7.app.AppCompatActivity;


public class WmMapActivity extends AppCompatActivity  {

//    private static final String TAG = "SmMapActivity";
//    SupportMapFragment mapFragment;
//    GoogleMap map;
//
//    Button wmListActivityGo;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wm_map);
//
//        wmListActivityGo = (Button) findViewById(R.id.wmListActivityGo);
//
//        wmListActivityGo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), WmListActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        mapFragment = (SupportMapFragment) getSupportFragmentManager().
//                       findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);  // 메인 쓰레드에서 호출되어야 메인스레드에서 콜백이 실행
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        Log.d(TAG, "GoogleMap is ready.");
//        map = googleMap;
//
//        // sensor 위치 변수값
//        final LatLng Sensor = new LatLng(37.573974, 127.023666);
//
//        // marker 표시
//        // market 의 위치, 타이틀, 짧은설명 추가 가능.
//        MarkerOptions marker = new MarkerOptions();
//        marker.position(Sensor); // sensor 위치 설정
//        marker.title("Sensor");   // maker title
//        //marker.snippet("수질");
//        map.addMarker(marker);   // maker click 시 보여줌
//        //map.addMarker(marker).showInfoWindow(); 화면에 바로 보여줌
//
//        //movecamera 좌표를 설정한 곳으로 이동
//        //Google 지도는 zoom은 1~23까지 가능
//        map.moveCamera(CameraUpdateFactory.newLatLng(Sensor));
//        map.animateCamera(CameraUpdateFactory.zoomTo(10));
//
////        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
////        googleMap.animateCamera(zoom);
//
//        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                // 마커 클릭시 호출되는 콜백 메서드
//                Toast.makeText(getApplicationContext(),
//                        marker.getTitle() + " 클릭했음"
//                        , Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), WmInfoActivity.class);
//                startActivity(intent);
//                return false;
//            }
//        });
//
//    }
}
