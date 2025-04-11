package com.example.hoteltransylvania

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.hoteltransylvania.ui.screens.HomeScreen
import com.example.hoteltransylvania.ui.theme.HotelTransylvaniaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HotelTransylvaniaTheme() {
                HomeScreen {location, checkIn, checkOut, rooms, guests ->
                    // Save to Room (next step)
                    // For now, just pass the data to the next activity
                    // GraphQL service call happens in the HotelListActivity

                    Log.i(TAG, "going next screen onCreate: location: $location")
                    val intent = Intent(this, HotelListActivity::class.java)
                    intent.putExtra("location", location)
                    intent.putExtra("checkIn", checkIn)
                    intent.putExtra("checkOut", checkOut)
                    intent.putExtra("rooms", rooms)
                    intent.putExtra("guests", guests)
                    startActivity(intent)

                }
            }
        }
    }
}

