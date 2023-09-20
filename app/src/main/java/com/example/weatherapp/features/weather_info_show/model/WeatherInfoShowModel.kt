package com.example.weatherapp.features.weather_info_show.model

import com.example.weatherapp.common.RequestCompleteListener
import com.example.weatherapp.features.weather_info_show.model.data_class.City
import com.example.weatherapp.features.weather_info_show.model.data_class.WeatherDataModel
import javax.security.auth.callback.Callback

interface WeatherInfoShowModel {
    fun getCityList(callback: RequestCompleteListener<MutableList<City>>)

    fun getWeatherInfo(cityId:Int,callback: RequestCompleteListener<WeatherDataModel>)
}