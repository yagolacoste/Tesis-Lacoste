package com.Tesis.bicycle.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Service.ApiRest.StoredDocumentApiService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoredDocumentApiRepository {
    private static StoredDocumentApiRepository repository;
    private final StoredDocumentApiService storedDocumentApiService;


    public StoredDocumentApiRepository() {
        this.storedDocumentApiService = ApiRestConnection.getStoredDocumentService();
    }

    public static StoredDocumentApiRepository getInstance(){
        if(repository==null){
            repository=new StoredDocumentApiRepository();
        }
        return repository;
    }

    public LiveData<Long> save(MultipartBody.Part part, RequestBody requestBody){
        final MutableLiveData<Long> mld = new MutableLiveData<>();
        this.storedDocumentApiService.save(part,requestBody).enqueue(new Callback<Long>() {
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

    public LiveData<Response> download(String fileName){
        final MutableLiveData<Response> mld = new MutableLiveData<>();
        this.storedDocumentApiService.download(fileName).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, Response<Response> response) {
                mld.setValue(response.body());
                System.out.println("foto descargada correctamente");
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                System.out.println("se produjo error al descargar foto"+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
