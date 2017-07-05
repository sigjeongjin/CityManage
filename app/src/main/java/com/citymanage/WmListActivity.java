package com.citymanage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WmListActivity extends AppCompatActivity {
    EditText addressInput;
    TextView textView;

    Button wmMapActivityGo;
    Button searchBtn;

    ListView listView;
    WmAdapter adapter;

    public static final String BASE_URL = "http://192.168.0.229:3000/";

    String resultCode;
    String url;// = "http://192.168.0.229:3000/wmList";
    ProgressDialog dialog;

    AddressSearchActivity ad = new AddressSearchActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wm_list);

        textView = (TextView) findViewById(R.id.textView);
        adapter = new WmAdapter(this);
        listView = (ListView) findViewById(R.id.listView);

        WmService.getRetrofit(getApplicationContext()).wm("sensorId", "city", "state", "street", "waterQuality", "waterLevel").enqueue(new Callback<WmData>() {
            @Override
            public void onResponse(Call<WmData> call, Response<WmData> response) {
                Log.d("sensorId", response.body().sensorId);
                Log.d("city", response.body().city);
                Log.d("state", response.body().state);
                Log.d("street", response.body().street);
                Log.d("waterQuality", response.body().waterQuality);
                Log.d("WmData", "가져오기 성공");
            }
            @Override
            public void onFailure(Call<WmData> call, Throwable t) {
                Log.d("WmData", "가져오기 실패");
            }
        });

    }
}