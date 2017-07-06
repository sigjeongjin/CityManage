package com.citymanage;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by we25 on 2017-06-30.
 */

public class BaseActivity extends AppCompatActivity {

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


    /* 프로그램 실행 후 필요한 상수 값들 정의 끝 */

    public ProgressDialog dialog; //프로그레스바 다이얼로그
    int ResultCode; //response 응답코드 변수
    String url = ""; //요청 url 셋팅 변수

}
