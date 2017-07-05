package com.citymanage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by we25 on 2017-07-04.
 */

public class Setting extends Fragment {

    Switch gAutoLoginOnOffSw;
    Button gPwdChangeBtn;
    ImageView gProfileChangeIv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);

        gAutoLoginOnOffSw = (Switch) rootView.findViewById(R.id.autoLoginOnOffSwitch);
        gPwdChangeBtn = (Button) rootView.findViewById(R.id.pwdChangeBtn);
        gProfileChangeIv = (ImageView) rootView.findViewById(R.id.profileChangeIv);

        gAutoLoginOnOffSw.setChecked((0 == getAutoLogin()) ?  false : true);

        gProfileChangeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((SettingActivity)getActivity()).selectAlbum();
                    }
                };

                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                new AlertDialog.Builder(getContext())
                        .setTitle("업로드할 이미지 선택")
                        .setPositiveButton("앨범선택",albumListener)
                        .setNegativeButton("취소",cancelListener)
                        .show();
            }
        });

        gAutoLoginOnOffSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(getAutoLogin() == 0) {
                    setAutoLogin(1);
                    buttonView.setChecked(true);
                } else {
                    setAutoLogin(0);
                    buttonView.setChecked(false);
                }
            }
        });

        return rootView;
    }

    // 값 불러오기
    private int getAutoLogin(){
        SharedPreferences pref = ((SettingActivity)getActivity()).getSharedPreferences("pref", MODE_PRIVATE);
        return Integer.parseInt(pref.getString("autoLogin", "0"));
    }

    // 값 저장하기
    private void setAutoLogin(int pAutoLogin){
        SharedPreferences pref = ((SettingActivity)getActivity()).getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("autoLogin", String.valueOf(pAutoLogin));
        editor.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("FRAGMENT RESULT","FRAGMENT RESULT");
        super.onActivityResult(requestCode, resultCode, data);
    }
}
