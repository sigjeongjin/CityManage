package com.citymanage.favorite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.citymanage.R;
import com.citymanage.favorite.repo.FavoritesInfoRepo;
import com.citymanage.favorite.repo.FavoritesService;
import com.citymanage.gm.GmInfoActivity;
import com.citymanage.sidenavi.SideNaviBaseActivity;
import com.citymanage.sm.SmInfoActivity;
import com.citymanage.tm.TmInfoActivity;
import com.citymanage.wm.WmInfoActivity;
import com.common.Module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.citymanage.R.id.favoriteHistoryListView;


public class FavoriteActivity extends SideNaviBaseActivity implements View.OnClickListener {

    final static String SENSORID = "sensorId";

    //wm : 수질    tm : 쓰레기통   gm : 도시가스   sm : 금연구역
    CheckBox gWmChk, gTmChk, gGmChk, gSmChk;

    static final String WM = "wm";
    static final String TM = "tm";
    static final String GM = "gm";
    static final String SM = "sm";

    ListView gFavoriteHistoryLv; //통신 후 받은 데이터 표현할 리스트
    FavoriteAdapter adapter; // 위의 리스트 adapter

    List<HashMap<String,String>> gListFavoriteHistory = new ArrayList<HashMap<String, String>>();

    String checkBtnTagSave = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        super.setupToolbar();
        setTitle(R.string.navigation_favorite);

        /** 체크 박스 셋팅 시작(객체 생성, 체크 박스 태그 생성, 체크 박스 리스너 등록) **/
        gWmChk = (CheckBox) findViewById(R.id.wmCheckBox);
        gTmChk = (CheckBox) findViewById(R.id.tmCheckBox);
        gGmChk = (CheckBox) findViewById(R.id.gmCheckBox);
        gSmChk = (CheckBox) findViewById(R.id.smCheckBox);

        gWmChk.setTag(WM);
        gTmChk.setTag(TM);
        gGmChk.setTag(GM);
        gSmChk.setTag(SM);

        //인터페이스가 받을 수 있도록 listener 등록
        gWmChk.setOnClickListener(this);
        gTmChk.setOnClickListener(this);
        gGmChk.setOnClickListener(this);
        gSmChk.setOnClickListener(this);
        /** 체크 박스 셋팅 끝(객체 생성, 체크 박스 태그 생성, 체크 박스 리스너 등록) **/

        gWmChk.setChecked(true);

        gFavoriteHistoryLv = (ListView) findViewById(favoriteHistoryListView);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEHOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FavoritesService service = retrofit.create(FavoritesService.class);
        final Call<FavoritesInfoRepo> repos = service.getFavoritesInfo(Module.getRecordId(getApplicationContext()),"wm");

