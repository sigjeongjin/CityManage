package com.citymanage.member;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_password_change, container, false);

        EditText idEv = (EditText) rootView.findViewById(R.id.idEv);
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

                        MemberService service = retrofit.create(MemberService.class);
                        final retrofit2.Call<MemberRepo> pwdChange = service.getMemberPwdChange(Module.getRecordId(getContext()), Module.getRecordPwd(getContext()));
                        pwdChange.enqueue(new Callback<MemberRepo>() {
                            @Override
                            public void onResponse(Call<MemberRepo> call, Response<MemberRepo> response) {

                                MemberRepo pwdChange = response.body();

                                if (pwdChange.getResultCode().equals("200")) {
                                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(pwdEv.getWindowToken(), 0);

                                    SettingActivity activity = (SettingActivity) getActivity();
                                    activity.onFragmentChanged(4);

                                    dialog.dismiss();

                                }
                            }


                            @Override
                            public void onFailure(Call<MemberRepo> call, Throwable t) {

                            }
                        });

//                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(pwdEv.getWindowToken(), 0);
//
//                        SettingActivity activity = (SettingActivity) getActivity();
//                        activity.onFragmentChanged(4);
                    }
                };

                new AlertDialog.Builder(getContext())
                        .setTitle("비밀번호 변경 완료")
                        .setPositiveButton("확인",confirmListener)
                        .show();
            }
        });

        return rootView;
    }
}
