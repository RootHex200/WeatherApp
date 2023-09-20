package com.example.weatherapp.network
import com.example.weatherapp.features.weather_info_show.model.data_class.WeatherDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
interface ApiInterface {
    @GET("weather")
    fun callAPIForWeatherData(@Query("id")cityId:Int):Call<WeatherDataModel>
}