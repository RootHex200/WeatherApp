package com.example.weatherapp

import android.opengl.Visibility
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
import com.example.weatherapp.features.weather_info_show.model.WeatherInfoShowModel
import com.example.weatherapp.features.weather_info_show.model.WeatherInfoShowModelImpl
import com.example.weatherapp.features.weather_info_show.model.data_class.City
import com.example.weatherapp.features.weather_info_show.model.data_class.WeatherDataModel
import com.example.weatherapp.features.weather_info_show.presenter.WeatherShowPresenter
import com.example.weatherapp.features.weather_info_show.presenter.WeatherShowPresenterImpl
import com.example.weatherapp.features.weather_info_show.view.MainActivityView
import com.example.weatherapp.utils.convertListCityNames
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(),MainActivityView {
    lateinit var weatherbutton:Button
    lateinit var spinner:Spinner
    private lateinit var model:WeatherInfoShowModel
    private lateinit var presenter:WeatherShowPresenter
    lateinit var  cityList:MutableList<City>
    lateinit var basic_show_view:LinearLayout
    lateinit var sunrise_sunset_view:LinearLayout
    lateinit var progressbar:ProgressBar
    lateinit var errorMessage:TextView
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
        presenter=WeatherShowPresenterImpl(this,model)

        presenter.fetchCityList()


        weatherbutton.setOnClickListener {
            basic_show_view.visibility=View.GONE
            sunrise_sunset_view.visibility=View.GONE

            var cityId:Int = cityList[spinner.selectedItemPosition].id

            presenter.fetchWeatherInfo(cityId)

        }

    }

    override fun progressBar(visibility: Int) {
        progressbar.visibility=visibility
    }

    override fun fetchSuccessWeatherInfo(data: WeatherDataModel) {
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

    override fun fetchFailedWeatherInfo(error: String) {
        errorMessage.visibility=View.VISIBLE
        errorMessage.text=error.toString()

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