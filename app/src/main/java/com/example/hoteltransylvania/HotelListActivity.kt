package com.example.hoteltransylvania

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.hoteltransylvania.ui.screens.HotelListScreen

import com.example.hoteltransylvania.ui.theme.HotelTransylvaniaTheme




class HotelListActivity : ComponentActivity() {

    // Get data from the intent
    private val location = intent.getStringExtra("location") ?: ""
    private val checkIn = intent.getStringExtra("checkIn") ?: ""
    private val checkOut = intent.getStringExtra("checkOut") ?: ""
    private val guests = intent.getIntExtra("guests", 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HotelTransylvaniaTheme {
                HotelListScreen(
                    location = location,
                    checkIn = checkIn,
                    checkOut = checkOut,
                    guests = guests,
                    onHotelSelected = { hotel ->

                        val intent = Intent(this, HotelListActivity::class.java)
                        intent.putExtra("location", location)
                        intent.putExtra("checkIn", checkIn)
                        intent.putExtra("checkOut", checkOut)
                        intent.putExtra("guests", guests)
                        startActivity(intent)
                        Toast.makeText(this, "Selected hotel: ${hotel.name}", Toast.LENGTH_LONG).show()
                    }
                )
            }
        }
    }
}
