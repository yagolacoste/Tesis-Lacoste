package com.Tesis.bicycle.Service.Room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;
import com.Tesis.bicycle.Model.Room.RefreshToken;

import java.util.List;

@Dao
public interface RefreshTokenService {

    @Query("Select Count(*) from refresh_token")
    int count();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(RefreshToken refreshToken);

    @Update
    void update(RefreshToken refreshToken);

    @Query("SELECT * FROM refresh_token ")
    List<RefreshTokenDto> getAll();

    @Query("DELETE FROM refresh_token")
    void delete();

    @Query("SELECT * FROM refresh_token LIMIT 1")
    RefreshTokenDto getFirst();
}
