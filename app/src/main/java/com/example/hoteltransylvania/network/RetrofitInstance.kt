package com.example.hoteltransylvania.network

import com.example.hoteltransylvania.service.HotelGraphQLService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // The Retrofit instance, which is used to create the API service
    val apiService: HotelGraphQLService by lazy {
        Retrofit.Builder()
            .baseUrl("https://hotel-transylvania-d7aedza2c5fpgjhn.canadacentral-01.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HotelGraphQLService::class.java)
    }


}