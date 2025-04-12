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
import com.example.hoteltransylvania.service.HotelGraphQLService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelListViewModel(application: Application) : AndroidViewModel(application) {

    // LiveData for holding hotel data
    val hotels = MutableLiveData<List<Hotel>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    private val _reviews = MutableStateFlow<List<String>>(emptyList())
    val reviews = _reviews.asStateFlow()

    private val _summary = MutableStateFlow<String>("")
    val summary = _summary.asStateFlow()

    private val _reviewLoading = MutableStateFlow(false)
    val reviewLoading = _reviewLoading.asStateFlow()

    private val _reviewError = MutableStateFlow<String?>(null)
    val reviewError = _reviewError.asStateFlow()


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

    fun fetchReviews(hotelName: String) {
        val query = GraphQLQueries.getReviewsQuery(hotelName)
        val request = GraphQLRequest(query = query)

        _reviewLoading.value = true
        _reviewError.value = null

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.getReviews(request)
                if (response.isSuccessful) {
                    val body = response.body()
                    _reviews.value = body?.data?.reviews ?: emptyList()
                    _summary.value = body?.data?.summary ?: ""
                } else {
                    _reviewError.value = response.errorBody()?.string() ?: "Unknown error"
                }
            } catch (e: Exception) {
                _reviewError.value = e.localizedMessage ?: "Unexpected error"
            } finally {
                _reviewLoading.value = false
            }
        }
    }



}
