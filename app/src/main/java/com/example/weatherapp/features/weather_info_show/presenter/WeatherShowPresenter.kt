package com.example.weatherapp.features.weather_info_show.presenter

interface WeatherShowPresenter {
    fun fetchCityList()

    fun fetchWeatherInfo(cityId:Int)
}