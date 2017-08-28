package com.citymanage.gm;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.citymanage.R.id.action_settings;

public class GmListActivity extends SideNaviBaseActivity {

    final static String ACTIVITYNAME = "GM";
    final static String SENSORID = "sensorId";

    String resultCode;

    GmListAdapter adapter; // 위의 리스트 adapter
    ListView gmListView;
    EditText streetFindEv;
    Button searchBtn;

    List<HashMap<String,String>> mListHashGm = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gm_list);
        super.setupToolbar();
        setTitle(R.string.gm_title);


        gmListView = (ListView) findViewById(R.id.gmLv);
        streetFindEv = (EditText) findViewById(R.id.streetFindEv);
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
                    mListHashGm.clear();
                    adapter = new GmListAdapter(getApplicationContext());

                    for(int i = 0; i < sensorInfoRepo.getSensorList().size(); i ++ ) {

                        HashMap<String, String> hashTemp = new HashMap<>();

                        String addressInfo = sensorInfoRepo.getSensorList().get(i).getLocationName();
                        String sensorId = sensorInfoRepo.getSensorList().get(i).getManageId();

                        hashTemp.put("addressInfo", addressInfo);
                        hashTemp.put("sensorId", sensorId);

                        mListHashGm.add(i, hashTemp);

                        adapter.addItem(new GmListItem(addressInfo, sensorId));
                    }
                    gmListView.setAdapter(adapter);
                    dialog.dismiss();
                } else {
                    Toast.makeText(GmListActivity.this, sensorInfoRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
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

                dialog = new ProgressDialog(GmListActivity.this);
                dialog.setMessage("Loading....");
                dialog.show();

//                StringBuilder sb = new StringBuilder(TM_LIST_URL);
//                String strStreet = streetFindEv.getText().toString();
//
//                try {
//                    if(strStreet.length() > 0) {
//                        sb.append("?find=");
//                        sb.append(URLEncoder.encode(streetFindEv.getText().toString(),"UTF-8"));
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                StringRequest pushHistoryRequest = new StringRequest(sb.toString(), new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String string) {
//                        parseJsonData(string);
//                        adapter = new GmListAdapter(getApplicationContext());
//
//                        for(int i = 0; i < mListHashGm.size(); i ++ ) {
//                            adapter.addItem(new GmListItem(mListHashGm.get(i).get("addressInfo"),
//                                    mListHashGm.get(i).get("sensorId")));
//                        }
//                        gmListView.setAdapter(adapter);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
//                        Log.i("volley error : ",volleyError.toString());
//                        dialog.dismiss();
//                    }
//                });
//
//                RequestQueue rQueue = Volley.newRequestQueue(GmListActivity.this);
//                rQueue.add(pushHistoryRequest);
            }
        });

        gmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getApplicationContext(), GmInfoActivity.class);
            intent.putExtra(SENSORID,mListHashGm.get(position).get(SENSORID));
            startActivity(intent);

            }
        });
    }

    //통신 후 json 파싱
    void parseJsonData(String jsonString) {
        try {
            mListHashGm.clear();

            JSONObject object = new JSONObject(jsonString);

            JSONArray tmListArray = object.getJSONArray("tmList");

            for(int i = 0; i < tmListArray.length(); i ++ ) {

                HashMap<String,String> hashTemp = new HashMap<>();

                String addressInfo = tmListArray.getJSONObject(i).getString("addressInfo");
                String sensorId = tmListArray.getJSONObject(i).getString("sensorId");

                hashTemp.put("addressInfo",addressInfo);
                hashTemp.put("sensorId",sensorId);

                mListHashGm.add(i,hashTemp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
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
                Intent intent = new Intent(getApplicationContext(), GmMapActivity.class);
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
