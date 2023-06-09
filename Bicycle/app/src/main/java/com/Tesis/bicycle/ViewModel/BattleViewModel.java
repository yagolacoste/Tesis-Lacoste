package com.Tesis.bicycle.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Tesis.bicycle.Dto.ApiRest.Battle.NewBattleDto;
import com.Tesis.bicycle.Dto.ApiRest.Battle.BattleDto;
import com.Tesis.bicycle.Repository.BattleApiRepository;

import java.io.IOException;
import java.util.List;

public class BattleViewModel extends AndroidViewModel {

    private final BattleApiRepository repository;


    public BattleViewModel(@NonNull Application application) {
        super(application);
        repository= BattleApiRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<BattleDto>> getBattles() throws IOException {
        return repository.getBattles();
    }

    public LiveData<BattleDto> getById(Long id) {
        return repository.getById(id);
    }

    public LiveData<Void> addBattle(NewBattleDto battleDto) throws IOException {
       return repository.addBattle(battleDto);
    }

    public LiveData<Boolean> updateBattle(Long appuser, Long battleId, String statisticsId){
        return repository.updateBattle(appuser, battleId, statisticsId);
    }

}
