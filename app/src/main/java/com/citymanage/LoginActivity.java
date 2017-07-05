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

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    //전역 변수 , result 코드 생성
    String resultCode;

    //자동 로드인 준비
    SharedPreferences setting;
    SharedPreferences.Editor editor;


    private  Button btnregister;
    CheckBox autologin;
    Button btnLogin;
    EditText email, password;
    String url = "http://192.168.0.230:3000/login?loginId=bang&pwd=1234";
//    StringBuilder url2= "http";


    ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        // 로그인 버튼 url로 보낸 정보가 맞는경우  메인화면으로 전환
        Button btnLogin = (Button)findViewById(R.id.btnLogin) ;
        btnLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

            EditText email = (EditText)findViewById(R.id.email);
            String id = email.getText().toString();
            EditText password = (EditText)findViewById(R.id.password);
            String pw = password.getText().toString();


                //release때 주석 처리 해야함 (임시)
            email.setText("bang");
            password.setText("1234");

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

            //정보 전송중 메시지 출력

            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Loading....");
            dialog.show();


            //정보를 보내고 받음.
            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String string) {
                    parseJsonData(string);

                    // 코드를 400으로 받은경우 메세지 출력
                    Log.d("RESULTCODE","TEST");
                    if(resultCode.equals("400")) {
                        Toast.makeText(LoginActivity.this, "정보가 정확하지 않습니다", Toast.LENGTH_SHORT).show();

                    }

                    //코드를 200으로 받은경우 메세지 출력 및 실행
                    else if(resultCode.equals ("200")) {
                        Toast.makeText(LoginActivity.this, "로그인을 환영합니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);
                    }

                }
                //통신이 되지 않는 경우
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
                // RequestQueue Queue = Volley.newRequestQueue(this); 정보의 전달
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

            Log.d("RESULTCODE", "TEST2");

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
//    // 값 불러오기
//      private void getPreferences(){
//        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
//        pref.getString("autologin","");
//    }
//    //값 저장하기
//    private void savePreferences(){
//        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("autologin", "1");
//        editor.putString("id", "bang");
//        editor.putString("password", "1234");
//        editor.commit();
//    }









//    private Check Adk, UserIdChk, PwdChk, AutoChk;
//    private boolean IsAdminIdChk, IsUserIdChk, IsPwdChk, IsAutoChk;


//
//    OnCreate(){
//
//
//        AChk = (CheckBox) findViewById(R.id.Login_Admin_Code_Save_Selector);
//        Uk = (CheckBox) findViewById(R.id.Login_User_Id_Save_Selector);
//        Pk= (CheckBox) findViewById(R.id.Login_PassWord_Save_Selector);
//        AuK= (CheckBox) findViewById(R.id.Login_Auto_Check);
//
//
//        SharedPreferences pref = getSharedPreferences("Pref",
//                Context.MODE_PRIVATE);
//
//
//
//        AChk .setChecked(pref.getBoolean("AChk ", false));
//        UChk .setChecked(pref.getBoolean("UChk ", false));
//        PChk.setChecked(pref.getBoolean("PChk", false));
//        AuChK.setChecked(pref.getBoolean("AuChK", false));
//
//
//
//    }
//
//
//
//
//
//    onStop(){
//
//
//
//        SharedPreferences pref = getSharedPreferences("Pref",
//                Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//
//        editor.putString("admin", admin.getText().toString());
//        editor.putString("id", user_id.getText().toString());
//        editor.putString("passwd", passwd.getText().toString());
//        editor.putBoolean("AdminIdChk", AChk .isChecked());
//        editor.putBoolean("UserIdChk", UChk .isChecked());
//        editor.putBoolean("PwdChk", PChk.isChecked());
//        editor.putBoolean("AutoChk", AuChK.isChecked());
//        editor.commit();
//
//    public static String get저장제목(Context context) {
//        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
//        return pref.getString("저장할키ID", "");
//    }
//    public static void set저장제목(Context context, String 저장시키고싶은값) {
//        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
//        if(!pref.getString("저장할키ID", "").toUpperCase().equals(저장시키고싶은값.toUpperCase())) {
//            SharedPreferences.Editor edit = pref.edit();
//            edit.putString("저장할키ID", 저장시키고싶은값.toUpperCase());
//            edit.commit();
//            //Log.i(getClass().getSimpleName(), "SharedPreferences Set : UserID = " + userId.toUpperCase());
//        }
//
//
// autologin = (CheckBox) findViewById(R.id.autologin);
//         autologin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//@Override
////체크박스 호출 리스너
//public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//        if(isChecked){
//
//        String ID = email.getText().toString();
//
//        String PW = password.getText().toString();
//
//
//
//        editor.putString("email", ID);
//        editor.putString("password", PW);
//        editor.putBoolean("autologin_enabled", true);
//        editor.commit();
//
//        }else{
////			editor.remove("ID");
////			editor.remove("PW");
////			editor.remove("Auto_Login_enabled");
//
//        editor.clear();
//
//        editor.commit();
//
//
//        }
//        //재접속시 아이디, 패스워드 유지
//        if(setting.getBoolean("autologin_enabled", false)) {
//
//        email.setText(setting.getString("ID", ""));
//        password.setText(setting.getString("PW", ""));
//        autologin.setChecked(true);
//        }
//
//        }
//        });






