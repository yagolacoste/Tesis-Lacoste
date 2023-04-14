package com.Tesis.bicycle.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Tesis.bicycle.Repository.StoredDocumentRepository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class StoredDocumentViewModel extends AndroidViewModel {

    private final StoredDocumentRepository repository;

    public StoredDocumentViewModel(@NonNull Application application) {
        super(application);
        this.repository= StoredDocumentRepository.getInstance();
    }

    public LiveData<Long> save(MultipartBody.Part file, RequestBody requestBody){
         return repository.save(file,requestBody);
    }
}
