package com.citymanage;

import android.app.Activity;
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

public class RegisterActivity extends AppCompatActivity{


    String resultCode;
    private EditText snm;
    private EditText spw;
    private EditText respw;
    private EditText sid;
    private EditText hp;
    private Button btns;
    private Button btnf;

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
        btns = (Button) findViewById(R.id.btns);
        btnf = (Button) findViewById(R.id.btnf);

        url.append("name=" + snm);
        url.append("password" + spw);
        url.append("repassword" + respw);
        url.append("email" + sid);
        url.append("phone" + hp);


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
                if( password.equals(confirm) ) {
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
        btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이름 입력 확인
                if(snm.getText().toString().length() == 0 ) {
                    Toast.makeText(RegisterActivity.this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    snm.requestFocus();
                    return;
                }
                //아이디 입력 확인
                if(sid.getText().toString().length() == 0 ) {
                    Toast.makeText(RegisterActivity.this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    sid.requestFocus();
                    return;
                }

                //비밀번호 입력 확인
                if(spw.getText().toString().length() == 0 ) {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    spw.requestFocus();
                    return;
                }
                //같은 비밀번호 입력 확인
                if(respw.getText().toString().length() == 0 ) {
                    Toast.makeText(RegisterActivity.this, "똑같은 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    respw.requestFocus();
                    return;
                }

                //비밀번호가 서로 일치하는지 확인.
                if( !spw.getText().toString().equals(respw.getText().toString()) ) {
                    Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    spw.setText("");
                    respw.setText("");
                    spw.requestFocus();
                    return;
                }
                //핸드폰 번호 입력 확인
                if(hp.getText().toString().length() == 0 ) {
                    Toast.makeText(RegisterActivity.this, "핸드폰 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    hp.requestFocus();
                    return;
                }
                dialog = new ProgressDialog(RegisterActivity.this);
                dialog.setMessage("Loading....");
                dialog.show();

                StringRequest request = new StringRequest(url.toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {
                        parseJsonData(string);

                        // if 문 삽입(200일때 , 400일때)
                        Log.d("RESULTCODE","TEST");
                        if(resultCode.equals("400")) {
                            Toast.makeText(RegisterActivity.this, "정보가 정확하지 않습니다", Toast.LENGTH_SHORT).show();

                        }
                        else if(resultCode.equals ("200")) {
                            Intent intent = new Intent(getApplication(), LoginActivity.class);
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
        btnf.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
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
