package com.citymanage.member;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.citymanage.R;
import com.citymanage.setting.SettingActivity;

/**
 * Created by we25 on 2017-07-04.
 */

public class PasswordChangeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_password_change, container, false);

        EditText idEv = (EditText) rootView.findViewById(R.id.idEv);
        final EditText pwdEv = (EditText) rootView.findViewById(R.id.pwdEv);

        Button settingGoBtn = (Button) rootView.findViewById(R.id.settingGoBtn);
        settingGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener confirmListener = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(pwdEv.getWindowToken(), 0);

                        SettingActivity activity = (SettingActivity) getActivity();
                        activity.onFragmentChanged(4);
                    }
                };

                new AlertDialog.Builder(getContext())
                        .setTitle("비밀번호 변경 완료")
                        .setPositiveButton("확인",confirmListener)
                        .show();
            }
        });

        return rootView;
    }
}
