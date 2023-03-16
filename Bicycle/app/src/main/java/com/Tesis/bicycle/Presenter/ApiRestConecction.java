package com.Tesis.bicycle.Presenter;

import android.content.Context;

import com.Tesis.bicycle.Presenter.Client.ClientRetrofit;
import com.Tesis.bicycle.Service.ApiRest.AppUserHasRouteApiRestService;
import com.Tesis.bicycle.Service.ApiRest.AuthRestService;
import com.Tesis.bicycle.Service.ApiRest.RouteApiRestService;

public class ApiRestConecction {

    public static final String URL="http://192.168.0.7:9191/";
    public static final String URL_ROUTE=ApiRestConecction.URL+"route/";
    public static final String URL_APPUSERHASROUTE=ApiRestConecction.URL+"appuserroute/";
    public static final String URL_AUTH=ApiRestConecction.URL+"auth/";

    public static RouteApiRestService getServiceRoute(Context context){
        return ClientRetrofit.getClient(URL_ROUTE,context).create(RouteApiRestService.class);
    }

    public static AppUserHasRouteApiRestService getServiceAppUserHasRoute(Context context){
        return ClientRetrofit.getClient(URL_APPUSERHASROUTE,context).create(AppUserHasRouteApiRestService.class);
    }

    public static AuthRestService getAuthService(Context context){
        return ClientRetrofit.getClient(URL_AUTH,context).create(AuthRestService.class);
    }
}
