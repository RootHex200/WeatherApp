package com.example.weatherapp.features.weather_info_show.model

import android.content.Context
import android.util.Log
import com.example.weatherapp.common.RequestCompleteListener
import com.example.weatherapp.features.weather_info_show.model.data_class.City
import com.example.weatherapp.features.weather_info_show.model.data_class.WeatherDataModel
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.IOException

class WeatherInfoShowModelImpl(
    private val context:Context
) :WeatherInfoShowModel{
    override fun getCityList(callback: RequestCompleteListener<MutableList<City>>) {
        try {
            var stream=context.assets.open("city_list.json")
            var size=stream.available()
            var buffer=ByteArray(size)
            stream.read(buffer)
            stream.close()

            var jsonContent=String(buffer)
            Log.d("data",jsonContent)
            var listtype = object : TypeToken<ArrayList<City>>() {}.type
            var gson=GsonBuilder().create()
            var cityData:MutableList<City> = gson.fromJson(jsonContent,listtype)
            Log.d("data",""+cityData)
            callback.onSuccess(cityData)

        }catch (e:IOException){
            e.printStackTrace()
            Log.d("CITY",e.toString())
            callback.onFailed("something wrong!!")

        }
    }

    override fun getWeatherInfo(cityId: Int, callback: RequestCompleteListener<WeatherDataModel>) {
        TODO("Not yet implemented")
    }
}