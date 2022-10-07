package com.Tesis.bicycle.Presenter;

import com.Tesis.bicycle.Presenter.Client.ClientRetrofit;
import com.Tesis.bicycle.Service.ApiRest.AppUserHasRouteApiRestService;
import com.Tesis.bicycle.Service.ApiRest.RouteApiRestService;

public class ApiRestConecction {

    public static final String URL="http://192.168.1.14:8080/";
    public static final String URL_Route=ApiRestConecction.URL+"route/";
    public static final String URL_STATISTICS=ApiRestConecction.URL+"appuserroute/";

    public static RouteApiRestService getServiceRoute(){
        return ClientRetrofit.getClient(URL_Route).create(RouteApiRestService.class);
    }

    public static AppUserHasRouteApiRestService getServiceAppUserHasRoute(){
        return ClientRetrofit.getClient(URL_STATISTICS).create(AppUserHasRouteApiRestService.class);
    }
}
