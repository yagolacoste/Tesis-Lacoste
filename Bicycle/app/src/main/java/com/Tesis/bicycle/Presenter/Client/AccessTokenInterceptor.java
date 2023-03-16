package com.Tesis.bicycle.Presenter.Client;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.JwtResponse;
import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;
import com.Tesis.bicycle.Model.Room.RefreshToken;
import com.Tesis.bicycle.Presenter.AppDataBase;
import com.Tesis.bicycle.Repository.AccessTokenRoomRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AccessTokenInterceptor implements Interceptor {

    private Context context;
    private AppDataBase db;

    public AccessTokenInterceptor(Context context) {
        this.context = context;
        this.db = Room.databaseBuilder(context,AppDataBase.class, Constants.NAME_DATA_BASE)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
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
        Request.Builder requestBuilder = chain.request().newBuilder();
        if (context != null) {
            List<RefreshTokenDto> refreshToken=db.refreshTokenService().getAll();
            if (!refreshToken.isEmpty()) {
                 requestBuilder.addHeader("Authorization", "Bearer "+refreshToken.get(0).getAccessToken()).build();
            }
        }
        return chain.proceed(requestBuilder.build());
    }
}
