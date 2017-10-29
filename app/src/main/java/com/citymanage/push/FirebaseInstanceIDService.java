package com.citymanage.push;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by minjeongkim on 2017-10-15.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        // 설치할때나 주기적으로 토큰을 생성
        String refreshedToken = FirebaseInstanceId.getInstance().getToken(); // 토큰 값을 가져옴
        Log.d(TAG, "Refreshed token: " + refreshedToken);

    }
}
