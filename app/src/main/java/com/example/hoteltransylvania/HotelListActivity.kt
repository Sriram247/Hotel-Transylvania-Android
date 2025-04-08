package com.example.hoteltransylvania

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.hoteltransylvania.ui.screens.HotelListScreen

import com.example.hoteltransylvania.ui.theme.HotelTransylvaniaTheme
import com.example.hoteltransylvania.viewmodel.HotelListViewModel


class HotelListActivity : ComponentActivity() {



    private val viewModel: HotelListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get data from the intent
        val location = intent.getStringExtra("location") ?: "default"
        val checkIn = intent.getStringExtra("checkIn") ?: ""
        val checkOut = intent.getStringExtra("checkOut") ?: ""
        val rooms = intent.getIntExtra("rooms",1)
        val guests = intent.getIntExtra("guests", 1)

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

                        val intent = Intent(this, HotelListActivity::class.java)
                        intent.putExtra("location", location)
                        intent.putExtra("checkIn", checkIn)
                        intent.putExtra("checkOut", checkOut)
                        intent.putExtra("rooms", rooms)
                        intent.putExtra("guests", guests)
                        startActivity(intent)
                        Toast.makeText(this, "Selected hotel: ${hotel.name}", Toast.LENGTH_LONG).show()
                    }
                )
            }
        }
    }
}
