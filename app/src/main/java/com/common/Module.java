package com.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by we25 on 2017-07-06.
 */

public class Module extends AppCompatActivity{

    static public void setRecordId (Context context,String pId) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id", String.valueOf(pId));
        editor.commit();
    }

    static public String getRecordId (Context context) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        return  pref.getString("id","");
    }

    static public void setRecordPwd (Context context,String pPwd) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("pwd", String.valueOf(pPwd));
        editor.commit();
    }

    static public String getRecordPwd (Context context) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        return  pref.getString("pwd","");
    }

        // 값 불러오기
    static public int getAutoLogin(Context context){
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        return Integer.parseInt(pref.getString("autoLogin", "0"));
    }

//    static String getRecordId () {
//
//    }
//
//

//
//    // 값 저장하기
//    private void setAutoLogin(int pAutoLogin){
//        SharedPreferences pref = ((SettingActivity)getActivity()).getSharedPreferences("pref", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("autoLogin", String.valueOf(pAutoLogin));
//        editor.commit();
//    }
}
