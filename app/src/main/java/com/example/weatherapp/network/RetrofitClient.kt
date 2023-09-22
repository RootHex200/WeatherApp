package com.example.weatherapp.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private  var retrofit:Retrofit?=null
    private  val gson=GsonBuilder().setLenient().create()
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    val client:Retrofit
        get() {
            if(retrofit==null){
                synchronized(Retrofit::class.java){
                    if(retrofit==null){
                        val httpClient=OkHttpClient.Builder()
                            .addInterceptor(QueryParameterAddInterceptor())

                        val client = httpClient.build()

                        retrofit = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(client)
                            .build()
                    }
                }
            }
            return retrofit!!
        }
}




















