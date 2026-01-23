package com.example.interviewaicoach.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://openrouter.ai/api/v1/"

    fun getApiService() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(MyOkHttpClient().get())
        .build()
        .create(ApiService::class.java)

}