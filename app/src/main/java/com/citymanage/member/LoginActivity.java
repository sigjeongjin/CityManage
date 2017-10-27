package com.citymanage.member;

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

import com.citymanage.BaseActivity;
import com.citymanage.MainActivity;
import com.citymanage.R;
import com.citymanage.member.repo.MemberRepo;
import com.citymanage.member.repo.MemberService;
import com.common.Module;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity {

    CheckBox autoLoginChk;
    TextView loginTv;

    EditText idEt, password;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();

        autoLoginChk = (CheckBox) findViewById(R.id.autoLoginChk);
        Button btnLogin = (Button)findViewById(R.id.btnLogin) ;

        idEt = (EditText)findViewById(R.id.idEt);
        password = (EditText)findViewById(R.id.password);

        if(Module.getAutoLogin(getApplicationContext()) == 1) {
            autoLoginChk.setChecked(true);
            idEt.setText(Module.getRecordId(getApplicationContext()));
            password.setText(Module.getRecordPwd(getApplicationContext()));
        }

        btnLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            String memberId = idEt.getText().toString();
            String memberPwd = password.getText().toString();

            //아이디와 비밀번호가 공백일때 출력메세지
            if(memberId.equals("") && memberPwd.equals("")){
                Toast.makeText(LoginActivity .this,"아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            //아이디가 공백일때 출력메세지
            else if(memberId.equals("")){
                Toast.makeText(LoginActivity .this,"아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            //비밀번호가 공백일때 출력메세지
            else if(memberPwd.equals("")){
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
                final Call<MemberRepo> repos = service.getMemberLogin(memberId, memberPwd);

                repos.enqueue(new Callback<MemberRepo>() {
                    @Override
                    public void onResponse(Call<MemberRepo> call, Response<MemberRepo> response) {
                        dialog.dismiss();

                        MemberRepo memberRepo= response.body();

                        if(memberRepo != null) {
                            if(memberRepo.getResultCode().equals("200")) {
                                Intent intent;

                                if(Module.getLocation(getApplicationContext()) == 1) {
                                    Toast.makeText(LoginActivity.this, memberRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                                    intent = new Intent(getApplication(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    Module.setRecordId(getApplicationContext(),idEt.getText().toString());
                                    Module.setRecordPwd(getApplicationContext(), password.getText().toString());

                                    Module.setProfileImageUrl(getApplicationContext(), memberRepo.getMemberPhotoOriginal());

                                } else {
                                    Toast.makeText(LoginActivity.this, memberRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                                    intent = new Intent(getApplication(), AddressSearchActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    Module.setRecordId(getApplicationContext(),idEt.getText().toString());
                                    Module.setRecordPwd(getApplicationContext(), password.getText().toString());

                                    Module.setProfileImageUrl(getApplicationContext(), memberRepo.getMemberPhotoOriginal());
                                }

                                if(autoLoginChk.isChecked()){
                                    Module.setAutoLogin(getApplicationContext(),1);
                                } else {
                                    Module.setAutoLogin(getApplicationContext(),0);
                                }

                                startActivity(intent);
                                finish();
                            } else if(memberRepo.getResultCode().equals("204")) {
                                Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 다시 확인하세요.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, memberRepo.getResultMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MemberRepo> call, Throwable t) {
                        Log.e("DEBUG Message : " , t.getMessage());
                        Toast.makeText(LoginActivity.this, "네트워크 연결을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        // 자동 로그인 가능 하도록 셋팅
        // sidenavgation에서 로그아웃을 눌렀을경우 logout플래그가 인텐트에 셋팅되어 있는지 먼저 확인 한 후 자동로그아웃 기능을
        // 실행하지 말지 선택

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