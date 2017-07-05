package com.citymanage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SettingActivity extends BaseActivity{

    private static final int CANCLE_FROM_CONTENT = 0;
    private static final int PICK_FROM_CAMERA = 1; //카메라 촬영으로 사진 가져오기
    private static final int PICK_FROM_ALBUM = 2; //앨범에서 사진 가져오기
    private static final int CROP_FROM_CAMERA = 3; //가져온 사진을 자르기 위한 변수
    private static final int autoLoginTrue = 1;
    private static final int autoLoginFalse = 0;
    private static final int ALBUM_WIDTH = 400;
    private static final int ALBUM_HEIGHT = 300;

    private Uri mImageCaptureUri;
    private ImageView iv_receipt;

    Uri photoUri;

    Setting settingFragment;
    PasswordConfirm passwordConfirmFragment;
    PasswordChange passwordChangeFragment;

    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        settingFragment = new Setting();
        passwordConfirmFragment = new PasswordConfirm();
        passwordChangeFragment = new PasswordChange();
    }

    public void selectAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //인텐트에 사진 앨범을 쓰고, sd카드의 uri를 가저온다는것을 명시
        //사진을 여러개 선택할수 있도록 한다
        intent.setType("image/*"); //mime값이 이미지라는것을 명시
        startActivityForResult(intent,PICK_FROM_ALBUM); //앨범에서 사진을 가져오면 key값과 함께 콜백을 받음
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("ACTIVITY RESULT","ACTIVITY RESULT");
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {

            switch (requestCode) {
                case PICK_FROM_ALBUM: //앨범에서 사진을 가져오는 콜백

                    try {
                        Matrix matrix = new Matrix();
                        matrix.setRotate(90); //사진을 90도로 회전시키기 위해 matrix설정

                        Bitmap bm = null;
                        Uri dataUri = data.getData();

                        bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dataUri); //앨범에서 가져온 uri로 비트맵 셋팅
                        Bitmap scaled = Bitmap.createScaledBitmap(bm, ALBUM_WIDTH, ALBUM_HEIGHT, false); //앨범 사진의 경우 크기가 너무 커서 scale 조정
                        Bitmap resized = Bitmap.createBitmap(scaled,0,0,ALBUM_WIDTH,ALBUM_HEIGHT,matrix,false); //크기가 조정된 사진의 회전 정보를 수정

                        settingFragment.gProfileChangeIv.setImageBitmap(resized);

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

    public void onFragmentChanged(int index) {
        if (index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, passwordConfirmFragment).commit();
        } else if (index == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, passwordChangeFragment).commit();
        } else if (index ==2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, settingFragment).commit();
        }
    }

}
