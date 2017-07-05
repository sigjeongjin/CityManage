package com.citymanage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by we25 on 2017-07-04.
 */

public class PasswordChangeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_password_change, container, false);

        Button settingGoBtn = (Button) rootView.findViewById(R.id.settingGoBtn);
        settingGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity activity = (SettingActivity) getActivity();
                activity.onFragmentChanged(4);
            }
        });

        return rootView;
    }
}
