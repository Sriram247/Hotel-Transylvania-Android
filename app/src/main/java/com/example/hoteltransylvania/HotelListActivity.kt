package com.example.hoteltransylvania

import android.content.ContentValues.TAG
import android.content.Intent
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
import com.example.hoteltransylvania.network.RetrofitInstance
import com.example.hoteltransylvania.service.HotelGraphQLService
import com.example.hoteltransylvania.viewmodel.HotelReserveViewModelFactory


class HotelListActivity : ComponentActivity() {

    private val viewModel: HotelListViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get data from the intent
        val location = intent.getStringExtra("location") ?: "default"
        val checkIn = intent.getStringExtra("checkIn") ?: ""
        val checkOut = intent.getStringExtra("checkOut") ?: ""
        val rooms = intent.getIntExtra("rooms", 1)
        val guests = intent.getIntExtra("guests", 1)

        Log.i(TAG, "HotelListActivity - location: $location, checkIn: $checkIn, checkOut: $checkOut, rooms: $rooms, guests: $guests")


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
                        Log.d("Navigation", "Navigating to HotelFormActivity")
                        val intent = Intent(this, HotelFormActivity::class.java)
                        intent.putExtra("hotel", hotel)
                        intent.putExtra("location", location)
                        intent.putExtra("checkIn", checkIn)
                        intent.putExtra("checkOut", checkOut)
                        intent.putExtra("rooms", rooms)
                        intent.putExtra("guests", guests)
                        startActivity(intent)
                    }
                )
            }
        }
    }

    companion object {
        private const val TAG = "HotelListActivity"
    }
}
