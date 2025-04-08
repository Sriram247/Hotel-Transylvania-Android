package com.example.hoteltransylvania.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import com.example.hoteltransylvania.viewmodel.HotelListViewModel
import com.example.hoteltransylvania.data.Hotel
import kotlinx.coroutines.delay
import com.eygraber.compose.placeholder.PlaceholderHighlight
import com.eygraber.compose.placeholder.placeholder
import com.eygraber.compose.placeholder.shimmer


@Composable
fun HotelListScreen(
    viewModel: HotelListViewModel,
    location: String,
    checkIn: String,
    checkOut: String,
    rooms: Int,
    guests: Int,
    onHotelSelected: @Composable (Hotel) -> Unit
){

    var selectedHotel by remember { mutableStateOf<Hotel?>(null) }
    // Observe the hotel list, loading, and error states
    val hotels = viewModel.hotels.observeAsState(emptyList())
    val loading = viewModel.loading.observeAsState(false)
    val error = viewModel.error.observeAsState("")

    //for fetching data and populating
    HotelListRequest(viewModel=viewModel, location = location, checkIn = checkIn, checkOut = checkOut, rooms = rooms, guests = guests, hotels = hotels, loading = loading, error = error)


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Show the passed data at the top
        Text("Check-in: $checkIn", style = MaterialTheme.typography.bodyMedium)
        Text("Check-out: $checkOut", style = MaterialTheme.typography.bodyMedium)
        Text("Guests: $guests", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // UI elements
        if (loading.value) {
            //loading part
            ShimmerHotelList()
        } else if (error.value.isNotEmpty()) {
            Text(text = error.value, color = MaterialTheme.colorScheme.error)
        } else {
            // API call and RecyclerView equivalent (LazyColumn)
            HotelList(hotels = hotels.value) { hotel ->
                selectedHotel = hotel
            }

            Spacer(modifier = Modifier.height(16.dp))

        }

    }
}



@Composable
fun HotelList(hotels: List<Hotel>, onHotelSelected: (Hotel) -> Unit = {}) {

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
                Text(text = "Price: \$${hotel.pricePerNight}", style = MaterialTheme.typography.bodySmall,fontSize = 20.sp)
                Text(
                    text = "Room: ${if (hotel.availability) "Available" else "Not Available"}",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 20.sp,
                )
            }
        }
    }
}

@Composable
fun ShimmerHotelList() {
    Column(modifier = Modifier.fillMaxWidth()) {
        repeat(5) {
            ShimmerHotelItem()
        }
    }
}

@Composable
fun ShimmerHotelItem(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
        ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically){
            // Hotel Image
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(180.dp)
                    .placeholder(
                        visible = true,
                        color = Color.Gray,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.White,
                        ),
                    )
            )

            // Hotel Info
            Column(modifier = Modifier
                .padding(16.dp)
                .weight(2f))
            {
                Text(text = "Sample", style = MaterialTheme.typography.labelLarge, fontSize = 30.sp,
                        modifier = Modifier
                    .placeholder(
                        visible = true,
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.White,
                        ),
                    ))
                Text(text = "Price:", style = MaterialTheme.typography.bodySmall,fontSize = 20.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .placeholder(
                            visible = true,
                            color = Color.Gray,
                            // optional, defaults to RectangleShape
                            shape = RoundedCornerShape(4.dp),
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.White,
                            ),
                        ))
                Text(
                    text = "Room: Sample",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .placeholder(
                            visible = true,
                            color = Color.Gray,
                            // optional, defaults to RectangleShape
                            shape = RoundedCornerShape(4.dp),
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.White,
                            ),
                        )
                )
            }
        }
    }
}


// call the hotelListViewModel
@Composable
fun HotelListRequest(viewModel: HotelListViewModel, location: String, checkIn: String, checkOut: String, rooms: Int, guests: Int, hotels: State<List<Hotel>>, loading: State<Boolean>, error: State<String>) {


    // Fetching hotel data when the screen is displayed
    LaunchedEffect(Unit) {

        delay(3000) // 3 seconds flex

        viewModel.fetchHotels(
            location = location,
            checkIn = checkIn,
            checkOut = checkOut,
            rooms = rooms,
            guests = guests
        )


    }

}
