package com.example.hoteltransylvania.service

import com.example.hoteltransylvania.data.GraphQLRequest
import com.example.hoteltransylvania.data.HotelResponseWrapper
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Call

interface HotelGraphQLService {
    @Headers("Content-Type: application/json")
    @POST("graphql")  // Replace with your actual endpoint
    fun getHotels(
        @Body request: GraphQLRequest
    ): Call<HotelResponseWrapper>
}
