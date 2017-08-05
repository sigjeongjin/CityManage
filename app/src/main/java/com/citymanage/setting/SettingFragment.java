package com.citymanage.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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
import android.widget.ToggleButton;

import com.citymanage.R;
import com.common.Module;

import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

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
    private static final int IMAGE_WIDTH = 400;
    private static final int IMAGE_HEIGHT = 300;

    ToggleButton gAutoLoginOnOffBtn;
    Button gPwdConfirmGoBtn;
    ImageView gProfileChangeIv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);

        gAutoLoginOnOffBtn = (ToggleButton) rootView.findViewById(R.id.autoLoginOnOffButton);
        gPwdConfirmGoBtn = (Button) rootView.findViewById(R.id.gPwdConfirmGoBtn);
        gProfileChangeIv = (ImageView) rootView.findViewById(R.id.profileChangeIv);

        gProfileChangeIv.setBackground(new ShapeDrawable(new OvalShape()));
        gProfileChangeIv.setClipToOutline(true);

        gAutoLoginOnOffBtn.setChecked((0 == Module.getAutoLogin(getContext())) ?  false : true);

        gProfileChangeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callCamera();
                    }
                };

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
                        .setNeutralButton("카메라선택",cameraListener)
                        .setPositiveButton("앨범선택",albumListener)
                        .setNegativeButton("취소",cancelListener)
                        .show();
            }
        });

        gAutoLoginOnOffBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(Module.getAutoLogin(getContext()) == 0) {
                    Module.setAutoLogin(getContext(),1);
                    buttonView.setChecked(true);
                } else {
                    Module.setAutoLogin(getContext(),0);
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
    public void selectAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //인텐트에 사진 앨범을 쓰고, sd카드의 uri를 가저온다는것을 명시
        //사진을 여러개 선택할수 있도록 한다
        intent.setType("image/*"); //mime값이 이미지라는것을 명시
        startActivityForResult(intent,PICK_FROM_ALBUM); //앨범에서 사진을 가져오면 key값과 함께 콜백을 받음
    }

    public void callCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());

        //이미지 사이즈 변경
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", IMAGE_WIDTH);
        intent.putExtra("outputY", IMAGE_HEIGHT);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PICK_FROM_CAMERA);
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
                        Bitmap scaled = Bitmap.createScaledBitmap(bm, IMAGE_WIDTH, IMAGE_HEIGHT, false); //앨범 사진의 경우 크기가 너무 커서 scale 조정
                        Bitmap resized = Bitmap.createBitmap(scaled,0,0, IMAGE_WIDTH, IMAGE_HEIGHT,matrix,false); //크기가 조정된 사진의 회전 정보를 수정

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

                case PICK_FROM_CAMERA :
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        gProfileChangeIv.setImageBitmap(photo);
                    }
                default:
                    break;
            }

        }
    }
}


//buttonCamera.setOnClickListener(new View.OnClickListener(){
//@Override
//public void onClick(View v) {
//        //카메라 호출
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//        MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
//
//        //이미지 사이즈 변경
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 0);
//        intent.putExtra("aspectY", 0);
//        intent.putExtra("outputX", 200);
//        intent.putExtra("outputY", 250);
//
//        try {
//        intent.putExtra("return-data", true);
//        startActivityForResult(intent, PICK_FROM_CAMERA);
//        }catch (ActivityNotFoundException e) {
//        }
//
//        }
//        });