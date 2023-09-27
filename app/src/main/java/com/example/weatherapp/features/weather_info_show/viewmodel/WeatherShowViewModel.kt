package com.example.weatherapp.features.weather_info_show.viewmodel


import androidx.lifecycle.ViewModel
import com.example.weatherapp.common.RequestCompleteListener
import com.example.weatherapp.features.weather_info_show.model.WeatherInfoShowModel
import com.example.weatherapp.features.weather_info_show.model.data_class.City
import com.example.weatherapp.features.weather_info_show.model.data_class.WeatherDataModel
import com.example.weatherapp.utils.kelvinToCelsius
import com.example.weatherapp.utils.unixTimestampToDateTimeString
import com.example.weatherapp.utils.unixTimestampToTimeString
import com.hellohasan.weatherforecast.features.weather_info_show.model.data_class.WeatherInfoResponse
import io.reactivex.rxjava3.subjects.BehaviorSubject

class WeatherShowViewModel():ViewModel() {

    val cityRxData =BehaviorSubject.create<MutableList<City>>()
    val cityRxFailed=BehaviorSubject.create<String>()
    val weatherInfoRxData=BehaviorSubject.create<WeatherDataModel>()
    val weatherFailedRxData=BehaviorSubject.create<String>()
    val progressbarRxData=BehaviorSubject.create<Boolean>()

     fun fetchCityList(model: WeatherInfoShowModel) {
        model.getCityList(object : RequestCompleteListener<MutableList<City>>{
            override fun onSuccess(data: MutableList<City>) {
                cityRxData.onNext(data)
            }
            override fun onFailed(error: String) {
                cityRxFailed.onNext(error)
            }

        })
    }

     fun fetchWeatherInfo(cityId: Int,model:WeatherInfoShowModel) {
         progressbarRxData.onNext(true)

        model.getWeatherInfo(cityId, object : RequestCompleteListener<WeatherInfoResponse> {

            override fun onSuccess(data: WeatherInfoResponse) {
                progressbarRxData.onNext(false)
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

                weatherInfoRxData.onNext(weatherDataModel)
            }

            override fun onFailed(error: String) {
                progressbarRxData.onNext(false)
                weatherFailedRxData.onNext(error)
            }
        })
    }
}