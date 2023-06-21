package com.Tesis.bicycle.Presenter;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.Tesis.bicycle.Converters.ConvertersDate;
import com.Tesis.bicycle.Model.Room.RefreshToken;
import com.Tesis.bicycle.Service.Room.RefreshTokenService;

@Database(entities = {RefreshToken.class}, version =9,exportSchema = true)
@TypeConverters({ConvertersDate.class})
public abstract class AppDataBase extends RoomDatabase {

    public abstract RefreshTokenService refreshTokenService();


}
