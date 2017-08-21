package com.citymanage.member;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.citymanage.BaseActivity;
import com.citymanage.MainActivity;
import com.citymanage.R;
import com.citymanage.member.repo.MemberRepo;
import com.citymanage.member.repo.MemberService;
import com.common.Module;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity {


    private Button btnregister;
    CheckBox autologin;
    TextView loginTv;
    EditText idEt, password;

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

        //2017.08.20 email 로그인으로 확정짓지 않아서 id로 변경
        idEt = (EditText)findViewById(R.id.idEt);
        password = (EditText)findViewById(R.id.password);

        if(Module.getAutoLogin(getApplicationContext()) == 1) {
            autoLoginChk.setChecked(true);
            idEt.setText(Module.getRecordId(getApplicationContext()));
            password.setText(Module.getRecordPwd(getApplicationContext()));
        }

        btnLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            String id = idEt.getText().toString();
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

            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Loading....");
            dialog.show();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASEHOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                MemberService service = retrofit.create(MemberService.class);
                final Call<MemberRepo> repos = service.getMemberRepo(id,pw);

                repos.enqueue(new Callback<MemberRepo>() {
                    @Override
                    public void onResponse(Call<MemberRepo> call, Response<MemberRepo> response) {
                        dialog.dismiss();

                        MemberRepo memberRepo= response.body();

                        if(memberRepo != null) {
                            if(memberRepo.getResultCode().equals("200") ) {
                                Intent intent;

                                if(Module.getLocation(getApplicationContext()) == 1) {
                                    Toast.makeText(LoginActivity.this, memberRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                                    intent = new Intent(getApplication(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    Module.setRecordId(getApplicationContext(),idEt.getText().toString());
                                    Module.setRecordPwd(getApplicationContext(), password.getText().toString());


                                } else {
                                    Toast.makeText(LoginActivity.this, memberRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                                    intent = new Intent(getApplication(), AddressSearchActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    Module.setRecordId(getApplicationContext(),idEt.getText().toString());
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
                        } else {
                            Toast.makeText(LoginActivity.this, memberRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MemberRepo> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "등록되지 않은 회원입니다.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        //자동 로그인 가능 하도록 셋팅
        //sidenavgation에서 로그아웃을 눌렀을경우 logout플래그가 인텐트에 셋팅되어 있는지 먼저 확인 한 후 자동로그아웃 기능을
        //실행하지 말지 선택

        if(intent.getStringExtra("logout") == null) {
            if(Module.getAutoLogin(getApplicationContext()) == 1 && Module.getRecordId(getApplicationContext()) != ""
                    && Module.getRecordPwd(getApplicationContext()) != "") {
                autoLoginChk.setChecked(true);
                idEt.setText(Module.getRecordId(getApplicationContext()));
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
