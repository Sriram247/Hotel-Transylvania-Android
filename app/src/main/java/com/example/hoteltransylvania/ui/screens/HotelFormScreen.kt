package com.example.hoteltransylvania.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.hoteltransylvania.data.Hotel
import com.example.hoteltransylvania.data.GuestInfo
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import coil.compose.AsyncImage
import com.example.hoteltransylvania.viewmodel.ReviewsViewModel

@Composable
fun HotelFormScreen(
    hotel: Hotel,
    guests: Int,
    checkIn: String,
    checkOut: String,
    rooms: Int,
    location: String,
    onSubmit: (List<GuestInfo>) -> Unit,
    reviewsViewModel: ReviewsViewModel,
) {
    // State lists
    val guestNames = remember { List(guests) { mutableStateOf(TextFieldValue("")) } }
    val guestGenders = remember { List(guests) { mutableStateOf("Male") } }
    val genderOptions = listOf("Male", "Female", "Other")

    val scrollState = rememberScrollState()

    // Collect review-related states from ReviewsViewModel
    val loading by reviewsViewModel.reviewLoading.collectAsState()
    val reviews by reviewsViewModel.reviews.collectAsState()
    val summary by reviewsViewModel.summary.collectAsState()
    val error by reviewsViewModel.reviewError.collectAsState()

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
                .aspectRatio(16f / 9f)
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
        HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
        Spacer(modifier = Modifier.height(24.dp))

        val showDialog = remember { mutableStateOf(false) }

        LaunchedEffect(showDialog.value) {
            if (showDialog.value) {
                reviewsViewModel.fetchReviews(hotel.name) // Use ReviewsViewModel here
            }
        }

        TextButton(onClick = { showDialog.value = true }) {
            Text("Reviews")
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
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
            Row {
                genderOptions.forEach { gender ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        RadioButton(
                            selected = guestGenders[index].value == gender,
                            onClick = { guestGenders[index].value = gender }
                        )
                        Text(gender)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(24.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                confirmButton = {
                    TextButton(onClick = { showDialog.value = false }) {
                        Text("Close")
                    }
                },
                title = { Text("Hotel Reviews") },
                text = {
                    if (loading) {
                        Text("Loading...")
                    } else if (error != null) {
                        Text("Error: $error")
                    } else {
                        Column {
                            Text("Summary: ${summary}")
                            Spacer(modifier = Modifier.height(8.dp))
                            reviews.forEach { review ->
                                Text("• $review")
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }
                    }
                }
            )
        }

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
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text("Submit")
        }
    }
}