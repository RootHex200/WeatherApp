package com.example.weatherapp.features.weather_info_show.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.common.RequestCompleteListener
import com.example.weatherapp.features.weather_info_show.model.WeatherInfoShowModel
import com.example.weatherapp.features.weather_info_show.model.data_class.City
import com.example.weatherapp.features.weather_info_show.model.data_class.WeatherDataModel
import com.example.weatherapp.utils.kelvinToCelsius
import com.example.weatherapp.utils.unixTimestampToDateTimeString
import com.example.weatherapp.utils.unixTimestampToTimeString
import com.hellohasan.weatherforecast.features.weather_info_show.model.data_class.WeatherInfoResponse

class WeatherShowViewModel():ViewModel() {

    val cityLiveData=MutableLiveData<MutableList<City>>()
    val cityFailed =MutableLiveData<String>()
    val weatherInfoLiveData=MutableLiveData<WeatherDataModel>()
    val weatherFailed=MutableLiveData<String>()
    val progressbarLivedata=MutableLiveData<Boolean>()
     fun fetchCityList(model: WeatherInfoShowModel) {
        model.getCityList(object : RequestCompleteListener<MutableList<City>>{
            override fun onSuccess(data: MutableList<City>) {
                cityLiveData.postValue(data)
            }
            override fun onFailed(error: String) {
                cityFailed.postValue(error)
            }

        })
    }

     fun fetchWeatherInfo(cityId: Int,model:WeatherInfoShowModel) {
         progressbarLivedata.postValue(true)

        model.getWeatherInfo(cityId, object : RequestCompleteListener<WeatherInfoResponse> {

            override fun onSuccess(data: WeatherInfoResponse) {
                Log.d("DATA",data.toString())
                progressbarLivedata.postValue(false)
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

            weatherInfoLiveData.postValue(weatherDataModel)
            }

            override fun onFailed(errorMessage: String) {
                progressbarLivedata.postValue(false)
//
                weatherFailed.postValue(errorMessage)
            }
        })
    }
}