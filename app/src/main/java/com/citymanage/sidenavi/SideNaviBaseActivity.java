package com.citymanage.sidenavi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.citymanage.BaseActivity;
import com.citymanage.member.LoginActivity;
import com.citymanage.R;
import com.citymanage.setting.SettingActivity;
import com.citymanage.favorite.FavoriteActivity;
import com.citymanage.gm.GmListActivity;
import com.citymanage.push.PushHistoryActivity;
import com.citymanage.sm.SmListActivity;
import com.citymanage.tm.TmListActivity;
import com.citymanage.wm.WmListActivity;

/**
 * Created by we25 on 2017-07-11.
 */

public abstract class SideNaviBaseActivity extends BaseActivity {

    /* 프로그램 실행 후 필요한 상수 값들 정의 끝 */

    public ProgressDialog dialog; //프로그레스바 다이얼로그
    int ResultCode; //response 응답코드 변수
    String url = ""; //요청 cityUrl 셋팅 변수

    protected static final int NAV_DRAWER_ITEM_INVALID = -1;

    private DrawerLayout drawerLayout;
    private Toolbar actionBarToolbar;

    ImageView profilShot;

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
    public void goToNavDrawerItem(int item) {
        switch (item) {
//            case R.id.nav_home:
//                startActivity(new Intent(this, MainActivity.class));
//                finish();
//                break;
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
            case R.id.nav_wm:
                startActivity(new Intent(this, WmListActivity.class));
                finish();
                break;
            case R.id.nav_tm:
                startActivity(new Intent(this, TmListActivity.class));
                finish();
                break;
            case R.id.nav_gm:
                startActivity(new Intent(this, GmListActivity.class));
                finish();
                break;
            case R.id.nav_sm:
                startActivity(new Intent(this, SmListActivity.class));
                finish();
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("logout","logout");
                startActivity(intent);
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

        /* 사이드 네비게이션바 프로필 이미지 셋팅*/
        profilShot = (ImageView) findViewById(R.id.profilShot);

        if(profilShot ==  null) {
            Log.i("null 입니다","null 입니다.");
        } else {
            profilShot.setImageResource(R.drawable.bearbang);
            profilShot.setBackground(new ShapeDrawable(new OvalShape()));
            profilShot.setClipToOutline(true);
        }
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
