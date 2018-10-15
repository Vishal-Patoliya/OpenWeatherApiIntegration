package com.gitprofile.rest

import com.gitprofile.utils.Constants
import com.openweatherapp.model.TodayForecastModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by vishal on 9/14/2018.
 * Use for api interface for call different Apis.
 */
interface ApiInterface {

    @GET(Constants.API_PARMAS.weather)
    fun getTodayWeatherInfo(@Query(Constants.API_PARMAS.lat) lattitide: String,
                       @Query(Constants.API_PARMAS.lon) longitude: String,
                       @Query(Constants.API_PARMAS.appId) appid: String,
                       @Query(Constants.API_PARMAS.units) unites: String): Call<TodayForecastModel>

    companion object Factory {
        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.URL.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }

}