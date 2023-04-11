package com.Tesis.bicycle.Service.ApiRest;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface StoredDocumentRestService {

    @POST("./")
    @Multipart
    Call<Long> save(@Part MultipartBody.Part file, @Part("name") RequestBody requestBody) ;
}
