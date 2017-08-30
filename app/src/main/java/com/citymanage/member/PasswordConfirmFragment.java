package com.citymanage.member;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.citymanage.BaseActivity;
import com.citymanage.R;
import com.citymanage.member.repo.MemberRepo;
import com.citymanage.member.repo.MemberService;
import com.citymanage.setting.SettingActivity;
import com.common.Module;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by we25 on 2017-07-04.
 */

public class PasswordConfirmFragment extends Fragment {

    String id;
    String pwd;

    public static final String CHECK = "http://192.168.0.230:3000/check";
    public ProgressDialog dialog; //프로그레스바 다이얼로그
    int resultCode; //response 응답코드 변수
    String resultMessage = ""; //계정 확인 후 메세지

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_password_confirm, container, false);

       id = Module.getRecordId(getContext());
       pwd = Module.getRecordPwd(getContext());

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
                String pwdstr = pwdEv.getText().toString();
                MemberService service = retrofit.create(MemberService.class);
                final Call<MemberRepo> repos = service.getMemberPwdConfirm(id,pwdstr);

                repos.enqueue(new Callback<MemberRepo>() {
                    @Override
                    public void onResponse(Call<MemberRepo> call, Response<MemberRepo> response) {

                        MemberRepo pwdConfirm = response.body();



                        if (pwdConfirm.getResultCode().equals("200")) {
                            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(pwdEv.getWindowToken(), 0);

                            SettingActivity activity = (SettingActivity) getActivity();
                            activity.onFragmentChanged(2);


                            dialog.dismiss();

                        } else {


                            Toast.makeText(getActivity(), "잘못된 비밀번호 입니다.", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();

                            return;
                        }

                    }

                    @Override
                    public void onFailure(retrofit2.Call<MemberRepo> call, Throwable t) {

                    }
                });
//                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(pwdEv.getWindowToken(), 0);
//
//                SettingActivity activity = (SettingActivity) getActivity();
//                activity.onFragmentChanged(2);
//                dialog.dismiss();

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
