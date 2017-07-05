package com.citymanage;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class SettingActivity extends AppCompatActivity{

    private Uri mImageCaptureUri;
    private ImageView iv_receipt;

    Uri photoUri;

    SettingFragment settingFragment;
    PasswordConfirmFragment passwordConfirmFragment;
    PasswordChangeFragment passwordChangeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        settingFragment = (SettingFragment) getSupportFragmentManager().findFragmentById(R.id.settingFragment);
        passwordConfirmFragment = new PasswordConfirmFragment();
        passwordChangeFragment = new PasswordChangeFragment();
    }

    public void onFragmentChanged(int index) {
        if (index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, settingFragment, "setting").commit();
        } else if (index == 1) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tr =  fm.beginTransaction();
            tr.replace(R.id.container, passwordConfirmFragment,"confirm");
            tr.addToBackStack(null);
            tr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            tr.commit();
        } else if (index ==2) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tr =  fm.beginTransaction();
            tr.replace(R.id.container, passwordChangeFragment,"change");
            tr.addToBackStack(null);
            tr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            tr.commit();
        } else if(index == 4) {
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

}
