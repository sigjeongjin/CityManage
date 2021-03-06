package com.citymanage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by we25 on 2017-07-04.
 */

public class SettingFragment extends Fragment {

    private static final int CANCLE_FROM_CONTENT = 0;
    private static final int PICK_FROM_CAMERA = 1; //카메라 촬영으로 사진 가져오기
    private static final int PICK_FROM_ALBUM = 2; //앨범에서 사진 가져오기
    private static final int CROP_FROM_CAMERA = 3; //가져온 사진을 자르기 위한 변수
    private static final int autoLoginTrue = 1;
    private static final int autoLoginFalse = 0;
    private static final int ALBUM_WIDTH = 400;
    private static final int ALBUM_HEIGHT = 300;

    Switch gAutoLoginOnOffSw;
    Button gPwdConfirmGoBtn;
    ImageView gProfileChangeIv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);

        gAutoLoginOnOffSw = (Switch) rootView.findViewById(R.id.autoLoginOnOffSwitch);
        gPwdConfirmGoBtn = (Button) rootView.findViewById(R.id.gPwdConfirmGoBtn);
        gProfileChangeIv = (ImageView) rootView.findViewById(R.id.profileChangeIv);

        gAutoLoginOnOffSw.setChecked((0 == getAutoLogin()) ?  false : true);

        gProfileChangeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectAlbum();
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

        gPwdConfirmGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity activity = (SettingActivity) getActivity();
                activity.onFragmentChanged(1);
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

    public void selectAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //인텐트에 사진 앨범을 쓰고, sd카드의 uri를 가저온다는것을 명시
        //사진을 여러개 선택할수 있도록 한다
        intent.setType("image/*"); //mime값이 이미지라는것을 명시
        startActivityForResult(intent,PICK_FROM_ALBUM); //앨범에서 사진을 가져오면 key값과 함께 콜백을 받음
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {

            switch (requestCode) {
                case PICK_FROM_ALBUM: //앨범에서 사진을 가져오는 콜백

                    try {
                        Matrix matrix = new Matrix();
                        matrix.setRotate(90); //사진을 90도로 회전시키기 위해 matrix설정

                        Bitmap bm = null;
                        Uri dataUri = data.getData();

                        bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), dataUri); //앨범에서 가져온 uri로 비트맵 셋팅
                        Bitmap scaled = Bitmap.createScaledBitmap(bm, ALBUM_WIDTH, ALBUM_HEIGHT, false); //앨범 사진의 경우 크기가 너무 커서 scale 조정
                        Bitmap resized = Bitmap.createBitmap(scaled,0,0,ALBUM_WIDTH,ALBUM_HEIGHT,matrix,false); //크기가 조정된 사진의 회전 정보를 수정

                        gProfileChangeIv.setImageBitmap(resized);

                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;

                case CANCLE_FROM_CONTENT: //앨범에서 취소 버튼을 눌렀을때 오는 콜백
                    break;
                default:
                    break;
            }

        }
    }
}
