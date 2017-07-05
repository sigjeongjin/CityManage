package com.citymanage;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * Created by we25 on 2017-07-05.
 */

public class BaseActivity2 extends AppCompatActivity {
    protected DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.base_layout);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //This is about creating custom listview for navigate drawer
        //Implementation for NavigateDrawer HERE !

    }
}
