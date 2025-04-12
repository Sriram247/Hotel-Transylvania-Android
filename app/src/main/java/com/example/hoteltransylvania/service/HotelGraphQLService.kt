package com.example.hoteltransylvania.service

import com.example.hoteltransylvania.data.GraphQLRequest
import com.example.hoteltransylvania.data.HotelResponseWrapper
import com.example.hoteltransylvania.data.ReserveHotelResponse
import com.example.hoteltransylvania.data.ReviewResponseWrapper
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.Response

interface HotelGraphQLService {

    @POST("graphql")
    @Headers("Content-Type: application/json")
    fun getHotels(
        @Body request: GraphQLRequest
    ): Call<HotelResponseWrapper>


    @POST("graphql")
    @Headers("Content-Type: application/json")
    suspend fun reserveHotel(
        @Body body: GraphQLRequest
    ): Response<ReserveHotelResponse>


    @Headers("Content-Type: application/json")
    @POST("graphql")
    suspend fun getReviews(@Body body: Map<String, String>): Response<ReviewResponseWrapper>

}



