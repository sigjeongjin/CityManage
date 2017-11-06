package com.citymanage.tm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.citymanage.R;
import com.citymanage.sidenavi.SideNaviBaseActivity;
import com.common.Module;
import com.common.repo.SensorInfoRepo;
import com.common.repo.SensorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.citymanage.R.id.action_settings;

public class TmListActivity extends SideNaviBaseActivity {

    final static String SENSORID = "sensorId";
    final static String ACTIVITYNAME ="tm";

    String resultCode;

    TmListAdapter adapter; // 위의 리스트 adapter
    ListView tmListView;
    EditText sensorIdFindEt;
    Button searchBtn;

    List<HashMap<String,String>> mListHashTm = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tm_list);
        super.setupToolbar();
        setTitle(R.string.tm_title);

        tmListView = (ListView) findViewById(R.id.tmLv);
        sensorIdFindEt = (EditText) findViewById(R.id.sensorIdFindEv);
        searchBtn = (Button) findViewById(R.id.searchBtn);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEHOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SensorService service = retrofit.create(SensorService.class);
        final Call<SensorInfoRepo> repos = service.getSensorList(Module.getRecordId(getApplicationContext()),ACTIVITYNAME);

        repos.enqueue(new Callback<SensorInfoRepo>(){
            @Override
            public void onResponse(Call<SensorInfoRepo> call, Response<SensorInfoRepo> response) {

                SensorInfoRepo sensorInfoRepo = response.body();

                if(sensorInfoRepo != null) {
                    mListHashTm.clear();
                    adapter = new TmListAdapter(getApplicationContext());

                    for(int i = 0; i < sensorInfoRepo.getSensorList().size(); i ++ ) {

                        HashMap<String, String> hashTemp = new HashMap<>();

                        String addressInfo = sensorInfoRepo.getSensorList().get(i).getLocationName();
                        String sensorId = sensorInfoRepo.getSensorList().get(i).getManageId();

                        hashTemp.put("addressInfo", addressInfo);
                        hashTemp.put("sensorId", sensorId);

                        mListHashTm.add(i, hashTemp);

                        adapter.addItem(new TmListItem(addressInfo, sensorId));
                    }
                    tmListView.setAdapter(adapter);
                    dialog.dismiss();
                } else {
                    Toast.makeText(TmListActivity.this, sensorInfoRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<SensorInfoRepo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            dialog = new ProgressDialog(TmListActivity.this);
            dialog.setMessage("Loading....");
            dialog.show();

            String strSensorId = sensorIdFindEt.getText().toString();

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
                        mListHashTm.clear();
                        adapter = new TmListAdapter(getApplicationContext());

                        for(int i = 0; i < sensorInfoRepo.getSensorList().size(); i ++ ) {

                            HashMap<String, String> hashTemp = new HashMap<>();

                            String addressInfo = sensorInfoRepo.getSensorList().get(i).getLocationName();
                            String sensorId = sensorInfoRepo.getSensorList().get(i).getManageId();

                            hashTemp.put("addressInfo", addressInfo);
                            hashTemp.put("sensorId", sensorId);

                            mListHashTm.add(i, hashTemp);

                            adapter.addItem(new TmListItem(addressInfo, sensorId));
                        }
                        tmListView.setAdapter(adapter);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(TmListActivity.this, sensorInfoRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<SensorInfoRepo> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            }
        });

        tmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getApplicationContext(), TmInfoActivity.class);
            intent.putExtra(SENSORID,mListHashTm.get(position).get(SENSORID));
            startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case action_settings :
                Intent intent = new Intent(getApplicationContext(), TmMapActivity.class);
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
