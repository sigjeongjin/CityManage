package com.citymanage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class SettingActivity extends AppCompatActivity{

    private final static int PICK_FROM_CAMERA = 0;
    private final static int PICK_FROM_ALBUM = 1;
    private final static int CROP_FROM_IMAGE = 2;

    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto;
    private String absoultePath;

    Switch autoLoginOnOffSwitch;
    Button passwordChangeButton;
    ImageView profileChangeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        autoLoginOnOffSwitch = (Switch) findViewById(R.id.autoLoginOnOffSwitch);
        passwordChangeButton = (Button) findViewById(R.id.passwordChangeButton);
        profileChangeImageView = (ImageView) findViewById(R.id.profileChangeImageView);

        profileChangeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(resultCode != RESULT_OK) return;

        switch (requestCode){

            case PICK_FROM_ALBUM : {
                mImageCaptureUri = data.getData();

            }

            case PICK_FROM_CAMERA : {
                Intent intent =new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri,"image/*");

                intent.putExtra("outputX",200);
                intent.putExtra("outputY", 200);
                intent.putExtra("aspecX",1);
                intent.putExtra("aspecY",1);
                intent.putExtra("scale",true);
                startActivityForResult(intent, CROP_FROM_IMAGE);
                break;
            }

            case CROP_FROM_IMAGE : {
                if(resultCode != RESULT_OK) {
                    return;
                }

                Log.i("BUNDLE",data.getDataString());

                Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                        "/SmartWheel" + System.currentTimeMillis() + ".jpg";

                if(extras != null) {
                    Bitmap photo = extras.getParcelable("mParcelledData");

                    Log.i("STRING", extras.toString());
                    iv_UserPhoto.setImageBitmap(photo);

                    storeCropImage(photo, filePath);
                    absoultePath = filePath;
                    break;
                }

                File f = new File(mImageCaptureUri.getPath());

                if(f.exists()){
                    f.delete();
                }
            }
        }
    }

    public void doTakeAlbumAction(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    private void storeCropImage(Bitmap bitmap, String filePath) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel";
        File directory_SmartWheel = new File(dirPath);

        if(!directory_SmartWheel.exists()) directory_SmartWheel.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {

            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
