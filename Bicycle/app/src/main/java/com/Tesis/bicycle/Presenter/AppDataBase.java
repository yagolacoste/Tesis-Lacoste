package com.Tesis.bicycle.Presenter;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.Tesis.bicycle.Converters.ConvertersDate;
import com.Tesis.bicycle.Model.Room.RefreshToken;
import com.Tesis.bicycle.Model.Room.Routes;
import com.Tesis.bicycle.Service.Room.RefreshTokenService;
import com.Tesis.bicycle.Service.Room.RoutesService;
import com.Tesis.bicycle.Service.Room.TrackedService;

@Database(entities = {RefreshToken.class, Routes.class}, version =11,exportSchema = true)
@TypeConverters({ConvertersDate.class})
public abstract class AppDataBase extends RoomDatabase {

    public abstract RefreshTokenService refreshTokenService();

    public abstract RoutesService routesService();

    public abstract TrackedService trackedService();

}
