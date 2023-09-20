package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.example.weatherapp.features.weather_info_show.model.WeatherInfoShowModel
import com.example.weatherapp.features.weather_info_show.model.WeatherInfoShowModelImpl
import com.example.weatherapp.features.weather_info_show.model.data_class.City
import com.example.weatherapp.features.weather_info_show.model.data_class.WeatherDataModel
import com.example.weatherapp.features.weather_info_show.presenter.WeatherShowPresenter
import com.example.weatherapp.features.weather_info_show.presenter.WeatherShowPresenterImpl
import com.example.weatherapp.features.weather_info_show.view.MainActivityView
import com.example.weatherapp.utils.convertListCityNames

class MainActivity : AppCompatActivity(),MainActivityView {
    lateinit var weatherbutton:Button
    lateinit var spinner:Spinner
    private lateinit var model:WeatherInfoShowModel
    private lateinit var presenter:WeatherShowPresenter
    lateinit var  cityList:MutableList<City>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weatherbutton=findViewById<Button>(R.id.check_weather_button);
        spinner=findViewById<Spinner>(R.id.spinner)

        model=WeatherInfoShowModelImpl(this)
        presenter=WeatherShowPresenterImpl(this,model)

        presenter.fetchCityList()
    }

    override fun progressBar(visibility: Int) {
        TODO("Not yet implemented")
    }

    override fun fetchSuccessWeatherInfo(data: WeatherDataModel) {
        TODO("Not yet implemented")
    }

    override fun fetchFailedWeatherInfo(error: String) {
        TODO("Not yet implemented")
    }

    override fun fetchSuccessCityData(data: MutableList<City>) {
        this.cityList=data
     var adapter=ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            data.convertListCityNames()
        )

        spinner.adapter=adapter
    }

    override fun fetchFailedCityData(error: String) {
        Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_LONG).show();
    }
}