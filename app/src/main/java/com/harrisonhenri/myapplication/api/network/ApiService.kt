package com.harrisonhenri.myapplication.api.network

import com.harrisonhenri.myapplication.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

interface ApiService {
        @GET("5/company")
        suspend fun getApiPayload(): String
}

object Api {
        val apiService = retrofit.create(ApiService::class.java)
}