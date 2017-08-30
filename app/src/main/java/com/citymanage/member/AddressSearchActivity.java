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

import com.citymanage.member.repo.CityRepo;
import com.citymanage.member.repo.MemberRepo;
import com.citymanage.member.repo.MemberService;
import com.citymanage.member.repo.StateRepo;

import com.citymanage.sidenavi.SideNaviBaseActivity;
import com.common.Module;

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

        final Call<CityRepo> repos = service.getCityInfo();

        repos.enqueue(new Callback<CityRepo>(){
            @Override
            public void onResponse(Call<CityRepo> call, Response<CityRepo> response) {

                CityRepo cityInfo = response.body();

                strArrayCityName = new String[cityInfo.getCity().size()];
                for (int i = 0; i < cityInfo.getCity().size(); i++){

                    String city = cityInfo.getCity().get(i).getCityName();
                    String cityCode = cityInfo.getCity().get(i).getCityCode();

                    HashMap<String,String> cityHashMap = new HashMap<>();
                    cityHashMap.put("city", city);
                    cityHashMap.put("cityCode", cityCode);

                    strArrayCityName[i] = city;
                    cityNameList.add(i,city);
                    cityList.add(i, cityHashMap);
                }

                setCityAdapter();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<CityRepo> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        citySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dialog = new ProgressDialog(AddressSearchActivity.this);
                dialog.setMessage("Loading....");
                dialog.show();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASEHOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                MemberService service = retrofit.create(MemberService.class);

                final Call<StateRepo> repos = service.getStateInfo(cityList.get(position).get("city"));

                repos.enqueue(new Callback<StateRepo>() {
                    @Override
                    public void onResponse(Call<StateRepo> call, Response<StateRepo> response) {

                        StateRepo stateInfo = response.body();

                        strArrayStateName = new String[stateInfo.getState().size()];
                        for (int i = 0; i < stateInfo.getState().size(); i++) {
                            String state = stateInfo.getState().get(i).getStateName();
                            String stateCode = stateInfo.getState().get(i).getStateCode();

                            HashMap<String, String> stateHashMap = new HashMap<>();
                            stateHashMap.put("state", state);
                            stateHashMap.put("stateCode", stateCode);

                            strArrayStateName[i] = state;
                            stateNameList.add(i, state);
                            stateList.add(i, stateHashMap);
                        }
                        setStateAdapater();
                        dialog.dismiss();

                    }


                    @Override
                    public void onFailure(Call<StateRepo> call, Throwable t) {


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
                    public void onClick(final DialogInterface dialog, int which) {
                        Module.setLocation(getApplicationContext(), 1);


                        String cityCode = cityList.get(citySp.getSelectedItemPosition()).get("cityCode");
                        String stateCode = stateList.get(stateSp.getSelectedItemPosition()).get("stateCode");

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASEHOST)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();


                        MemberService service = retrofit.create(MemberService.class);
                        final Call<MemberRepo> cityStateInfo = service.getCityStateInfoRegister(cityCode, stateCode, Module.getRecordId(getApplicationContext()),Module.getRecordPwd(getApplicationContext()));

                        cityStateInfo.enqueue(new Callback<MemberRepo>() {

                            public void onResponse(Call<MemberRepo> call, Response<MemberRepo> response) {

                                MemberRepo cityStateInfo = response.body();


                                setStateAdapater();
                                dialog.dismiss();

                            }

                            @Override
                            public void onFailure(Call<MemberRepo> call, Throwable t) {

                            }

                        });
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


