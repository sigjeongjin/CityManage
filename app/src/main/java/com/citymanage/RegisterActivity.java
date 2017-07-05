package com.citymanage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by we25 on 2017-06-27.
 */

public class RegisterActivity extends AppCompatActivity {


    String resultCode;

    private EditText snm;
    private EditText spw;
    private EditText respw;
    private EditText sid;
    private EditText hp;
    private Button btns;
    private Button btnf;

    //url 생성 192.168.0.230.3000 으로 register 관련 정보를 보냄
    StringBuilder url = new StringBuilder("http://192.168.0.230:3000/register?"); //?name=snm&spw=1234&respw=spw&sid=abc&hp=010

    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        snm = (EditText) findViewById(R.id.snm);
        spw = (EditText) findViewById(R.id.spw);
        respw = (EditText) findViewById(R.id.respw);
        sid = (EditText) findViewById(R.id.sid);
        hp = (EditText) findViewById(R.id.hp);
        btnf = (Button) findViewById(R.id.btnf);

        // 192.168.0.230.3000 으로 register 관련 정보를 보냄
        url.append("name=" + snm.getText().toString());
        url.append("&email=" + sid.getText().toString());
        url.append("&password=" + spw.getText().toString());
        url.append("&repassword=" + respw.getText().toString());
        url.append("&phone=" + hp.getText().toString());


        //비밀번호가 일치하는지에 대한 검사
        respw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String password = spw.getText().toString();
                String confirm = respw.getText().toString();

                // 패스워드가 일치하는 경우 녹색 표시
                if (password.equals(confirm)) {
                    spw.setBackgroundColor(Color.GREEN);
                    respw.setBackgroundColor(Color.GREEN);

                    // 패스워드가 불일치 하는경우 적색 표시
                } else {
                    spw.setBackgroundColor(Color.RED);
                    respw.setBackgroundColor(Color.RED);
                }


            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //정보 등록 확인 부분
        btns = (Button) findViewById(R.id.btns);
        btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이름 입력 확인
                if (snm.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    snm.requestFocus();
                    return;
                }
                //이메일 입력 확인
                if (sid.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    sid.requestFocus();
                    return;
                }

                //비밀번호 입력 확인
                if (spw.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    spw.requestFocus();
                    return;
                }
                //같은 비밀번호 입력 확인
                if (respw.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "똑같은 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    respw.requestFocus();
                    return;
                }

                //비밀번호가 서로 일치하는지 확인.
                if (!spw.getText().toString().equals(respw.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    spw.setText("");
                    respw.setText("");
                    spw.requestFocus();
                    return;
                }
                //핸드폰 번호 입력 확인
                if (hp.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "핸드폰 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    hp.requestFocus();
                    return;
                }

                //정보 전송중 메시지 출력
                dialog = new ProgressDialog(RegisterActivity.this);
                dialog.setMessage("Loading....");
                dialog.show();


                //정보를 보내고 받음
                StringRequest request = new StringRequest(url.toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {
                        parseJsonData(string);

                        // if 문 삽입(200일때 , 400일때)
                        // 받는  정보가 400일 경우 정보가 정확하지 않음.
                        Log.d("RESULTCODE", "TEST");
                        if (resultCode.equals("400")) {
                            Toast.makeText(RegisterActivity.this, "정보가 정확하지 않습니다", Toast.LENGTH_SHORT).show();

                        }

                        //받는 정보가 200일 경우 로그인 액티비티 클래스로 전환
                        else if (resultCode.equals("200")) {
                            Toast.makeText(RegisterActivity.this, "회원가입을 환영합니다", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplication(), LoginActivity.class);
                            startActivity(intent);
                        }
                        // 정보통신이 제대로 되지 않는 경우
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                // RequestQueue Queue = Volley.newRequestQueue(this); 정보의 전달
                RequestQueue rQueue = Volley.newRequestQueue(RegisterActivity.this);
                rQueue.add(request);
            }
        });
//                // 자신을 호출한 액티비티로 데이터를 보낸다
//                Intent result = new Intent();
//                result.putExtra("email", sid.getText().toString());
//                setResult(RESULT_OK, result);
//                finish();

        //취소 버튼시 화면을 종료하고 로그인 화면으로 돌아감.
        btnf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void parseJsonData(String jsonString) {
        try {
            //새로운 json객체 생성
            JSONObject object = new JSONObject(jsonString);

            Log.d("RESULTCODE", "TEST2");


            //결과값을 받아옴

            resultCode = object.getString("resultCode");


//            JSONObject object = new JSONObject(jsonString);
//            JSONArray fruitsArray = object.getJSONArray("fruits");
//            ArrayList al = new ArrayList();
//
//            for(int i = 0; i < fruitsArray.length(); ++i) {
//                al.add(fruitsArray.getString(i));
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }
}
//    protected void onActivityResult(int requestCode,
//                                    int resultCode,
//                                    Intent data) {
//
//        if (requestCode == PICK_FROM_CAMERA) {
//            Bundle extras = data.getExtras();
//            if (extras != null) {
//                Bitmap photo = extras.getParcelable("data");
//                imgview.setImageBitmap(photo);
//            }
//        }
//        if (requestCode == PICK_FROM_GALLERY) {
//            Bundle extras2 = data.getExtras();
//            if (extras2 != null) {
//                Bitmap photo = extras2.getParcelable("data");
//                imgview.setImageBitmap(photo);
//            }
//        }
//    }
//}










//package com.example.android.myapplication;
//
//        import android.content.Intent;
//        import android.database.Cursor;
//        import android.graphics.Bitmap;
//        import android.graphics.BitmapFactory;
//        import android.media.ExifInterface;
//        import android.net.Uri;
//        import android.os.Bundle;
//        import android.provider.MediaStore;
//        import android.support.annotation.Nullable;
//        import android.support.v7.app.AppCompatActivity;
//
//        import java.io.IOException;
//
//        import static android.R.attr.path;
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final int CAMERA_CODE = 0;
//    private static final int GALLERY_CODE = 1;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//    private void SelectPhoto() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, CAMERA_CODE);
//    }
//
//    @Override
//    private void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//
//            switch (requestCode) {
//
//                case GALLERY_CODE:
//                    SendPicture(data);
//                    break;
//                case CAMERA_CODE:
//                    SendPicture(data); //카메라에서 가져오기
//                    break;
//
//                default:
//                    break;
//
//            }
//        }
//    }
//
//    private void SendPicture(Intent data) {
//        Uri imgUri = data.getData();
//        String imagePath = getRealPathFromURI(imgUri); // path 경로
//        ExifInterface exif = null;
//        try {
//            exif = new ExifInterface(imagePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//        int exifDegree = exifOrientationToDegrees(exifOrientation);
//
//        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
//        iv_receipt.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
//
//    }
//
//    ExifInterface exif = null;
//    try
//
//    {
//        exif = new ExifInterface(path);
//    } catch(
//    IOException e)
//
//    {
//        e.printStackTrace();
//    }
//
//    int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//    int exifDegree = exifOrientationToDegrees(exifOrientation);
//
//    public int exifOrientationToDegrees(int exifOrientation) {
//        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
//            return 90;
//        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
//            return 180;
//        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
//            return 270;
//        }
//        return 0;
//    }
//
//    public String getRealPathFromURI(Uri contentUri) {
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//
//    }
//}
//
//
//
//
//
//
//
//}
//

