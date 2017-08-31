package com.citymanage.member;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

public class PasswordChangeFragment extends Fragment {

    public static final String CHECK = "http://192.168.0.230:3000/check";
    public ProgressDialog dialog; //프로그레스바 다이얼로그
    int resultCode; //response 응답코드 변수
    String resultMessage = ""; //계정 확인 후 메세지
    String id;
    String pwd;
    String memberChangePwd;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        id = Module.getRecordId(getContext());
        pwd = Module.getRecordPwd(getContext());





        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_password_change, container, false);
        final EditText memberChangePwd = (EditText) rootView.findViewById(R.id.memberChangePwd);
        final EditText pwdEv = (EditText) rootView.findViewById(R.id.pwdEv);


        Button settingGoBtn = (Button) rootView.findViewById(R.id.settingGoBtn);
        settingGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DialogInterface.OnClickListener confirmListener = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {





                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BaseActivity.BASEHOST)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();




                        String pwdid = memberChangePwd.getText().toString();
                        String pwdstr = pwdEv.getText().toString();
                        MemberService service = retrofit.create(MemberService.class);


                        if(pwdid.length() <= 5){
                            Toast.makeText(getActivity(), "잘못된 비밀번호 입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (pwdid.equals(pwdstr)) {
                            memberChangePwd.setBackgroundColor(Color.GREEN);
                            pwdEv.setBackgroundColor(Color.GREEN);

                        } else {


                            Toast.makeText(getActivity(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        final Call<MemberRepo> repos = service.getMemberPwdChange(pwdid ,Module.getRecordId(getContext()),Module.getRecordPwd(getContext()));
                        repos.enqueue(new Callback<MemberRepo>() {




                            @Override
                            public void onResponse(Call<MemberRepo> call, Response<MemberRepo> response) {



                                MemberRepo pwdChange = response.body();

                                if (pwdChange.getResultCode().equals("200")) {

                                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(pwdEv.getWindowToken(), 0);

                                    Toast.makeText(getActivity(), "비밀번호 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                                    SettingActivity activity = (SettingActivity) getActivity();
                                    activity.onFragmentChanged(4);

                                    dialog.dismiss();

                                } else {

                                    Toast.makeText(getActivity(), "잘못된 비밀번호 입니다.", Toast.LENGTH_SHORT).show();

                                    dialog.dismiss();

                                    return;

                                }

                            }



                            @Override
                            public void onFailure(Call<MemberRepo> call, Throwable t) {

                            }
                        });




                    }
                };

                        new AlertDialog.Builder(getContext())
//                        .setTitle("비밀번호 변경 완료")
                        .setPositiveButton("확인해주세요",confirmListener)
                        .show();
            }
        });

        return rootView;
    }
}


//         if (pwdid.equals(pwdstr)) {
//                 idEv.setBackgroundColor(Color.GREEN);
//                 pwdEv.setBackgroundColor(Color.GREEN);
//
//                 // 패스워드가 불일치 하는경우 적색 표시
//                 } else {
//                 idEv.setBackgroundColor(Color.RED);
//                 pwdEv.setBackgroundColor(Color.RED);
//                 }