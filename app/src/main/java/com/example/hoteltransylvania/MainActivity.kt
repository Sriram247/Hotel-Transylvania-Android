package com.example.hoteltransylvania

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.hoteltransylvania.ui.screens.HomeScreen
import com.example.hoteltransylvania.ui.screens.HotelListActivity
import com.example.hoteltransylvania.ui.theme.HotelTransylvaniaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HotelTransylvaniaTheme() {
                HomeScreen { checkIn, checkOut, guests ->
                    // Save to Room (next step)
                    // Make GraphQL service call (later)

                    val intent = Intent(this, Class.forName("com.example.hoteltransylvania.ui.screens.HotelListActivity"))
                    intent.putExtra("checkIn", checkIn)
                    intent.putExtra("checkOut", checkOut)
                    intent.putExtra("guests", guests)
                    startActivity(intent)

                }
            }
        }
    }
}

