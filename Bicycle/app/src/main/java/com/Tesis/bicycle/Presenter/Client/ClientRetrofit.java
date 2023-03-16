package com.Tesis.bicycle.Presenter.Client;

import android.content.Context;
import android.os.Build;

import com.Tesis.bicycle.BuildConfig;
import com.Tesis.bicycle.Converters.DateDeserializer;
import com.Tesis.bicycle.Converters.DateSerializer;
import com.Tesis.bicycle.Converters.ListOfGeoPointDeserializer;
import com.Tesis.bicycle.Converters.ListOfGeoPointSerializer;
import com.Tesis.bicycle.Converters.LocalTimeDeserializer;
import com.Tesis.bicycle.Converters.LocalTimeSerializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientRetrofit {

    public static Retrofit getClient(String url,Context context){
      //  TokenAuthenticator tokenAuthenticator=new TokenAuthenticator(context);
        OkHttpClient httpClient = new  OkHttpClient.Builder()
//                .authenticator(new TokenAuthenticator(context))
                .addInterceptor(new AccessTokenInterceptor(context))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();



        GsonBuilder gsonBuilder = new GsonBuilder();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
            gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer());
            gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
            gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
           // gsonBuilder.registerTypeAdapter(List.class,new ListOfGeoPointDeserializer());
            gsonBuilder.registerTypeAdapter(List.class,new ListOfGeoPointSerializer());
        }



        Gson gson = gsonBuilder.setPrettyPrinting().create();
        Retrofit retrofit= new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient).build();
        return retrofit;
    }

}
