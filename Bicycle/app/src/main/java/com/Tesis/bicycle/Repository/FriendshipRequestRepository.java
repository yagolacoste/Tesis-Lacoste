package com.Tesis.bicycle.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Tesis.bicycle.Dto.ApiRest.FriendshipRequest.FriendshipRequestDto;
import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Service.ApiRest.BattleApiService;
import com.Tesis.bicycle.Service.ApiRest.FriendshipRequestApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendshipRequestRepository {
    private static FriendshipRequestRepository repository;

    private final FriendshipRequestApiService friendshipRequestApiService;


    public FriendshipRequestRepository(Context context) {
        this.friendshipRequestApiService = ApiRestConnection.getFriendshipRequestService(context);
    }

    public static FriendshipRequestRepository getInstance(Context context) {
        if (repository == null) {
            repository = new FriendshipRequestRepository(context);
        }
        return repository;
    }

    public LiveData<Boolean> sendRequest( FriendshipRequestDto friendshipRequestDto){
        final MutableLiveData<Boolean> mld=new MutableLiveData<>();
        friendshipRequestApiService.sendRequest(friendshipRequestDto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mld.setValue(true);
                }
                else mld.setValue(false);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return mld;
    }

    public LiveData<Boolean> acceptedRequest( FriendshipRequestDto friendshipRequestDto){
        final MutableLiveData<Boolean> mld=new MutableLiveData<>();
        friendshipRequestApiService.acceptedRequest(friendshipRequestDto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mld.setValue(true);
                }
                else mld.setValue(false);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return mld;
    }

    public LiveData<Boolean> rejectedRequest( FriendshipRequestDto friendshipRequestDto){
        final MutableLiveData<Boolean> mld=new MutableLiveData<>();
        friendshipRequestApiService.rejectedRequest(friendshipRequestDto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mld.setValue(true);
                }
                else mld.setValue(false);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return mld;
    }


}
