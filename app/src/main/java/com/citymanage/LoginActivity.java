package com.citymanage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.common.Module;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {


    private Button btnregister;
    CheckBox autologin;
    TextView loginTv;
    EditText email, password;

//    StringBuilder url2= "http";

    ProgressDialog dialog;

    CheckBox autoLoginChk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();

        autoLoginChk = (CheckBox) findViewById(R.id.autoLoginChk);
        Button btnLogin = (Button)findViewById(R.id.btnLogin) ;
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        if(Module.getAutoLogin(getApplicationContext()) == 1) {
            autoLoginChk.setChecked(true);
            email.setText(Module.getRecordId(getApplicationContext()));
            password.setText(Module.getRecordPwd(getApplicationContext()));
        }

        btnLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            String id = email.getText().toString();
            String pw = password.getText().toString();

            //아이디와 비밀번호가 공백일때 출력메세지
            if(id.equals("") && pw.equals("")){
                Toast.makeText(LoginActivity .this,"아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            //아이디가 공백일때 출력메세지
            else if(id.equals("")){
                Toast.makeText(LoginActivity .this,"아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            //비밀번호가 공백일때 출력메세지
            else if(pw.equals("")){
                Toast.makeText(LoginActivity .this,"비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            //아이디가 틀린경우 출력메세지
            else if(!id.equals("bang")){
                Toast.makeText(LoginActivity .this,"아이디가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            //비밀번호가가 틀린경우 출력메세지
            else if(!pw.equals("1234")){
                Toast.makeText(LoginActivity .this,"비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Loading....");
            dialog.show();

            StringBuilder sb = new StringBuilder(LOGIN);
                sb.append("?loginId=").append(email.getText().toString()).append("&pwd=").append(password.getText().toString());
            //정보를 보내고 받음.
            StringRequest request = new StringRequest(sb.toString(), new Response.Listener<String>() {
                @Override
                public void onResponse(String string) {
                    parseJsonData(string);

                    // 코드를 400으로 받은경우 메세지 출력
                    if(resultCode == 400) {
                        Toast.makeText(LoginActivity.this, "정보가 정확하지 않습니다", Toast.LENGTH_SHORT).show();
                    }

                    //코드를 200으로 받은경우 메세지 출력 및 실행
                    else if(resultCode == 200) {

                        Intent intent;

                        if(Module.getLocation(getApplicationContext()) == 1) {
                            Toast.makeText(LoginActivity.this, "로그인을 환영합니다.", Toast.LENGTH_SHORT).show();
                            intent = new Intent(getApplication(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            Module.setRecordId(getApplicationContext(),email.getText().toString());
                            Module.setRecordPwd(getApplicationContext(), password.getText().toString());


                        } else {
                            Toast.makeText(LoginActivity.this, "로그인을 환영합니다.", Toast.LENGTH_SHORT).show();
                            intent = new Intent(getApplication(), AddressSearchActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            Module.setRecordId(getApplicationContext(),email.getText().toString());
                            Module.setRecordPwd(getApplicationContext(), password.getText().toString());
                        }

                        if(autoLoginChk.isChecked()){
                            Module.setAutoLogin(getApplicationContext(),1);
                        } else {
                            Module.setAutoLogin(getApplicationContext(),0);
                        }


                        startActivity(intent);
                        finish();
                    }

                }
                //통신이 되지 않는 경우
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.i("VOLLEY ERROR", volleyError.toString());
                    dialog.dismiss();
                }
            });
                // RequestQueue Queue = Volley.newRequestQueue(this); 정보의 전달
                RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
            rQueue.add(request);
            }
        });

        //자동 로그인 가능 하도록 셋팅
        //sidenavgation에서 로그아웃을 눌렀을경우 logout플래그가 인텐트에 셋팅되어 있는지 먼저 확인 한 후 자동로그아웃 기능을
        //실행하지 말지 선택

        if(intent.getStringExtra("logout") == null) {
            if(Module.getAutoLogin(getApplicationContext()) == 1 && Module.getRecordId(getApplicationContext()) != ""
                    && Module.getRecordPwd(getApplicationContext()) != "") {
                autoLoginChk.setChecked(true);
                email.setText(Module.getRecordId(getApplicationContext()));
                password.setText(Module.getRecordPwd(getApplicationContext()));

                btnLogin.callOnClick();
            }
        }

        //회원가입 화면으로 전환
        loginTv = (TextView) findViewById(R.id.loginTv);
        loginTv.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplication(), RegisterActivity.class);
                startActivity(intent);
            }
        });
     }

    void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);

            resultCode = object.getInt("resultCode");
            String profileImageUrl = object.getString("profileImageUrl");
            Module.setProfileImageUrl(getApplicationContext(),profileImageUrl);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        createCloseAlertDialog();
    }
    private void createCloseAlertDialog() {
        DialogInterface.OnClickListener appExitListener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.finishAffinity(LoginActivity.this);
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("앱을 종료하시겠습니까?")
                .setPositiveButton("종료",appExitListener)
                .setNegativeButton("취소",cancelListener)
                .show();

    }
}
