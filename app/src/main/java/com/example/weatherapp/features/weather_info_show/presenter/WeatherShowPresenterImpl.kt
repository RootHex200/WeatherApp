package com.example.weatherapp.features.weather_info_show.presenter

import android.util.Log
import android.view.View
import com.example.weatherapp.common.RequestCompleteListener
import com.example.weatherapp.features.weather_info_show.model.WeatherInfoShowModel
import com.example.weatherapp.features.weather_info_show.model.data_class.City
import com.example.weatherapp.features.weather_info_show.model.data_class.WeatherDataModel
import com.example.weatherapp.features.weather_info_show.view.MainActivityView
import com.example.weatherapp.utils.kelvinToCelsius
import com.example.weatherapp.utils.unixTimestampToDateTimeString
import com.example.weatherapp.utils.unixTimestampToTimeString
import com.hellohasan.weatherforecast.features.weather_info_show.model.data_class.WeatherInfoResponse

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
        view?.progressBar(View.VISIBLE) // let view know about progress bar visibility

        // call model's method for weather information
        model.getWeatherInfo(cityId, object : RequestCompleteListener<WeatherInfoResponse> {

            // if model successfully fetch the data from 'somewhere', this method will be called
            override fun onSuccess(data: WeatherInfoResponse) {
                Log.d("DATA",data.toString())
                view?.progressBar(View.GONE) // let view know about progress bar visibility

                // data formatting to show on UI
                val weatherDataModel = WeatherDataModel(
                    dateTime = data.dt.unixTimestampToDateTimeString(),
                    temperature = data.main.temp.kelvinToCelsius().toString(),
                    cityAndCountry = "${data.name}, ${data.sys.country}",
                    weatherConditionIconUrl = "http://openweathermap.org/img/w/${data.weather[0].icon}.png",
                    weatherConditionIconDescription = data.weather[0].description,
                    humidity = "${data.main.humidity}%",
                    pressure = "${data.main.pressure} mBar",
                    visibility = "${data.visibility/1000.0} KM",
                    sunrise = data.sys.sunrise.unixTimestampToTimeString(),
                    sunset = data.sys.sunset.unixTimestampToTimeString()
                )

                view?.fetchSuccessWeatherInfo(weatherDataModel) //let view know the formatted weather data
            }

            // if model failed to fetch data then this method will be called
            override fun onFailed(errorMessage: String) {
                view?.progressBar(View.GONE) // let view know about progress bar visibility

                view?.fetchFailedWeatherInfo(errorMessage) //let view know about failure
            }
        })
    }
}