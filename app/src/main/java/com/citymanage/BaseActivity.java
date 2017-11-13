package com.citymanage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {


//    public static final String BASEHOST = "http://172.30.1.18:8080"; //민정 집 톰캣
//    public static final String BASEHOST = "http://192.168.0.2:8080"; // 현진 집 톰캣
    public static final String BASEHOST = "http://192.168.0.30:8080"; // 현진 집 톰캣

//    public static final String BASEHOST = "http://kukjae.iptime.org:8085"; // 현진 집 톰캣

    /******** 프로그램 실행 후 필요한 상수 값들 정의 시작 ********/

    public static final int CANCLE_FROM_CONTENT = 0;
    public static final int PICK_FROM_CAMERA = 1; //카메라 촬영으로 사진 가져오기
    public static final int PICK_FROM_ALBUM = 2; //앨범에서 사진 가져오기
    public static final int CROP_FROM_CAMERA = 3; //가져온 사진을 자르기 위한 변수
    public static final int autoLoginTrue = 1;
    public static final int autoLoginFalse = 0;
    public static final int ALBUM_WIDTH = 400;
    public static final int ALBUM_HEIGHT = 300;
    public static final int IMAGE_WIDTH = 150;
    public static final int IMAGE_HEIGHT = 150;

    /******** 프로그램 실행 후 필요한 상수 값들 정의 끝 ********/

    public ProgressDialog dialog; //프로그레스바 다이얼로그
    public AlertDialog closeAlertDialog; //back 버튼 눌렀을경우 다이얼로그
    public int resultCode; //response 응답코드 변수
    public String url = ""; //요청 cityUrl 셋팅 변수

    /******** 사이드 네비게이션바 사진 이름 셋팅하기 위한 변수 정의 시작 ********/
    public ImageView profileShot;
    public TextView profileName;

    /******** 사이드 네비게이션바 사진 이름 셋팅하기 위한 변수 정의 끝 ********/

    public static final String RESULT_SUCCESS = "200";
    public static final String RESULT_FAIL = "204";
    public static final String RESULT_FAIL_MESSAGE = "서버와 통신중 오류가 발생 하였습니다.";
    public static final String NETWORK_DISCONNECT_MESSAGE = "네트워크 연결을 확인해 주세요.";
}
