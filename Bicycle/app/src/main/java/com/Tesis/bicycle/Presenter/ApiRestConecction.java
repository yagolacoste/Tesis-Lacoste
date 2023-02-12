package com.Tesis.bicycle.Presenter;

import com.Tesis.bicycle.Presenter.Client.ClientRetrofit;
import com.Tesis.bicycle.Service.ApiRest.AppUserHasRouteApiRestService;
import com.Tesis.bicycle.Service.ApiRest.AuthService;
import com.Tesis.bicycle.Service.ApiRest.RouteApiRestService;

public class ApiRestConecction {

    public static final String URL="http://192.168.0.7:9191/";
    public static final String URL_ROUTE=ApiRestConecction.URL+"route/";
    public static final String URL_APPUSERHASROUTE=ApiRestConecction.URL+"appuserroute/";
    public static final String URL_AUTH=ApiRestConecction.URL+"auth/";

    public static RouteApiRestService getServiceRoute(){
        return ClientRetrofit.getClient(URL_ROUTE).create(RouteApiRestService.class);
    }

    public static AppUserHasRouteApiRestService getServiceAppUserHasRoute(){
        return ClientRetrofit.getClient(URL_APPUSERHASROUTE).create(AppUserHasRouteApiRestService.class);
    }

    public static AuthService getAuthService(){
        return ClientRetrofit.getClient(URL_AUTH).create(AuthService.class);
    }
}
