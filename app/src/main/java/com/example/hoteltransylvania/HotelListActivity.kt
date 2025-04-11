package com.example.hoteltransylvania

import android.content.ContentValues.TAG
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.hoteltransylvania.data.Hotel

import com.example.hoteltransylvania.ui.theme.HotelTransylvaniaTheme
import com.example.hoteltransylvania.viewmodel.HotelListViewModel
import com.example.hoteltransylvania.data.GuestInfo
import com.example.hoteltransylvania.ui.screens.HotelConfirmationScreen
import com.example.hoteltransylvania.ui.screens.HotelFormScreen
import com.example.hoteltransylvania.ui.screens.HotelListScreen
import com.example.hoteltransylvania.viewmodel.HotelReserveViewModel
import android.util.Log
import androidx.compose.runtime.*


class HotelListActivity : ComponentActivity() {

    private val viewModel: HotelListViewModel by viewModels()
    private val reserveViewModel: HotelReserveViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get data from the intent
        val location = intent.getStringExtra("location") ?: "default"
        val checkIn = intent.getStringExtra("checkIn") ?: ""
        val checkOut = intent.getStringExtra("checkOut") ?: ""
        val rooms = intent.getIntExtra("rooms", 1)
        val guests = intent.getIntExtra("guests", 1)

        Log.i(TAG, "HotelListActivity - location: $location, checkIn: $checkIn, checkOut: $checkOut, rooms: $rooms, guests: $guests")

        // Function to submit reservation
        fun submitHotelDetails(hotel: Hotel, guestInfo: List<GuestInfo>) {
            reserveViewModel.reserveHotel(
                hotel = hotel,
                checkIn = checkIn,
                checkOut = checkOut,
                rooms = rooms,
                guests = guestInfo,
                onSuccess = { confirmationNumber ->
                    setContent {
                        HotelTransylvaniaTheme {
                            HotelConfirmationScreen(confirmationNumber = confirmationNumber)
                        }
                    }
                },
                onError = { errorMsg ->
                    Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
                }
            )
        }

        setContent {
            HotelTransylvaniaTheme {
                HotelListScreen(
                    viewModel = viewModel,
                    location = location,
                    checkIn = checkIn,
                    checkOut = checkOut,
                    rooms = rooms,
                    guests = guests,
                    onHotelSelected = { hotel, guestList ->
                        submitHotelDetails(hotel, guestList)
                    }
                )
            }
        }
    }

    companion object {
        private const val TAG = "HotelListActivity"
    }
}
