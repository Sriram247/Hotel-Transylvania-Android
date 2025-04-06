package com.example.hoteltransylvania.ui.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hoteltransylvania.ui.theme.HotelTransylvaniaTheme


data class Hotel(
    val id: Int,
    val name: String,
    val price: Double,
    val availability: Boolean
)

class HotelListActivity : ComponentActivity() {

    // Get data from the intent
    private val checkIn = intent.getStringExtra("checkIn") ?: ""
    private val checkOut = intent.getStringExtra("checkOut") ?: ""
    private val guests = intent.getIntExtra("guests", 1)

    // For hotel selection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HotelTransylvaniaTheme() {
                var selectedHotel by remember { mutableStateOf<Hotel?>(null) }

                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    // Show the passed data at the top
                    Text("Check-in: $checkIn", style = MaterialTheme.typography.bodyMedium)
                    Text("Check-out: $checkOut", style = MaterialTheme.typography.bodyMedium)
                    Text("Guests: $guests", style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(24.dp))

                    // API call and RecyclerView equivalent (LazyColumn)
                    HotelList { hotel ->
                        selectedHotel = hotel
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Next button
                    Button(
                        onClick = {
                            if (selectedHotel != null) {
                                // Navigate to next screen with the selected hotel
                                Toast.makeText(this@HotelListActivity, "Proceeding to next step", Toast.LENGTH_SHORT).show()
                                // Example of passing hotel to the next screen via Intent or callback
                                // Example: navigate(selectedHotel)
                            } else {
                                Toast.makeText(this@HotelListActivity, "Please select a hotel", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Next")
                    }
                }
            }
        }
    }
}

@Composable
fun HotelList(onHotelSelected: (Hotel) -> Unit) {
    // Sample list of hotels (in a real app, this will come from an API call)
    val hotels = listOf(
        Hotel(id = 1, name = "Hotel 1", price = 150.0, availability = true),
        Hotel(id = 2, name = "Hotel 2", price = 200.0, availability = false),
        Hotel(id = 3, name = "Hotel 3", price = 120.0, availability = true),
        Hotel(id = 4, name = "Hotel 4", price = 180.0, availability = true)
    )

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(hotels) { hotel ->
            HotelItem(hotel = hotel, onClick = { onHotelSelected(hotel) })
        }
    }
}

@Composable
fun HotelItem(hotel: Hotel, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = hotel.name, style = MaterialTheme.typography.bodyMedium)
                Text(text = "Price: \$${hotel.price}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Availability: ${if (hotel.availability) "Available" else "Not Available"}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
