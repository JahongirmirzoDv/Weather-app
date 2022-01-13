package com.example.weatherapptest.retrofit

import com.example.weatherapp.models.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    fun getData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String,
        @Query("appid") appId: String
    ): Call<Data>
}