/*
 * *
 *  * Created by Salman Saleem on 6/21/18 1:44 AM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 6/21/18 1:44 AM
 *
 */

package com.salman.appnews.api.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.salman.appnews.entity.weather.weatherResponse;
import com.salman.appnews.entity.weather.weatherResponseCurrent;

/**
 * Created by Salman Saleem on 14/02/2017.
 */

public interface apiWeather {
    @GET("forecast/daily")
    Call<weatherResponse> getFiveDayWeather(@Query("lat") String lat,@Query("lon") String lon,@Query("mode") String mode,@Query("appid") String appid,@Query("units") String units);
    @GET("weather")
    Call<weatherResponseCurrent> getCurrentWeather(@Query("lat") String lat,@Query("lon") String lon,@Query("mode") String mode,@Query("appid") String appid,@Query("units") String units);


}

