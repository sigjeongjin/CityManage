package com.citymanage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by we25 on 2017-07-11.
 */

public abstract class SideNaviBaseActivity extends AppCompatActivity {

    /* 프로그램 실행 후 필요한 상수 값들 정의 시작 */
    private static final int CANCLE_FROM_CONTENT = 0;
    private static final int PICK_FROM_CAMERA = 1; //카메라 촬영으로 사진 가져오기
    private static final int PICK_FROM_ALBUM = 2; //앨범에서 사진 가져오기
    private static final int CROP_FROM_CAMERA = 3; //가져온 사진을 자르기 위한 변수
    private static final int autoLoginTrue = 1;
    private static final int autoLoginFalse = 0;
    private static final int ALBUM_WIDTH = 400;
    private static final int ALBUM_HEIGHT = 300;
    public static final String HOST = "http://192.168.0.230:3000";
    public static final String LOGIN = "http://192.168.0.230:3000/login";
    public static final String REGISTER = "http://192.168.0.230:3000/register";
    public static final String PUSH_HISTORY_HOST = "http://192.168.0.230:3000/pushHistory";
    public static final String TM_LIST_URL = "http://192.168.0.230:3000/tmList";
    public static final String SM_LIST_URL = "http://192.168.0.230:3000/smList";
    public static final String FAVORITE_HOST = "http://192.168.0.230:3000/favoriteList"; // 즐찾 추가
    public static final String TM_INFO_URL = "http://192.168.0.230:3000/tmInfo";
    public static final String SM_INFO_URL = "http://192.168.0.230:3000/smInfo";
    public static final String CITYURL = "http://192.168.0.230:3000/cityList";
    public static final String SATATEURL = "http://192.168.0.230:3000/stateList";

    /* 프로그램 실행 후 필요한 상수 값들 정의 끝 */

    public ProgressDialog dialog; //프로그레스바 다이얼로그
    int ResultCode; //response 응답코드 변수
    String url = ""; //요청 cityUrl 셋팅 변수

    protected static final int NAV_DRAWER_ITEM_INVALID = -1;

    private DrawerLayout drawerLayout;
    private Toolbar actionBarToolbar;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();
    }

    /**
     * Sets up the navigation drawer.
     */
    private void setupNavDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout == null) {
            // current activity does not have a drawer.
            return;
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerSelectListener(navigationView);
            setSelectedItem(navigationView);
        }
    }

    /**
     * Updated the checked item in the navigation drawer
     * @param navigationView the navigation view
     */
    private void setSelectedItem(NavigationView navigationView) {
        // Which navigation item should be selected?
        int selectedItem = getSelfNavDrawerItem(); // subclass has to override this method
        navigationView.setCheckedItem(selectedItem);
    }

    /**
     * Creates the item click listener.
     * @param navigationView the navigation view
     */
    private void setupDrawerSelectListener(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        drawerLayout.closeDrawers();
                        onNavigationItemClicked(menuItem.getItemId());
                        return true;
                    }
                });
    }

    /**
     * Handles the navigation item click.
     * @param itemId the clicked item
     */
    private void onNavigationItemClicked(final int itemId) {
        if(itemId == getSelfNavDrawerItem()) {
            // Already selected
            closeDrawer();
            return;
        }

        goToNavDrawerItem(itemId);
    }

    /**
     * Handles the navigation item click and starts the corresponding activity.
     * @param item the selected navigation item
     */
    private void goToNavDrawerItem(int item) {
        switch (item) {
            case R.id.nav_home:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.nav_favorite:
                startActivity(new Intent(this, FavoriteActivity.class));
                finish();
                break;
            case R.id.nav_pushHistory:
                startActivity(new Intent(this, PushHistoryActivity.class));
                finish();
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingActivity.class));
                finish();
                break;
        }
    }

    /**
     * Provides the action bar instance.
     * @return the action bar.
     */
    protected ActionBar getActionBarToolbar() {
        if (actionBarToolbar == null) {
            actionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (actionBarToolbar != null) {
                setSupportActionBar(actionBarToolbar);
            }
        }
        return getSupportActionBar();
    }


    /**
     * Returns the navigation drawer item that corresponds to this Activity. Subclasses
     * have to override this method.
     */
    protected int getSelfNavDrawerItem() {
        return NAV_DRAWER_ITEM_INVALID;
    }

    protected void openDrawer() {
        if(drawerLayout == null)
            return;

        drawerLayout.openDrawer(GravityCompat.START);
    }

    protected void closeDrawer() {
        if(drawerLayout == null)
            return;

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public abstract boolean providesActivityToolbar();

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.btn_list);
    }
}
