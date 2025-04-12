package com.example.hoteltransylvania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hoteltransylvania.data.GraphQLRequest
import com.example.hoteltransylvania.data.Hotel
import com.example.hoteltransylvania.data.ReviewList
import com.example.hoteltransylvania.network.RetrofitInstance
import com.example.hoteltransylvania.repository.GraphQLQueries
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReviewsViewModel(application: Application) : AndroidViewModel(application) {

private val _reviews = MutableStateFlow<List<ReviewList>>(emptyList())
    val reviews = _reviews.asStateFlow()

    private val _summary = MutableStateFlow<String>("")
    val summary = _summary.asStateFlow()

    private val _reviewLoading = MutableStateFlow(false)
    val reviewLoading = _reviewLoading.asStateFlow()

    private val _reviewError = MutableStateFlow<String?>(null)
    val reviewError = _reviewError.asStateFlow()


    fun fetchReviews(hotelId: Int, ai_summary: String) {
        viewModelScope.launch {
            _reviewLoading.value = true
            _reviewError.value = null
            try {
                val query = GraphQLQueries.getReviewsQuery(hotelId)
                val response = RetrofitInstance.apiService.getReviews(mapOf("query" to query))
                if (response.isSuccessful) {
                    val body = response.body()
                    _reviews.value = body?.data?.getAllReviews ?: emptyList()
                    _summary.value = getHotelSummaryFromPreferences(hotelId, ai_summary)
                } else {
                    _reviewError.value = "Failed to fetch reviews"
                }
            } catch (e: Exception) {
                _reviewError.value = e.localizedMessage ?: "Unknown error"
            } finally {
                _reviewLoading.value = false
            }
        }
    }

    private fun getHotelSummaryFromPreferences(hotelId: Int, defaultSummary: String): String {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("YourSharedPreferencesName", Application.MODE_PRIVATE)
        val hotelListJson = sharedPreferences.getString("HotelList", null) ?: return defaultSummary

        val hotelList = try {
            // Assuming you are using a JSON library like Gson to parse the list
            val gson = com.google.gson.Gson()
            val type = object : com.google.gson.reflect.TypeToken<List<Hotel>>() {}.type
            gson.fromJson<List<Hotel>>(hotelListJson, type)
        } catch (e: Exception) {
            emptyList<Hotel>()
        }

        val matchingHotel = hotelList.find { it.id == hotelId }
        return matchingHotel?.ai_summary ?: defaultSummary
    }
}