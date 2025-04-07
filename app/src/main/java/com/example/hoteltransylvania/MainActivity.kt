package com.example.hoteltransylvania

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.hoteltransylvania.ui.screens.HomeScreen
import com.example.hoteltransylvania.ui.theme.HotelTransylvaniaTheme
import com.example.hoteltransylvania.HotelListActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HotelTransylvaniaTheme() {
                HomeScreen { location ,checkIn, checkOut, guests ->
                    // Save to Room (next step)
                    // Make GraphQL service call (later)

                    val intent = Intent(this, HotelListActivity::class.java)
                    intent.putExtra("location", location)
                    intent.putExtra("checkIn", checkIn)
                    intent.putExtra("checkOut", checkOut)
                    intent.putExtra("guests", guests)
                    startActivity(intent)

                }
            }
        }
    }
}

