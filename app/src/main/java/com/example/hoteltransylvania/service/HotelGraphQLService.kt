package com.example.hoteltransylvania.service

import com.example.hoteltransylvania.data.GraphQLRequest
import com.example.hoteltransylvania.data.HotelResponseWrapper
import com.example.hoteltransylvania.data.ReserveHotelResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface HotelGraphQLService {

    @Headers("Content-Type: application/json")
    @GET("graphql/getHotels")  // Replace with your actual endpoint
    fun getHotels(
        @Body request: GraphQLRequest
    ): Call<HotelResponseWrapper>

    @POST("graphql/reserveHotel")
    suspend fun reserveHotel(@Body body: GraphQLRequest): Response<ReserveHotelResponse>
}


