package com.example.pfc.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // IMPORTANTE: 10.0.2.2 es el "localhost" para el emulador de Android
    private static final String BASE_URL = "http://10.0.2.2:8000/api/";
    private static Retrofit retrofit = null;

    public static DjangoApi getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Traduce el JSON automáticamente
                    .build();
        }
        return retrofit.create(DjangoApi.class);
    }
}