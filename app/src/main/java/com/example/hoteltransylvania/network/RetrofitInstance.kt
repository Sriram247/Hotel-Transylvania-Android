package com.example.hoteltransylvania.network

import com.example.hoteltransylvania.service.HotelGraphQLService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // The Retrofit instance, which is used to create the API service
    val apiService: HotelGraphQLService by lazy {
        Retrofit.Builder()
            .baseUrl("https://your-backend-url.com/") // Replace with your backend URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HotelGraphQLService::class.java)
    }


}