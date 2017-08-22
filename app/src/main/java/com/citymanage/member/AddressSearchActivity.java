package com.citymanage.member;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.citymanage.MainActivity;
import com.citymanage.R;
import com.citymanage.member.repo.MemberRepo;
import com.citymanage.member.repo.MemberService;
import com.citymanage.sidenavi.SideNaviBaseActivity;
import com.common.Module;

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


public class AddressSearchActivity extends SideNaviBaseActivity {


    List<HashMap<String, String>> cityList = new ArrayList<HashMap<String, String>>();
    List<String> cityNameList = new ArrayList<String>();
    String [] strArrayCityName;

    List<HashMap<String, String>> stateList = new ArrayList<HashMap<String, String>>();
    List<String> stateNameList = new ArrayList<String>();
    String [] strArrayStateName;

    Spinner citySp;
    Spinner stateSp;

    Button selectConfirmBtn;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_search);

        citySp = (Spinner) findViewById(R.id.citySp);
        stateSp = (Spinner) findViewById(R.id.stateSp);
        selectConfirmBtn = (Button) findViewById(R.id.selectConfirmBtn);

        dialog = new ProgressDialog(AddressSearchActivity.this);
        dialog.setMessage("Loading....");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEHOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MemberService service = retrofit.create(MemberService.class);
        final Call<MemberRepo> repos = service.getCityInfo();

        repos.enqueue(new Callback<MemberRepo>(){
            @Override
            public void onResponse(Call<MemberRepo> call, Response<MemberRepo> response) {

                setCityAdapter();

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<MemberRepo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        citySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dialog = new ProgressDialog(AddressSearchActivity.this);
                dialog.setMessage("Loading....");
                dialog.show();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASEHOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                MemberService service = retrofit.create(MemberService.class);
                final Call<MemberRepo> repos = service.getCityInfo();

                repos.enqueue(new Callback<MemberRepo>() {
                    @Override
                    public void onResponse(Call<MemberRepo> call, Response<MemberRepo> response) {
                        dialog.dismiss();

                        MemberRepo memberRepo= response.body();

                        Log.e("TEST TEST :", memberRepo.getResultMessage());
                        Log.e("TEST TEST :", memberRepo.getResultCode());

                        if(memberRepo != null) {
                            setStateAdapater();
                        }
                    }

                    @Override
                    public void onFailure(Call<MemberRepo> call, Throwable t) {

                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener confirmListener = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Module.setLocation(getApplicationContext(),1);

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                };

                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                new AlertDialog.Builder(AddressSearchActivity.this)
                        .setTitle("지역선택 완료")
                        .setPositiveButton("확인",confirmListener)
                        .setNegativeButton("취소",cancelListener)
                        .show();
            }
        });
    }

    void setCityAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, strArrayCityName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySp.setAdapter(adapter);
    }

    void setStateAdapater() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, strArrayStateName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSp.setAdapter(adapter);
    }

    void parseCityJsonData(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray jsonArray = jsonObject.getJSONArray("address");
            strArrayCityName = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++){

                String city = jsonArray.getJSONObject(i).getString("city");
                String cityCode = jsonArray.getJSONObject(i).getString("cityCode");

                HashMap<String,String> cityInfo = new HashMap<>();
                cityInfo.put("city", city);
                cityInfo.put("cityCode", cityCode);

                strArrayCityName[i] = city;
                cityNameList.add(i,city);
                cityList.add(i, cityInfo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }

    void parseStateJsonData(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray jsonArray = jsonObject.getJSONArray("address");
            strArrayStateName = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++){

                String state = jsonArray.getJSONObject(i).getString("state");
                String stateCode = jsonArray.getJSONObject(i).getString("stateCode");

                HashMap<String,String> stateInfo = new HashMap<>();
                stateInfo.put("state", state);
                stateInfo.put("stateCode", stateCode);

                strArrayStateName[i] = state;
                stateNameList.add(i,state);
                stateList.add(i, stateInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
    protected int getSelfNavDrawerItem() {
        return R.id.nav_favorite;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }
}


