package com.example.hoteltransylvania.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hoteltransylvania.service.HotelGraphQLService

class HotelReserveViewModelFactory(
    private val graphQLService: HotelGraphQLService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HotelReserveViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HotelReserveViewModel(graphQLService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
