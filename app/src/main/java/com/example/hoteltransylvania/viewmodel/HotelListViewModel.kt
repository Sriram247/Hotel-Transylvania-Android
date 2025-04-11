package com.example.hoteltransylvania.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hoteltransylvania.data.GraphQLRequest
import com.example.hoteltransylvania.data.Hotel
import com.example.hoteltransylvania.network.RetrofitInstance
import com.example.hoteltransylvania.repository.GraphQLQueries
import com.example.hoteltransylvania.data.HotelResponseWrapper
import kotlinx.coroutines.launch
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
                    val hotelList = response.body()?.data?.getAllHotels ?: emptyList()
                    hotels.postValue(hotelList)
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
