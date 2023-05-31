package com.Tesis.bicycle.Presenter;

import android.content.Context;

import com.Tesis.bicycle.Presenter.Client.ClientRetrofit;
import com.Tesis.bicycle.Service.ApiRest.AppUserHasRouteApiService;
import com.Tesis.bicycle.Service.ApiRest.AuthApiService;
import com.Tesis.bicycle.Service.ApiRest.BattleApiService;
import com.Tesis.bicycle.Service.ApiRest.RouteApiService;
import com.Tesis.bicycle.Service.ApiRest.StoredDocumentApiService;
import com.Tesis.bicycle.Service.ApiRest.UserApiService;

public class ApiRestConecction {

    public static final String URL="http://192.168.0.7:9191/";
    public static final String URL_ROUTES=ApiRestConecction.URL+"routes/";
    public static final String URL_APPUSERHASROUTES=ApiRestConecction.URL+"statistics/";
    public static final String URL_STORED_DOCUMENT=ApiRestConecction.URL+"storeddocuments/";
    public static final String URL_AUTH=ApiRestConecction.URL+"auth/";
    public static final String URL_USERS=ApiRestConecction.URL+"users/";

    public static final String URL_BATTLES=ApiRestConecction.URL+"battles/";

    public static RouteApiService getServiceRoute(Context context){
        return ClientRetrofit.getClientWithInterceptor(URL_ROUTES,context).create(RouteApiService.class);
    }

    public static AppUserHasRouteApiService getServiceAppUserHasRoute(Context context){
        return ClientRetrofit.getClientWithInterceptor(URL_APPUSERHASROUTES,context).create(AppUserHasRouteApiService.class);
    }

    public static BattleApiService getBattleApiService(Context context){
        return ClientRetrofit.getClientWithInterceptor(URL_BATTLES,context).create(BattleApiService.class);
    }

    public static AuthApiService getAuthService(){
        return ClientRetrofit.getClient(URL_AUTH).create(AuthApiService.class);
    }

    public static StoredDocumentApiService getStoredDocumentService(){
        return ClientRetrofit.getClient(URL_STORED_DOCUMENT).create(StoredDocumentApiService.class);
    }

    public static UserApiService getUserService(Context context){
        return ClientRetrofit.getClientWithInterceptor(URL_USERS,context).create(UserApiService.class);
    }


}
