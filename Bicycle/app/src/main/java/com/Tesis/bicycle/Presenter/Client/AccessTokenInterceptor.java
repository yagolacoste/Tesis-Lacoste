package com.Tesis.bicycle.Presenter.Client;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.TokenRefreshRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.TokenRefreshResponse;
import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;
import com.Tesis.bicycle.Model.Room.RefreshToken;
import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Presenter.AppDataBase;
import com.Tesis.bicycle.Service.ApiRest.AuthApiService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AccessTokenInterceptor implements Interceptor {

    private Context context;
    private AppDataBase db;
    private AuthApiService service;

    public AccessTokenInterceptor(Context context) {
        this.context = context;
        this.db = Room.databaseBuilder(context,AppDataBase.class, Constants.NAME_DATA_BASE)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        this.service= ApiRestConnection.getAuthService();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request=chain.request();
        List<RefreshTokenDto> refreshToken=db.refreshTokenService().getAll();
        Response response=chain.proceed(newRequestWithAccessToken(request,refreshToken.get(0).getAccessToken()));

        if(response.code()== HttpURLConnection.HTTP_UNAUTHORIZED){
            TokenRefreshRequest tokenRefreshRequest=new TokenRefreshRequest(refreshToken.get(0).getRefreshToken());
            TokenRefreshResponse refreshResponse=service.refreshtoken(tokenRefreshRequest).execute().body();
            if(refreshResponse.getAccessToken()!=null) {
                RefreshToken refToken = new RefreshToken();
                refToken.setRefreshToken(refreshResponse.getRefreshToken());
                refToken.setAccessToken(refreshResponse.getAccessToken());
                refToken.setId(refreshToken.get(0).getId());
                db.refreshTokenService().add(refToken);
                response = chain.proceed(newRequestWithAccessToken(request, refreshResponse.getAccessToken()));
            }
        }
        return response;
    }

    private Request newRequestWithAccessToken(Request request, String accessToken){
        return request.newBuilder().addHeader("Authorization","Bearer "+ accessToken).build();
    }
}
