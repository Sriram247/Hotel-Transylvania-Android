package com.example.hoteltransylvania

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

        // Handle hotel reservation submission
        fun submitHotelDetails(hotel: Hotel, guestInfo: List<GuestInfo>) {
            reserveViewModel.reserveHotel(
                hotel = hotel,
                checkIn = checkIn,
                checkOut = checkOut,
                rooms = rooms,
                guests = guestInfo,
                onSuccess = { confirmationNumber ->
                    // Display confirmation screen directly
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
                    onHotelSelected = { hotel ->
                        // Show the hotel form screen with guest details and reservation info
                        HotelFormScreen(
                            hotel = hotel,
                            guests = guests,
                            checkIn = checkIn,
                            checkOut = checkOut,
                            rooms = rooms,
                            location = location,
                            onSubmit = { guestList ->
                                // Handle submission of guest details and reserve hotel
                                submitHotelDetails(hotel, guestList)
                            }
                        )
                    }
                )
            }
        }
    }
}