        repos.enqueue(new Callback<FavoritesInfoRepo>(){

            @Override
            public void onResponse(Call<FavoritesInfoRepo> call, Response<FavoritesInfoRepo> response) {


                FavoritesInfoRepo favoritesInfo = response.body();

                if(favoritesInfo != null) {
                    for(int i = 0; i < favoritesInfo.getFavoritesList().size(); i ++ ) {

                        HashMap<String,String> hashTemp = new HashMap<>();

                        String addressInfo = favoritesInfo.getFavoritesList().get(i).getLocationName();
                        String sensorId = favoritesInfo.getFavoritesList().get(i).getManageId();

                        hashTemp.put("addressInfo",addressInfo);
                        hashTemp.put("sensorId",sensorId);

                        gListFavoriteHistory.add(i,hashTemp);
                    }
                adapter = new FavoriteAdapter(getApplicationContext());

                for(int i = 0; i < gListFavoriteHistory.size(); i ++ ) {
                    adapter.addItem(new FavoriteItem(gListFavoriteHistory.get(i).get("addressInfo"),
                            gListFavoriteHistory.get(i).get("sensorId")));
                }
                gFavoriteHistoryLv.setAdapter(adapter);

                } else {
                    Toast.makeText(FavoriteActivity.this, favoritesInfo.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<FavoritesInfoRepo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        gFavoriteHistoryLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(checkBtnTagSave.equals("wm")) {
                    Intent intent = new Intent(getApplicationContext(), WmInfoActivity.class);
                    intent.putExtra(SENSORID,gListFavoriteHistory.get(position).get(SENSORID));
                    startActivity(intent);
                } else if(checkBtnTagSave.equals("tm")) {
                    Intent intent = new Intent(getApplicationContext(), TmInfoActivity.class);
                    intent.putExtra(SENSORID,gListFavoriteHistory.get(position).get(SENSORID));
                    startActivity(intent);
                } else if(checkBtnTagSave.equals("gm")) {
                    Intent intent = new Intent(getApplicationContext(), GmInfoActivity.class);
                    intent.putExtra(SENSORID,gListFavoriteHistory.get(position).get(SENSORID));
                    startActivity(intent);
                } else if(checkBtnTagSave.equals("sm")) {
                    Intent intent = new Intent(getApplicationContext(), SmInfoActivity.class);
                    intent.putExtra(SENSORID,gListFavoriteHistory.get(position).get(SENSORID));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        dialog = new ProgressDialog(FavoriteActivity.this);
        dialog.setMessage("Loading....");
        dialog.show();

        checkedSettingUrl(v.getTag().toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEHOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FavoritesService service = retrofit.create(FavoritesService.class);

        CheckBox temp = (CheckBox)v.findViewWithTag(v.getTag());

        checkBtnTagSave = v.getTag().toString();

        if(!temp.isChecked()){
            dialog.dismiss();
            return;
        }

        final Call<FavoritesInfoRepo> repos = service.getFavoritesInfo(Module.getRecordId(getApplicationContext()),v.getTag().toString());

        repos.enqueue(new Callback<FavoritesInfoRepo>(){
            @Override
            public void onResponse(Call<FavoritesInfoRepo> call, Response<FavoritesInfoRepo> response) {

                FavoritesInfoRepo favoritesInfo = response.body();

                gListFavoriteHistory.clear();

                //통신을 해서 데이터를 받아 오기전에 리스트뷰를 아무것도 없는 상태로 셋팅한다.
                adapter.clearItemAll(); //어댑터에 셋팅된 아이템 전부 삭제
                adapter.notifyDataSetChanged(); //어댑터 정보 갱신

                for(int i = 0; i < favoritesInfo.getFavoritesList().size(); i ++ ) {

                    HashMap<String,String> hashTemp = new HashMap<>();

                    String addressInfo = favoritesInfo.getFavoritesList().get(i).getLocationName();
                    String sensorId = favoritesInfo.getFavoritesList().get(i).getManageId();

                    hashTemp.put("addressInfo",addressInfo);
                    hashTemp.put("sensorId",sensorId);

                    gListFavoriteHistory.add(i,hashTemp);
                }

                for(int i = 0; i < gListFavoriteHistory.size(); i ++ ) {
                    adapter.addItem(new FavoriteItem(gListFavoriteHistory.get(i).get("addressInfo"),
                            gListFavoriteHistory.get(i).get("sensorId")));
                }
                gFavoriteHistoryLv.setAdapter(adapter);

                gFavoriteHistoryLv.deferNotifyDataSetChanged();

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<FavoritesInfoRepo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

    public void checkedSettingUrl(String pCheckBoxTag) {

        if(pCheckBoxTag.equals("wm")) {
            gTmChk.setChecked(false);
            gGmChk.setChecked(false);
            gSmChk.setChecked(false);

        } else if(pCheckBoxTag.equals("tm")){
            gWmChk.setChecked(false);
            gGmChk.setChecked(false);
            gSmChk.setChecked(false);

        } else if(pCheckBoxTag.equals("gm")) {
            gWmChk.setChecked(false);
            gTmChk.setChecked(false);
            gSmChk.setChecked(false);

        } else if(pCheckBoxTag.equals("sm")) {
            gWmChk.setChecked(false);
            gTmChk.setChecked(false);
            gGmChk.setChecked(false);
        }
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
    public boolean providesActivityToolbar() {
        return true;
    }
}