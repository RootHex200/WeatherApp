package com.example.weatherapp.features.weather_info_show.model

import android.content.Context
import android.util.Log
import com.example.weatherapp.common.RequestCompleteListener
import com.example.weatherapp.features.weather_info_show.model.data_class.City
import com.example.weatherapp.features.weather_info_show.model.data_class.WeatherDataModel
import com.example.weatherapp.network.ApiInterface
import com.example.weatherapp.network.RetrofitClient
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hellohasan.weatherforecast.features.weather_info_show.model.data_class.WeatherInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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


    override fun getWeatherInfo(cityId: Int, callback: RequestCompleteListener<WeatherInfoResponse>) {
        val apiInterface: ApiInterface = RetrofitClient.client.create(ApiInterface::class.java)
        val call: Call<WeatherInfoResponse> = apiInterface.callAPIForWeatherData(cityId)

        call.enqueue(object : Callback<WeatherInfoResponse> {

            override fun onResponse(call: Call<WeatherInfoResponse>, response: Response<WeatherInfoResponse>) {
                if (response.body() != null)
                    callback.onSuccess(response.body()!!) //let presenter know the weather information data
                else
                    callback.onFailed(response.message()) //let presenter know about failure
            }

            override fun onFailure(call: Call<WeatherInfoResponse>, t: Throwable) {
                callback.onFailed(t.localizedMessage!!) //let presenter know about failure
            }

        })
    }
}