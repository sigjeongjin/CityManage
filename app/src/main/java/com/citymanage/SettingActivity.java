package com.citymanage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SettingActivity extends BaseActivity{

    private static final int CANCLE_FROM_CONTENT = 0;
    private static final int PICK_FROM_CAMERA = 1; //카메라 촬영으로 사진 가져오기
    private static final int PICK_FROM_ALBUM = 2; //앨범에서 사진 가져오기
    private static final int CROP_FROM_CAMERA = 3; //가져온 사진을 자르기 위한 변수
    private static final int autoLoginTrue = 1;
    private static final int autoLoginFalse = 0;

    private Uri mImageCaptureUri;
    private ImageView iv_receipt;
    private String absoultePath;

    Switch autoLoginOnOffSwitch;
    Button passwordChangeButton;
    ImageView profileChangeImageView;

    Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        autoLoginOnOffSwitch = (Switch) findViewById(R.id.autoLoginOnOffSwitch);
        passwordChangeButton = (Button) findViewById(R.id.passwordChangeButton);
        profileChangeImageView = (ImageView) findViewById(R.id.profileChangeImageView);

        autoLoginOnOffSwitch.setChecked((0 == getAutoLogin()) ? false : true);

        Log.i("autologin",String.valueOf(getAutoLogin()));

        profileChangeImageView.setOnClickListener(new View.OnClickListener() {
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

                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("업로드할 이미지 선택")
                        .setPositiveButton("앨범선택",albumListener)
                        .setNegativeButton("취소",cancelListener)
                        .show();
            }
        });

        autoLoginOnOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
    }

    // 값 불러오기
    private int getAutoLogin(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return Integer.parseInt(pref.getString("autoLogin", "0"));
    }

    // 값 저장하기
    private void setAutoLogin(int pAutoLogin){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("autoLogin", String.valueOf(pAutoLogin));
        editor.commit();
    }

    public void selectAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //사진을 여러개 선택할수 있도록 한다
        intent.setType("image/*");
        startActivityForResult(intent,PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("requestCode", String.valueOf(requestCode));

        Log.i("resultCode", String.valueOf(resultCode));

        if(resultCode == RESULT_OK) {

            switch (requestCode) {
                case PICK_FROM_ALBUM:

                    try {
                        Matrix matrix = new Matrix();
                        matrix.setRotate(90);

                        Bitmap bm = null;
                        Uri dataUri = data.getData();

                        bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dataUri);
                        Bitmap resized = Bitmap.createScaledBitmap(bm, 400, 300, false);

//                        resized.
//                        Log.i("resized",resized.get)

                        profileChangeImageView.setImageBitmap(resized);

                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;

                case CANCLE_FROM_CONTENT:
                    break;
                default:
                    break;
            }

        }
    }

}
