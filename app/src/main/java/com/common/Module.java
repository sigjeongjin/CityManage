package com.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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
        return pref.getInt("autoLogin", 0);
    }

    static public void setAutoLogin(Context context, int pAutoLogin) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("autoLogin",pAutoLogin);
        editor.commit();
    }

    // 값 불러오기
    static public int getLocation(Context context){
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getInt("location", 0);
    }

    static public void setLocation(Context context, int pAutoLogin) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("location",pAutoLogin);
        editor.commit();
    }

    // 값 불러오기
    static public Uri getProfileImageUrl(Context context){
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        return Uri.parse(pref.getString("profileImageUrl", ""));
    }

    static public void setProfileImageUrl(Context context, String profileImageUrl) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("context",profileImageUrl);
        editor.commit();
    }
}
