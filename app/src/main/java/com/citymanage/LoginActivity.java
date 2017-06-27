package com.citymanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    CheckBox autoLogin;
    Button btnLogin, btnregister;
    EditText email, password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//
//        Button btn = (Button)findViewById(R.id.btnLogin);
//        btn.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View view)  {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    public void OnButtonClick(View view) {




            
        Button btn = (Button)findViewById(R.id.btnLogin) ;
        btnLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                EditText email = (EditText)findViewById(R.id.email);
                String id = email.getText().toString();
                EditText password = (EditText)findViewById(R.id.password);
                String pw = password.getText().toString();


                if(id.equals("") && pw.equals("")){
                    Toast.makeText(LoginActivity .this,"아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(id.equals("")){
                    Toast.makeText(LoginActivity .this,"아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(pw.equals("")){
                    Toast.makeText(LoginActivity .this,"비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(!id.equals("abc")){
                    Toast.makeText(LoginActivity .this,"아이디가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(!pw.equals("abc")){
                    Toast.makeText(LoginActivity .this,"비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(id.equals("abc") && pw.equals("abc")) {

                }

                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}


