package com.Tesis.bicycle.Service.ApiRest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface StoredDocumentApiService {

    @POST("./")
    @Multipart
    Call<Long> save(@Part MultipartBody.Part file, @Part("name") RequestBody requestBody) ;


    @GET("/download/{fileName}")
    Call<Response>download(@Path("fileName") String fileName);
}
