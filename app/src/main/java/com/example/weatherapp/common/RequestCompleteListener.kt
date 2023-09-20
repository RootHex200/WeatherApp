package com.example.weatherapp.common

interface RequestCompleteListener<T> {

    fun onSuccess(data:T)
    fun onFailed(error:String)
}