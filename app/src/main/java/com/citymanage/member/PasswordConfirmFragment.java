package com.citymanage.member;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.citymanage.BaseActivity;
import com.citymanage.R;
import com.citymanage.member.repo.MemberRepo;
import com.citymanage.member.repo.MemberService;
import com.citymanage.setting.SettingActivity;
import com.common.Module;

import org.json.JSONException;
import org.json.JSONObject;


import java.lang.reflect.Member;

import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by we25 on 2017-07-04.
 */

public class PasswordConfirmFragment extends Fragment {

    public static final String CHECK = "http://192.168.0.230:3000/check";
    public ProgressDialog dialog; //프로그레스바 다이얼로그
    int resultCode; //response 응답코드 변수
    String resultMessage = ""; //계정 확인 후 메세지

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_password_confirm, container, false);

        final String id = Module.getRecordId(getContext());
        final String pwd = Module.getRecordPwd(getContext());

        final TextView idEv = (TextView) rootView.findViewById(R.id.idEv);
        idEv.setText(id); //초기 아이디 셋팅

        final EditText pwdEv = (EditText) rootView.findViewById(R.id.pwdEv);

        final Button pwdChangeGoBtn = (Button) rootView.findViewById(R.id.pwdChangeGoBtn);

        pwdChangeGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new ProgressDialog(getContext());
                dialog.setMessage("Loading....");
                dialog.show();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BaseActivity.BASEHOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                MemberService service = retrofit.create(MemberService.class);
                final retrofit2.Call<MemberRepo> pwdConfirm = service.getMemberPwdConfirm(Module.getRecordId(getContext()), Module.getRecordPwd(getContext()));
                pwdConfirm.enqueue(new Callback<MemberRepo>() {
                    @Override
                    public void onResponse(retrofit2.Call<MemberRepo> call, Response<MemberRepo> response) {

                        MemberRepo pwdConfirm = response.body();


                    }

                    @Override
                    public void onFailure(retrofit2.Call<MemberRepo> call, Throwable t) {

                    }
                });
            }

            void parseJsonData(String jsonString) {
                try {
                    JSONObject object = new JSONObject(jsonString);

                    resultCode = Integer.parseInt(object.getString("resultCode"));
                    resultMessage = object.getString("확인되었습니다.");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }

        });

        return rootView;
    }
}









//                StringBuilder sb = new StringBuilder(CHECK);
//
//                sb.append("?loginId=");
//                sb.append(idEv.getText().toString());
//                sb.append("&pwd=");
//                sb.append(pwdEv.getText().toString());

//            StringRequest pushHistoryItemRequest = new StringRequest(sb.toString(), new Response.Listener<String>() {
//                @Override
//                public void onResponse(String string) {
//                    parseJsonData(string);
//
//                    if(resultCode == 200 ) {
//                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(pwdEv.getWindowToken(), 0);
//
//                        SettingActivity activity = (SettingActivity) getActivity();
//                        activity.onFragmentChanged(2);
//                    } else if(resultCode == 201) {
//                        Toast.makeText(getContext(), resultMessage, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError volleyError) {
//                    Toast.makeText(getContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
//            });
//            RequestQueue rQueue = Volley.newRequestQueue(getContext());
//            rQueue.add(pushHistoryItemRequest);
//            }
//        });
//
//        return rootView;
//    }

//통신 후 json 파싱
