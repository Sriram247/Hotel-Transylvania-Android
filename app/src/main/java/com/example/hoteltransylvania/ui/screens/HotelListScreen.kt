package com.example.hoteltransylvania.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

data class Hotel(
    val id: Int,
    val name: String,
    val price: Double,
    val availability: Boolean,
    val imageUrl: String
)

@Composable
fun HotelListScreen(
    location: String,
    checkIn: String,
    checkOut: String,
    guests: Int,
    onHotelSelected: (Hotel) -> Unit
){

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
                    // Example: navigate(selectedHotel)
                } else {
                    // Show a message to select a hotel
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}


@Composable
fun HotelList(onHotelSelected: (Hotel) -> Unit = {}) {
    val hotels = listOf(
        Hotel(1, "Hotel 1", 150.0, true, "https://keycdn.smu.ca/webfiles/Logo-svg.svg"),
        Hotel(2, "Hotel 2", 200.0, false, "https://keycdn.smu.ca/webfiles/Logo-svg.svg"),
        Hotel(3, "Hotel 3", 120.0, true, "https://keycdn.smu.ca/webfiles/Logo-svg.svg"),
        Hotel(4, "Hotel 4", 180.0, true, "https://keycdn.smu.ca/webfiles/Logo-svg.svg")
    )

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(hotels) { hotel ->
            HotelItem(hotel = hotel, onClick = { onHotelSelected(hotel) })
        }
    }
}



// HotelItem function for everything inside the card
@Composable
fun HotelItem(hotel: Hotel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),

        ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically){
            // Hotel Image
            AsyncImage(
                model = hotel.imageUrl,
                contentDescription = hotel.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .height(180.dp)
            )

            // Hotel Info
            Column(modifier = Modifier
                .padding(16.dp)
                .weight(2f)) {
                Text(text = hotel.name, style = MaterialTheme.typography.labelLarge, fontSize = 30.sp)
                Text(text = "Price: \$${hotel.price}", style = MaterialTheme.typography.bodySmall,fontSize = 20.sp)
                Text(
                    text = "Room: ${if (hotel.availability) "Available" else "Not Available"}",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 20.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HotelListScreenPreview() {
    HotelListScreen(
        location = "Transylvania",
        checkIn = "2023-10-01",
        checkOut = "2023-10-05",
        guests = 2,
        onHotelSelected = {}
    )
}

