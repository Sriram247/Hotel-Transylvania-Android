package com.example.hoteltransylvania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.hoteltransylvania.data.GraphQLRequest
import com.example.hoteltransylvania.data.Hotel
import com.example.hoteltransylvania.network.RetrofitInstance
import com.example.hoteltransylvania.repository.GraphQLQueries
import com.example.hoteltransylvania.data.HotelResponseWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelListViewModel(application: Application) : AndroidViewModel(application) {

    // LiveData for holding hotel data
    val hotels = MutableLiveData<List<Hotel>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun fetchHotels() {
        // Show loading
        loading.postValue(true)

        // Build the GraphQL query
        val query = GraphQLQueries.SEARCH_HOTELS_QUERY

        val request = GraphQLRequest(query = query)

        // Make the GraphQL request
        RetrofitInstance.apiService.getHotels(request).enqueue(object : Callback<HotelResponseWrapper> {
            override fun onResponse(
                call: Call<HotelResponseWrapper>,
                response: Response<HotelResponseWrapper>
            ) {
                loading.postValue(false)

                if (response.isSuccessful) {
                    val hotelList = response.body()?.data?.getAllHotels?.map { hotel ->
                        if (hotel.imageUrl.isNullOrEmpty()) {
                            hotel.copy(imageUrl = "https://i.pinimg.com/736x/82/41/0d/82410d4c63371b01d1e71182ec07cde8.jpg")
                        } else {
                            hotel
                        }
                    } ?: emptyList()
                    hotels.postValue(hotelList)

                    // Save the hotelList to SharedPreferences
                    val sharedPreferences = getApplication<Application>().getSharedPreferences("HotelPrefs", Application.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    val gson = com.google.gson.Gson()
                    val hotelListJson = gson.toJson(hotelList)
                    editor.putString("hotelList", hotelListJson)
                    editor.apply()

                } else {
                    error.postValue("Error response: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<HotelResponseWrapper>, t: Throwable) {
                loading.postValue(false)
                error.postValue("Error - retrofit failure : ${t.localizedMessage}")
            }
        })
    }
}