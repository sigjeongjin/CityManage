package com.citymanage.push;

import android.util.Log;
import android.widget.Toast;

import com.citymanage.push.repo.PushInfoRepo;
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
        // 설치할때 여기서 토큰을 자동으로 만들어 준다
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // 생성한 토큰을 서버로 날려서 저장하기 위해서 만든거
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {

        String memberId = Module.getRecordId(getApplicationContext());
        String pushToken = token;

        // Add custom implementation, as needed.

        // Retrofit를 이용해 웹서버로 토큰값을 날려준다.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEHOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PushService service = retrofit.create(PushService.class);
        final Call<PushInfoRepo> repos = service.getPushTokenRegister(pushToken);

        repos.enqueue(new Callback<PushInfoRepo>() {
            @Override
            public void onResponse(Call<PushInfoRepo> call, Response<PushInfoRepo> response) {
                Log.d(TAG, "Server up Token");
                PushInfoRepo pushInfoRepo = response.body();
            }

            @Override
            public void onFailure(Call<PushInfoRepo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
