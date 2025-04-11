package com.example.hoteltransylvania.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hoteltransylvania.data.GraphQLRequest
import com.example.hoteltransylvania.data.GuestInfo
import com.example.hoteltransylvania.data.Hotel
import com.example.hoteltransylvania.data.ReserveHotelResponse
import com.example.hoteltransylvania.service.HotelGraphQLService
import com.example.hoteltransylvania.repository.GraphQLQueries
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class HotelReserveViewModel(
    private val graphQLService: HotelGraphQLService
) : ViewModel() {

    private val _confirmationNumber = MutableStateFlow<String?>(null)
    val confirmationNumber = _confirmationNumber.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun reserveHotel(
        hotel: Hotel,
        checkIn: String,
        checkOut: String,
        rooms: Int,
        guests: List<GuestInfo>,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val mutation = GraphQLQueries.getBookHotelMutation(
            checkInDate = checkIn,
            checkOutDate = checkOut,
            hotelName = hotel.name,
            location = hotel.location
        )

        val request = GraphQLRequest(query = mutation)

        _loading.value = true
        _error.value = null

        viewModelScope.launch {
            val response: Response<ReserveHotelResponse> = graphQLService.reserveHotel(request)
            if (response.isSuccessful) {
                val confirmation = response.body()?.id
                if (confirmation != null) {
                    _confirmationNumber.value = confirmation
                    onSuccess(confirmation)
                } else {
                    onError("No confirmation number received.")
                }
            } else {
                _error.value = response.errorBody()?.string() ?: "Unknown error"
                onError(_error.value ?: "Unknown error")
            }
            _loading.value = false
        }
    }
}
