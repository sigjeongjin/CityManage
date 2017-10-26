package com.citymanage.push;

import android.util.Log;
import android.widget.Toast;

import com.citymanage.member.repo.PushInfoRepo;
import com.citymanage.push.repo.PushService;
import com.common.Module;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.citymanage.BaseActivity.BASEHOST;

/**
 * Created by minjeongkim on 2017-10-15.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        // 설치할나 주기적으로 토큰을 생성
        String refreshedToken = FirebaseInstanceId.getInstance().getToken(); // 토큰 값을 가져옴
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // 생성한 토큰을 서버로 날려서 저장하기 위해서 만듬
        // sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {

    }
}
