package com.example.weatherapp.features.weather_info_show.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.R
import com.example.weatherapp.features.weather_info_show.model.WeatherInfoShowModel
import com.example.weatherapp.features.weather_info_show.model.WeatherInfoShowModelImpl
import com.example.weatherapp.features.weather_info_show.model.data_class.City
import com.example.weatherapp.features.weather_info_show.model.data_class.WeatherDataModel
import com.example.weatherapp.features.weather_info_show.viewmodel.WeatherShowViewModel
import com.example.weatherapp.utils.convertListCityNames

class MainActivity : AppCompatActivity() {
    lateinit var weatherbutton:Button
    lateinit var spinner:Spinner
    private lateinit var model:WeatherInfoShowModel
    lateinit var  cityList:MutableList<City>
    lateinit var basic_show_view:LinearLayout
    lateinit var sunrise_sunset_view:LinearLayout
    lateinit var progressbar:ProgressBar
    lateinit var errorMessage:TextView
    lateinit var viewmodel:WeatherShowViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weatherbutton=findViewById<Button>(R.id.check_weather_button);
        spinner=findViewById<Spinner>(R.id.spinner)
        basic_show_view=findViewById(R.id.basic_data_view)
        sunrise_sunset_view=findViewById(R.id.sunrise_sunset_view)
        progressbar=findViewById(R.id.progressBar)
        errorMessage=findViewById<TextView>(R.id.tv_error_message)


        model=WeatherInfoShowModelImpl(this)
        viewmodel= ViewModelProviders.of(this).get(WeatherShowViewModel::class.java)

        viewmodel.fetchCityList(model)


        weatherbutton.setOnClickListener {

            var cityId:Int = cityList[spinner.selectedItemPosition].id

            viewmodel.fetchWeatherInfo(cityId,model)

        }


        setLiveDataListener()

    }

    fun setLiveDataListener(){
        viewmodel.cityLiveData.observe(this,object:Observer<MutableList<City>>{
            override fun onChanged(t: MutableList<City>) {
                fetchSuccessCityData(t)
            }

        })


        viewmodel.progressbarLivedata.observe(this,object :Observer<Boolean>{
            override fun onChanged(t: Boolean) {
                if(t==true){
                    progressbar.visibility=View.VISIBLE
                }else{
                    progressbar.visibility=View.GONE
                }
            }

        })

        viewmodel.weatherInfoLiveData.observe(this,object :Observer<WeatherDataModel>{
            override fun onChanged(t: WeatherDataModel) {
                fetchSuccessWeatherInfo(t)
            }

        })

        viewmodel.weatherFailed.observe(this,object:Observer<String>{
            override fun onChanged(t: String) {
                fetchFailedWeatherInfo(t)
            }

        })
        viewmodel.cityFailed.observe(this,object:Observer<String>{
            override fun onChanged(t: String) {
                fetchFailedCityData(t)
            }

        })

    }

     fun fetchSuccessWeatherInfo(data: WeatherDataModel) {
        Log.d("MainActivity",""+data)
        basic_show_view.visibility=View.VISIBLE
        sunrise_sunset_view.visibility=View.VISIBLE

        val degree:TextView=findViewById(R.id.degree)
        val city_name:TextView=findViewById(R.id.city_name)
        val present_date:TextView=findViewById(R.id.present_date)
        val value_sunrise:TextView=findViewById(R.id.value_sunrise)
        val value_sunset:TextView=findViewById(R.id.value_sunset)


        degree.text=data.temperature
        city_name.text=data.cityAndCountry
        present_date.text=data.dateTime
        value_sunrise.text=data.sunrise
        value_sunset.text=data.sunset
    }

     fun fetchFailedWeatherInfo(error: String) {
        errorMessage.visibility=View.VISIBLE
        errorMessage.text=error.toString()

    }

     fun fetchSuccessCityData(data: MutableList<City>) {
        this.cityList=data
     var adapter=ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            data.convertListCityNames()
        )

        spinner.adapter=adapter
    }

     fun fetchFailedCityData(error: String) {
        Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_LONG).show();
    }
}