package com.Tesis.bicycle.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Tesis.bicycle.Repository.StoredDocumentApiRepository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class StoredDocumentViewModel extends AndroidViewModel {

    private final StoredDocumentApiRepository repository;

    public StoredDocumentViewModel(@NonNull Application application) {
        super(application);
        this.repository= StoredDocumentApiRepository.getInstance();
    }

    public LiveData<Long> save(MultipartBody.Part file, RequestBody requestBody){
         return repository.save(file,requestBody);
    }

    public LiveData<Response> download(String fileName){
        return repository.download(fileName);
    }
}
