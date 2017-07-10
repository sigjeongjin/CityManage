package com.citymanage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.common.Module;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    //전역 변수 , result 코드 생성
    String resultCode;

    //자동 로드인 준비
    SharedPreferences setting;
    SharedPreferences.Editor editor;


    private Button btnregister;
    CheckBox autologin;
    Button btnLogin;
    EditText email, password;
    String url = "http://192.168.0.230:3000/login?loginId=bang&pwd=1234";
//    StringBuilder url2= "http";

    ProgressDialog dialog;

    CheckBox autoLoginChk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

            //정보를 보내고 받음.
            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String string) {
                    parseJsonData(string);

                    // 코드를 400으로 받은경우 메세지 출력
                    if(resultCode.equals("400")) {
                        Toast.makeText(LoginActivity.this, "정보가 정확하지 않습니다", Toast.LENGTH_SHORT).show();

                    }

                    //코드를 200으로 받은경우 메세지 출력 및 실행
                    else if(resultCode.equals ("200")) {
                        Toast.makeText(LoginActivity.this, "로그인을 환영합니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(), MainActivity.class);

                        Module.setRecordId(getApplicationContext(),email.getText().toString());
                        Module.setRecordPwd(getApplicationContext(), password.getText().toString());

                        if(autoLoginChk.isChecked()){
                            Module.setAutoLogin(getApplicationContext(),1);
                        } else {
                            Module.setAutoLogin(getApplicationContext(),0);
                        }

                        startActivity(intent);
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
        if(Module.getAutoLogin(getApplicationContext()) == 1 && Module.getRecordId(getApplicationContext()) != ""
                && Module.getRecordPwd(getApplicationContext()) != "") {
            autoLoginChk.setChecked(true);
            email.setText(Module.getRecordId(getApplicationContext()));
            password.setText(Module.getRecordPwd(getApplicationContext()));

            Log.i("onclick","onclick");
            btnLogin.callOnClick();
            Log.i("onclick","onclick");
        }

        //회원가입 화면으로 전환
        btnregister = (Button) findViewById(R.id.btnregister);
        btnregister.setOnClickListener(new Button.OnClickListener(){
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

            resultCode = object.getString("resultCode");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }

}
