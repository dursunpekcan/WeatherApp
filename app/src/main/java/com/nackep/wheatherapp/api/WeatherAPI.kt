package com.nackep.wheatherapp.api

import com.nackep.wheatherapp.activity.latitude
import com.nackep.wheatherapp.activity.longitude
import com.nackep.wheatherapp.model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.DuplicateFormatFlagsException


interface WeatherAPI {


    @GET("forecast.json?key=YOUR_API_KEY")
    fun getData(
        @Query("q") q: String = "$latitude,$longitude"

    ): Call<WeatherModel>

    @GET("forecast.json?key=YOUR_API_KEY")
    fun getDataByDays(
        @Query("q") q: String = "$latitude,$longitude", @Query("days") days: String
    ):Call<WeatherModel>


}