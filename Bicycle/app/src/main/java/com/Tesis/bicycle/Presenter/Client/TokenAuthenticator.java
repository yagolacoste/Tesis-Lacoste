package com.Tesis.bicycle.Presenter.Client;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.TokenRefreshRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.TokenRefreshResponse;
import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;
import com.Tesis.bicycle.Model.Room.RefreshToken;
import com.Tesis.bicycle.Presenter.AppDataBase;
import com.Tesis.bicycle.Service.ApiRest.AuthRestService;

import java.io.IOException;
import java.util.List;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {
    private Context context;
    private AuthRestService myServiceHolder;
    private AppDataBase db;


    public TokenAuthenticator(Context context) {
        this.context = context;
       // this.myServiceHolder = ApiRestConecction.getAuthService(context);
        this.db = Room.databaseBuilder(context,AppDataBase.class, Constants.NAME_DATA_BASE)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }



    @Nullable
    @Override
    public synchronized Request authenticate(@Nullable Route route, @NonNull Response response) throws IOException {
        if (myServiceHolder == null) {
            return null;
        }
        List<RefreshTokenDto> oldRefreshToken=db.refreshTokenService().getAll();

        TokenRefreshRequest tokenRefreshRequest=new TokenRefreshRequest(oldRefreshToken.get(0).getRefreshToken());
        retrofit2.Response retrofitResponse = myServiceHolder.refreshtoken(tokenRefreshRequest).execute();

        if (retrofitResponse != null) {
            TokenRefreshResponse refreshTokenResponse = (TokenRefreshResponse) retrofitResponse.body();
            RefreshTokenDto refreshToken=oldRefreshToken.get(0);
            refreshToken.setAccessToken(refreshTokenResponse.getAccessToken());
            refreshToken.setRefreshToken(refreshTokenResponse.getRefreshToken());
            RefreshToken newRefreshToken=new RefreshToken(refreshToken);
            db.refreshTokenService().add(newRefreshToken);
            String newAccessToken = refreshTokenResponse.getAccessToken();
            return response.request().newBuilder()
                    .addHeader("Authorization", newAccessToken)
                    .build();
        }
        return null;

    }

}
