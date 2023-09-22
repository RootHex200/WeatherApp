package com.example.weatherapp.network

import okhttp3.Interceptor
import okhttp3.Response

class QueryParameterAddInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url=chain.request().url().newBuilder()
            .addQueryParameter("appid","47324804eae336e46db89dea0deae5ca")
            .build()
        val request=chain.request().newBuilder().url(url).build()
        return  chain.proceed(request)
    }
}































