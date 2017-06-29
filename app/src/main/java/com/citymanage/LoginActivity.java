package com.citymanage;

import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    //전역 변수 , result 코드 생성
    String resultCode;

    private  Button btnregister;
    CheckBox autoLogin;
    Button btnLogin;
    EditText email, password;
    String url = "http://192.168.0.230:3000/login?loginId=bang&pwd=1234";
//    StringBuilder url2= "http";


    ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = (Button)findViewById(R.id.btnLogin) ;
        btnLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                EditText email = (EditText)findViewById(R.id.email);
                String id = email.getText().toString();
                EditText password = (EditText)findViewById(R.id.password);
                String pw = password.getText().toString();


                email.setText("bang");
                password.setText("1234");

                if(id.equals("") && pw.equals("")){
                    Toast.makeText(LoginActivity .this,"아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(id.equals("")){
                    Toast.makeText(LoginActivity .this,"아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(pw.equals("")){
                    Toast.makeText(LoginActivity .this,"비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!id.equals("bang")){
                    Toast.makeText(LoginActivity .this,"아이디가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!pw.equals("1234")){
                    Toast.makeText(LoginActivity .this,"비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setMessage("Loading....");
                dialog.show();

                StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {
                        parseJsonData(string);

                        // if 문 삽입(200일때 , 400일때)
                        Log.d("RESULTCODE","TEST");
                        if(resultCode.equals("400")) {
                            Toast.makeText(LoginActivity.this, "정보가 정확하지 않습니다", Toast.LENGTH_SHORT).show();

                        }
                        else if(resultCode.equals ("200")) {
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            startActivity(intent);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
                rQueue.add(request);
            }
        });
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

            Log.d("RESULTCODE","TEST2");

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


//  btnregister = (Button) findViewById(R.id.btnregister);
//          btnregister.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivityForResult(intent, 1000);
//        }
//        });
//        }
//        }


