package com.example.weatherapp.features.weather_info_show.presenter

import com.example.weatherapp.common.RequestCompleteListener
import com.example.weatherapp.features.weather_info_show.model.WeatherInfoShowModel
import com.example.weatherapp.features.weather_info_show.model.data_class.City
import com.example.weatherapp.features.weather_info_show.view.MainActivityView

class WeatherShowPresenterImpl(
    private var view:MainActivityView?,
    private var model:WeatherInfoShowModel,

): WeatherShowPresenter {
    override fun fetchCityList() {
        model.getCityList(object : RequestCompleteListener<MutableList<City>>{
            override fun onSuccess(data: MutableList<City>) {
               view?.fetchSuccessCityData(data)
            }
            override fun onFailed(error: String) {
                view?.fetchFailedCityData(error)
            }

        })
    }

    override fun fetchWeatherInfo(cityId: Int) {
        TODO("Not yet implemented")
    }
}