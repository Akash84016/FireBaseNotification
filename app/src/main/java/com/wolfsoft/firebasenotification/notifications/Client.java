package com.wolfsoft.firebasenotification.notifications;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class Client {

    private static Retrofit retrofit = null;

    static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://fcm.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
