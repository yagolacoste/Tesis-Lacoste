package com.Tesis.bicycle.Repository;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Tesis.bicycle.Activity.TrackingDetailActivity;
import com.Tesis.bicycle.Presenter.ApiRestConecction;
import com.Tesis.bicycle.Service.ApiRest.AuthRestService;
import com.Tesis.bicycle.Service.ApiRest.StoredDocumentRestService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoredDocumentRepository {
    private static StoredDocumentRepository repository;
    private final StoredDocumentRestService storedDocumentRestService;


    public StoredDocumentRepository() {
        this.storedDocumentRestService = ApiRestConecction.getStoredDocumentService();
    }

    public static StoredDocumentRepository getInstance(){
        if(repository==null){
            repository=new StoredDocumentRepository();
        }
        return repository;
    }

    public LiveData<Long> save(MultipartBody.Part part, RequestBody requestBody){
        final MutableLiveData<Long> mld = new MutableLiveData<>();
        this.storedDocumentRestService.save(part,requestBody).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                mld.setValue(response.body());
                System.out.println("foto agregada correctamente");
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                System.out.println("se produjo error al insertar foto"+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
