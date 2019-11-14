package com.wolfsoft.firebasenotification.notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA4oX6jVc:APA91bH6hrqT2uF9vzOdLIUaex42o774mtsf_0QerbzMMBm7iWrXDCjJq8u8RaFsLAtiB6rlcGGYl1G1tG28OwRHtRYXB8iRxMYhxm-MYCTzdlguJp2x1oWMCXhGma36aqbF3hNIpUiH"
    })

    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}
