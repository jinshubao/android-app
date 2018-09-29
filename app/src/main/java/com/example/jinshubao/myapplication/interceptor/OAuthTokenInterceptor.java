package com.example.jinshubao.myapplication.interceptor;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.jinshubao.myapplication.model.AuthToken;
import com.example.jinshubao.myapplication.utils.JSONUtils;
import com.example.jinshubao.myapplication.utils.SPUtils;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OAuthTokenInterceptor implements Interceptor {

    private static final String TAG = "OAuthTokenInterceptor";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        String token = SPUtils.getString(SPUtils.TOKEN_KEY);
        String tokenType = SPUtils.getString(SPUtils.TOKEN_TYPE);
        if (!TextUtils.isEmpty(token)) {
            request = request.newBuilder()
                    .header("Authorization", tokenType + " " + token).build();
        }
        Response response = chain.proceed(request);
        if (response.code() == 401 || response.code() == 301) {
            //token失效
            AuthToken authToken = getAuthToken(chain);
            if (authToken != null) {
                request = request.newBuilder()
                        .header("Authorization", authToken.getTokenType() + " " + authToken.getAccessToken())
                        .build();
                return chain.proceed(request);
            }
        }
        return response;
    }

    private AuthToken getAuthToken(@NonNull Chain chain) throws IOException {
        String url = "http://10.52.2.100:8014/oauth/token";
        String credential = Credentials.basic("shangying", "secret");
        FormBody body = new FormBody.Builder()
                .add("grant_type", "password")
                .add("username", "user")
                .add("password", "1")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", credential)
                .post(body)
                .build();
        Response response = chain.proceed(request);
        if (response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String bodyString = responseBody.string();
                AuthToken token = JSONUtils.fromJson(bodyString, AuthToken.class);
                this.refreshToken(chain, token);
                return token;
            }
        }
        return null;
    }

    private void refreshToken(@NonNull Chain chain, @NonNull AuthToken token) {
        SPUtils.putString(SPUtils.TOKEN_KEY, token.getAccessToken());
        SPUtils.putString(SPUtils.TOKEN_TYPE, token.getTokenType());
    }
}
