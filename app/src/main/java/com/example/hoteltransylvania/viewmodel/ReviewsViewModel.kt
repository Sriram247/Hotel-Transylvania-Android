package com.example.hoteltransylvania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hoteltransylvania.data.GraphQLRequest
import com.example.hoteltransylvania.network.RetrofitInstance
import com.example.hoteltransylvania.repository.GraphQLQueries
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReviewsViewModel(application: Application) : AndroidViewModel(application) {

    private val _reviews = MutableStateFlow<List<String>>(emptyList())
    val reviews = _reviews.asStateFlow()

    private val _summary = MutableStateFlow<String>("")
    val summary = _summary.asStateFlow()

    private val _reviewLoading = MutableStateFlow(false)
    val reviewLoading = _reviewLoading.asStateFlow()

    private val _reviewError = MutableStateFlow<String?>(null)
    val reviewError = _reviewError.asStateFlow()

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