package com.Tesis.bicycle.Presenter.Client;

import android.os.Build;

import com.Tesis.bicycle.Converters.LocalDateDeserializer;
import com.Tesis.bicycle.Converters.LocalDateSerializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientRetrofit {

    public static Retrofit getClient(String url){
        OkHttpClient httpClient = new  OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
//        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class,new LocalDateSerializer());
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        }
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        Retrofit retrofit= new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient).build();
        return retrofit;
    }

    public static Gson gsonWithDate() {
        final GsonBuilder builder = new GsonBuilder();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                @Override
                public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                   String aux=p.getValueAsString();
                    return LocalDate.parse(p.getValueAsString());
                }



            });
        }
        return builder.create();
    }

}
