package com.Tesis.bicycle.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Tesis.bicycle.Dto.ApiRest.FriendshipRequest.FriendshipRequestDto;
import com.Tesis.bicycle.Repository.FriendshipRequestRepository;

public class FriendshipRequestViewModel extends AndroidViewModel {

    private final FriendshipRequestRepository repository;

    public FriendshipRequestViewModel(@NonNull Application application) {
        super(application);
        repository=FriendshipRequestRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<Boolean> sendRequest(FriendshipRequestDto friendshipRequestDto){
        return repository.sendRequest(friendshipRequestDto);
    }

    public LiveData<Boolean> acceptedRequest(FriendshipRequestDto friendshipRequestDto){
        return repository.acceptedRequest(friendshipRequestDto);
    }

    public LiveData<Boolean> rejectedRequest(FriendshipRequestDto friendshipRequestDto){
        return repository.rejectedRequest(friendshipRequestDto);
    }
}
