package com.citymanage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by we25 on 2017-07-04.
 */

public class PasswordConfirmFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("container", container.toString());
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_password_confirm, container, false);

        Button pwdChangeGoBtn = (Button) rootView.findViewById(R.id.pwdChangeGoBtn);
        pwdChangeGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity activity = (SettingActivity) getActivity();
                activity.onFragmentChanged(2);
            }
        });

        return rootView;
    }
}
