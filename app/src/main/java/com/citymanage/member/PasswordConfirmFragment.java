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

    String memberId;
    String memberPwd;

    public ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_password_confirm, container, false);

        memberId = Module.getRecordId(getContext());
        memberPwd = Module.getRecordPwd(getContext());

        // 초기 아이디 셋팅
        final TextView idEv = (TextView) rootView.findViewById(R.id.idEv);
        idEv.setText(memberId);

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

            String memberPwd = pwdEv.getText().toString();
            MemberService service = retrofit.create(MemberService.class);
            final Call<MemberRepo> repos = service.getMemberPwdConfirm(memberId, memberPwd);

            repos.enqueue(new Callback<MemberRepo>() {
                @Override
                public void onResponse(Call<MemberRepo> call, Response<MemberRepo> response) {

                    MemberRepo memberRepo = response.body();

                    if (memberRepo.getResultCode().equals("200")) {
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
                    Toast.makeText(getActivity(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            }
        });
        return rootView;
    }
}