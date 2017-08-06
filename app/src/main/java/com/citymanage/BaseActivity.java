package com.citymanage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by we25 on 2017-06-30.
 */

public class BaseActivity extends AppCompatActivity {

//    public static final  String HOST = "http://172.30.1.43:3000"; //민정 집
//    public static final String HOST = "http://192.168.0.230:3000"; //학원
    public static final String HOST = "http://192.168.0.2:3000"; //현진 집
//    public static final String HOST = "http://192.168.0.2:8080"; //현진 집 톰캣

    /* 프로그램 실행 후 필요한 상수 값들 정의 시작 */
    private static final int CANCLE_FROM_CONTENT = 0;
    private static final int PICK_FROM_CAMERA = 1; //카메라 촬영으로 사진 가져오기
    private static final int PICK_FROM_ALBUM = 2; //앨범에서 사진 가져오기
    private static final int CROP_FROM_CAMERA = 3; //가져온 사진을 자르기 위한 변수
    private static final int autoLoginTrue = 1;
    private static final int autoLoginFalse = 0;
    private static final int ALBUM_WIDTH = 400;
    private static final int ALBUM_HEIGHT = 300;

    public static final String LOGIN = HOST + "/login";
    public static final String REGISTER = HOST + "/register";
    public static final String PUSH_HISTORY_HOST = HOST + "/pushHistory";
    public static final String TM_LIST_URL = HOST + "/tmList";
    public static final String SM_LIST_URL = HOST + "/smList";
    public static final String FAVORITE_HOST = HOST + "/favoriteList"; // 즐찾 추가
    public static final String TM_INFO_URL = HOST + "/tmInfo";
    public static final String SM_INFO_URL = HOST + "/smInfo";
    public static final String CITYURL = HOST + "/cityList";
    public static final String SATATEURL = HOST + "/stateList";

    /* 프로그램 실행 후 필요한 상수 값들 정의 끝 */

    public ProgressDialog dialog; //프로그레스바 다이얼로그
    public AlertDialog closeAlertDialog; //back 버튼 눌렀을경우 다이얼로그
    public int resultCode; //response 응답코드 변수
    public String url = ""; //요청 cityUrl 셋팅 변수
}
