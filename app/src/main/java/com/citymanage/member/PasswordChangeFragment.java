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

    public ProgressDialog dialog; //프로그레스바 다이얼로그
    String memberId;
    String memberPwd;
    String memberPwdChange;
    String memberPwdConfirm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        memberId = Module.getRecordId(getContext());
        memberPwd = Module.getRecordPwd(getContext());

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_password_change, container, false);
        final EditText memberChangePwd = (EditText) rootView.findViewById(R.id.memberChangePwd);
        final EditText pwdEv = (EditText) rootView.findViewById(R.id.pwdEv);

        Button settingGoBtn = (Button) rootView.findViewById(R.id.settingGoBtn);


        settingGoBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                memberPwdChange = memberChangePwd.getText().toString();
                memberPwdConfirm = pwdEv.getText().toString();

                // 변경할 비밀번호와 비밀번호 확인이 다를 경우
                if (!memberPwdChange.equals(memberPwdConfirm)) {
                    Toast.makeText(getActivity(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 변경할 비밀번호의 길이가 8~20자가 아닐 경우
                if(memberPwdChange.length() < 8 || memberPwdChange.length() > 20){
                    Toast.makeText(getActivity(), "비밀번호는 8자~20자 이용이 가능합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DialogInterface.OnClickListener confirmListener = new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(final DialogInterface dialog, int which) {

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BaseActivity.BASEHOST)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                        MemberService service = retrofit.create(MemberService.class);

                        final Call<MemberRepo> repos = service.getMemberPwdChange(memberPwdChange ,Module.getRecordId(getContext()),Module.getRecordPwd(getContext()));
                        repos.enqueue(new Callback<MemberRepo>() {

                            @Override
                            public void onResponse(Call<MemberRepo> call, Response<MemberRepo> response) {

                                MemberRepo pwdChange = response.body();

                                if (pwdChange.getResultCode().equals("200")) {

                                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(pwdEv.getWindowToken(), 0);

                                    Toast.makeText(getActivity(), "비밀번호 변경 되었습니다.", Toast.LENGTH_SHORT).show();

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
                                Toast.makeText(getActivity(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                };

                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                new AlertDialog.Builder(getContext())
                .setTitle("비밀번호를 변경하시겠습니까?")
                .setPositiveButton("변경",confirmListener)
                .setNegativeButton("취소", cancelListener)
                .show();
            }
        });
        return rootView;
    }
}