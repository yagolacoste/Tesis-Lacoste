package com.Tesis.bicycle.Presenter;

import android.content.Context;

import com.Tesis.bicycle.Presenter.Client.ClientRetrofit;
import com.Tesis.bicycle.Service.ApiRest.AppUserHasRouteRestService;
import com.Tesis.bicycle.Service.ApiRest.AuthRestService;
import com.Tesis.bicycle.Service.ApiRest.RouteApiRestService;
import com.Tesis.bicycle.Service.ApiRest.StoredDocumentRestService;

public class ApiRestConecction {

    public static final String URL="http://192.168.0.7:9191/";
    public static final String URL_ROUTE=ApiRestConecction.URL+"route/";
    public static final String URL_APPUSERHASROUTE=ApiRestConecction.URL+"appuserroute/";
    public static final String URL_STORED_DOCUMENT=ApiRestConecction.URL+"storeddocument/";
    public static final String URL_AUTH=ApiRestConecction.URL+"auth/";

    public static RouteApiRestService getServiceRoute(Context context){
        return ClientRetrofit.getClientWithInterceptor(URL_ROUTE,context).create(RouteApiRestService.class);
    }

    public static AppUserHasRouteRestService getServiceAppUserHasRoute(Context context){
        return ClientRetrofit.getClientWithInterceptor(URL_APPUSERHASROUTE,context).create(AppUserHasRouteRestService.class);
    }

    public static AuthRestService getAuthService(){
        return ClientRetrofit.getClient(URL_AUTH).create(AuthRestService.class);
    }

    public static StoredDocumentRestService getStoredDocumentService(){
        return ClientRetrofit.getClient(URL_STORED_DOCUMENT).create(StoredDocumentRestService.class);
    }


}
