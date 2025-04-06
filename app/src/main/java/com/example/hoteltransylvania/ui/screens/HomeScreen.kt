package com.example.hoteltransylvania.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hoteltransylvania.ui.theme.HotelTransylvaniaTheme


@Composable
fun HomeScreen(
    onSearchClick: (String, String, Int) -> Unit
) {
    var checkInDate by remember { mutableStateOf("") }
    var checkOutDate by remember { mutableStateOf("") }
    var guests by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hotel Reservation", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = checkInDate,
            onValueChange = { checkInDate = it },
            label = { Text("Check-in Date (yyyy-mm-dd)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = checkOutDate,
            onValueChange = { checkOutDate = it },
            label = { Text("Check-out Date (yyyy-mm-dd)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = guests,
            onValueChange = { guests = it },
            label = { Text("Number of Guests") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val guestCount = guests.toIntOrNull() ?: 1
                onSearchClick(checkInDate, checkOutDate, guestCount)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search Hotels")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyScreen() {
    HotelTransylvaniaTheme {
        HomeScreen(onSearchClick = { _, _, _ -> })
    }
}


