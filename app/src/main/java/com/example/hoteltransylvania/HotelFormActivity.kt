package com.example.hoteltransylvania

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.hoteltransylvania.data.Hotel
import com.example.hoteltransylvania.data.GuestInfo
import com.example.hoteltransylvania.ui.theme.HotelTransylvaniaTheme
import com.example.hoteltransylvania.ui.screens.HotelFormScreen
import com.example.hoteltransylvania.viewmodel.HotelListViewModel
import com.example.hoteltransylvania.viewmodel.ReviewsViewModel

class HotelFormActivity : ComponentActivity() {

    private val reviewsViewModel: ReviewsViewModel by viewModels()

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve data from the intent
        val hotel = intent.getParcelableExtra<Hotel>("hotel")!!
        val location = intent.getStringExtra("location")!!
        val checkIn = intent.getStringExtra("checkIn")!!
        val checkOut = intent.getStringExtra("checkOut")!!
        val rooms = intent.getIntExtra("rooms", 1)
        val guests = intent.getIntExtra("guests", 1)

        reviewsViewModel.fetchReviews(hotel.id,hotel.ai_summary?: "Default summary")
        setContent {
            HotelTransylvaniaTheme {
                HotelFormScreen(
                    hotel = hotel,
                    guests = guests,
                    checkIn = checkIn,
                    checkOut = checkOut,
                    rooms = rooms,
                    location = location,
                    reviewsViewModel = reviewsViewModel,
                    onSubmit = { guestList ->
                        val intent = Intent(this, ConfirmationActivity::class.java).apply {
                            putParcelableArrayListExtra("guestList", ArrayList(guestList))
                            putExtra("checkIn", checkIn)
                            putExtra("checkOut", checkOut)
                            putExtra("hotelName", hotel.name)
                            putExtra("location", location)
                        }
                        startActivity(intent)
                        finish() // Close the current activity
                    }
                )
            }
        }
    }
}