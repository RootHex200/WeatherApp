package com.example.weatherapp.utils

import com.example.weatherapp.features.weather_info_show.model.data_class.City

fun MutableList<City>.convertListCityNames():MutableList<String>{
    var cityNames:MutableList<String> = mutableListOf();

    for(city in this){
        cityNames.add(city.name)
    }
    return  cityNames
}