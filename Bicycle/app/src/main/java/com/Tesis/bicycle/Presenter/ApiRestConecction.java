package com.Tesis.bicycle.Presenter;

import com.Tesis.bicycle.Presenter.Client.ClientRetrofit;
import com.Tesis.bicycle.Service.RouteApiRestService;

public class ApiRestConecction {

    public static final String URL="http://192.168.1.19:8080/route/";

    public static RouteApiRestService getService(){
        return ClientRetrofit.getClient(URL).create(RouteApiRestService.class);
    }
}
