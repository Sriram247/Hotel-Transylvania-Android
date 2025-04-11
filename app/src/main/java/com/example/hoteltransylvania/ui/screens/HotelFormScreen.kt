package com.example.hoteltransylvania.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.hoteltransylvania.data.Hotel
import com.example.hoteltransylvania.data.GuestInfo
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

@Composable
fun HotelFormScreen(
    hotel: Hotel,
    guests: Int,
    checkIn: String,
    checkOut: String,
    rooms: Int,
    location: String,
    onSubmit: (List<GuestInfo>) -> Unit
) {
    // State lists
    val guestNames = remember { List(guests) { mutableStateOf(TextFieldValue("")) } }
    val guestGenders = remember { List(guests) { mutableStateOf("Male") } }
    val genderOptions = listOf("Male", "Female", "Other")

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Hotel Image and Title
        Text("${hotel.name}", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            model = hotel.imageUrl,
            contentDescription = hotel.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Stay Info
        Text("Location: $location")
        Text("Check-in: $checkIn")
        Text("Check-out: $checkOut")
        Text("Rooms: $rooms")
        Text("Guests: $guests")
        Text("Price/Night: \$${hotel.pricePerNight}")

        Spacer(modifier = Modifier.height(24.dp))

        // Guest Form Fields
        repeat(guests) { index ->
            Text("Guest ${index + 1} Details", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = guestNames[index].value,
                onValueChange = { guestNames[index].value = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Gender:")
            genderOptions.forEach { gender ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    RadioButton(
                        selected = guestGenders[index].value == gender,
                        onClick = { guestGenders[index].value = gender }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(gender)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                val guestList = guestNames.mapIndexed { index, nameState ->
                    GuestInfo(
                        name = nameState.value.text,
                        gender = guestGenders[index].value
                    )
                }
                onSubmit(guestList)
            },
            enabled = guestNames.all { it.value.text.isNotBlank() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}
