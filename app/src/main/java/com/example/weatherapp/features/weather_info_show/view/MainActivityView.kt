package com.example.weatherapp.features.weather_info_show.view

import com.example.weatherapp.features.weather_info_show.model.data_class.City
import com.example.weatherapp.features.weather_info_show.model.data_class.WeatherDataModel

interface MainActivityView {
    fun progressBar(visibility:Int);

    fun fetchSuccessWeatherInfo(data:WeatherDataModel)

    fun fetchFailedWeatherInfo(error:String)

    fun fetchSuccessCityData(data:MutableList<City>)

    fun  fetchFailedCityData(error:String)
}