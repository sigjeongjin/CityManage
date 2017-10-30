package com.citymanage.member;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.citymanage.R;
import com.citymanage.member.repo.MemberRepo;
import com.citymanage.member.repo.MemberService;
import com.common.ImageRound;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.citymanage.BaseActivity.BASEHOST;

/**
 * Created by we25 on 2017-06-27.
 */

public class RegisterActivity extends AppCompatActivity {

    private static final int CANCLE_FROM_CONTENT = 0;
    private static final int PICK_FROM_CAMERA = 1; //카메라 촬영으로 사진 가져오기
    private static final int PICK_FROM_ALBUM = 2; //앨범에서 사진 가져오기
    private static final int CROP_FROM_CAMERA = 3; //가져온 사진을 자르기 위한 변수
    private static final int IMAGE_WIDTH = 150;
    private static final int IMAGE_HEIGHT = 150;

    // 기본값
    private boolean imageDraw = false;
    private EditText snm;
    private EditText spw;
    private EditText respw;
    private EditText sid;
    private EditText email;
    private EditText hp;
    private Button btnf;
    private Button btns;

    private Uri dataUri;

    ImageView gProfileShot;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        snm = (EditText) findViewById(R.id.snm);
        spw = (EditText) findViewById(R.id.spw);
        respw = (EditText) findViewById(R.id.respw);
        sid = (EditText) findViewById(R.id.sid);
        email = (EditText) findViewById(R.id.email);
        hp = (EditText) findViewById(R.id.hp);
        btnf = (Button) findViewById(R.id.btnf);
        gProfileShot = (ImageView) findViewById(R.id.registerProfileShot);

        //두개가 한세트로 이미지뷰 라운딩 api 21부터 가능한 코드
//        gProfileShot.setBackground(new ShapeDrawable(new OvalShape()));
//        gProfileShot.setClipToOutline(true);

        gProfileShot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    callCamera();
                }
            };

            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
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

            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("업로드할 이미지 선택")
                    .setNeutralButton("카메라선택", cameraListener)
                    .setPositiveButton("앨범선택", albumListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();
            }
        });

        //정보 등록 확인 부분
        btns = (Button) findViewById(R.id.btns);
        btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        // 사진 선택 안할 경우
        if (imageDraw == false) {
            Toast.makeText(RegisterActivity.this, "사진을 등록해 주세요.", Toast.LENGTH_SHORT).show();
            gProfileShot.requestFocus();
            return;
        }
        // 이름 입력 안할 경우
        if (snm.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            snm.requestFocus();
            return;
        }
        // 아이디 입력 안할 경우
        if (sid.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            sid.requestFocus();
            return;
        }
        // 이메일 입력 안할 경우
        if (email.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return;
        }
        // 핸드폰 번호 입력 안할 경우
        if (hp.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "핸드폰 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            hp.requestFocus();
            return;
        }
        // 비밀번호가 서로 일치하지 안할 경우
        if (!spw.getText().toString().equals(respw.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
            spw.setText("");
            respw.setText("");
            spw.requestFocus();
            return;
        }
        // 비밀번호의 길이가 8~20자가 아닐 경우
        if (spw.getText().toString().length() < 8 || spw.getText().toString().length() > 20) {
            Toast.makeText(RegisterActivity.this, "비밀번호는 8자~20자 이용이 가능합니다.", Toast.LENGTH_SHORT).show();
            spw.setText("");
            respw.setText("");
            spw.requestFocus();
            return;
        }

            //정보 전송중 메시지 출력
            dialog = new ProgressDialog(RegisterActivity.this);
            dialog.setMessage("Loading....");
            dialog.show();

            String path = getRealImagePath(dataUri);
            File file = new File(path);
            Log.d("Uri", path);

            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(getContentResolver().getType(dataUri)),
                            file
                    );

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

            RequestBody memberPhoto = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            RequestBody memberName = RequestBody.create(MediaType.parse("text/plain"), snm.getText().toString());
            RequestBody memberId = RequestBody.create(MediaType.parse("text/plain"), sid.getText().toString());
            RequestBody memberPwd = RequestBody.create(MediaType.parse("text/plain"), spw.getText().toString());
            RequestBody memberEmail = RequestBody.create(MediaType.parse("text/plain"), email.getText().toString());
            RequestBody memberPhone = RequestBody.create(MediaType.parse("text/plain"), hp.getText().toString());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASEHOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MemberService service = retrofit.create(MemberService.class);
            final Call<MemberRepo> repos = service.getMemberRegister(body, memberPhoto, memberName,  memberId,  memberPwd, memberPhone,  memberEmail);

            repos.enqueue(new Callback<MemberRepo>() {
                @Override
                public void onResponse(Call<MemberRepo> call, Response<MemberRepo> response) {
                    MemberRepo memberRepo = response.body();
                    dialog.dismiss();

                    if (response.isSuccessful()) {
                        if (memberRepo.getResultCode().equals("200")) {
                            Toast.makeText(RegisterActivity.this, "회원가입을 환영합니다", Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (memberRepo.getResultCode().equals("400")) {
                            Toast.makeText(RegisterActivity.this, "정보가 정확하지 않습니다", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                @Override
                public void onFailure(Call<MemberRepo> call, Throwable t) {
                    Log.e("REGISTER DEBUG ", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            }
        });
        //취소 버튼시 화면을 종료하고 로그인 화면으로 돌아감.
        btnf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {

            switch (requestCode) {
                case PICK_FROM_ALBUM: //앨범에서 사진을 가져오는 콜백

                    try {
                        Matrix matrix = new Matrix();
                        matrix.setRotate(90); //사진을 90도로 회전시키기 위해 matrix설정

                        Bitmap bm = null;
                        //Uri dataUri = data.getData();
                        dataUri = data.getData();

                        bm = MediaStore.Images.Media.getBitmap(getContentResolver(), dataUri); //앨범에서 가져온 uri로 비트맵 셋팅
                        Bitmap scaled = Bitmap.createScaledBitmap(bm, IMAGE_WIDTH, IMAGE_HEIGHT, false); //앨범 사진의 경우 크기가 너무 커서 scale 조정
                        Bitmap resized = Bitmap.createBitmap(scaled,0,0, IMAGE_WIDTH, IMAGE_HEIGHT,matrix,false); //크기가 조정된 사진의 회전 정보를 수정
                        resized = ImageRound.getRoundedCornerBitmap(resized,90);
                        gProfileShot.setImageBitmap(resized);

                        imageDraw = true;

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
                        gProfileShot.setImageBitmap(photo);
                    }
                    if (requestCode == PICK_FROM_CAMERA) {
                        Bundle extras1 = data.getExtras();
                        if (extras1 != null) {
                            Bitmap photo = extras1.getParcelable("data");
                            gProfileShot.setImageBitmap(photo);
                        }
                    }
                    if (requestCode == PICK_FROM_ALBUM) {
                        Bundle extras2 = data.getExtras();
                        if (extras2 != null) {
                            Bitmap photo = extras2.getParcelable("data");
                            gProfileShot.setImageBitmap(photo);
                        }
                    }

                    imageDraw = true;
                default:
                    break;
            }

        }
    }
    // 사진의 실제 경로를 구하는 method
    public String getRealImagePath (Uri uriPath) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uriPath, proj, null, null, null);
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();
        String path = cursor.getString(index);

        return path;
    }
}