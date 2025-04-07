@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hoteltransylvania.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hoteltransylvania.ui.theme.HotelTransylvaniaTheme
import java.time.format.DateTimeFormatter
import java.util.Date


@Composable
fun HomeScreen(
    onSearchClick: (String, String, String, Int) -> Unit
) {
    var location by remember { mutableStateOf("") }
    var guests by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Pair<Long?, Long?>>(Pair(null, null)) }

    var showDatePicker by remember { mutableStateOf(false) }


    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Greetings, Traveler!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.Start)

        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Where to?") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        DateRangePickerField(
            selectedDateRange = selectedDate,
            onDateRangeSelected = { selectedDate = it },
            showDatePicker = showDatePicker,
            onShowDatePickerChange = { showDatePicker = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val guestCount = guests.toIntOrNull() ?: 1
                onSearchClick(location,selectedDate.first.toString(), selectedDate.second.toString(), guestCount)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search Hotels")
        }
    }
}

@Composable
fun DateRangePickerField(
    selectedDateRange: Pair<Long?, Long?>,
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    showDatePicker: Boolean,
    onShowDatePickerChange: (Boolean) -> Unit
) {
    // Format the Long? dates to a readable date format or placeholder
    val checkInDate = selectedDateRange.first?.let { Date(it).toString() } ?: "Check-in"
    val checkOutDate = selectedDateRange.second?.let { Date(it).toString() } ?: "Check-out"

    // Row layout for check-in, arrow, and check-out dates
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onShowDatePickerChange(true) } // Trigger the DatePicker dialog
            .border(1.dp, MaterialTheme.colorScheme.outline)
            .padding(16.dp)
            .clip(RoundedCornerShape(30.dp))
        
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = checkInDate,
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Arrow Icon",
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.align(Alignment.CenterVertically) // Center the arrow icon
            )
            Text(
                text = checkOutDate,
            )
        }
    }

    // Show the DateRangePickerModal when the OutlinedTextField is clicked
    if (showDatePicker) {
        DateRangePickerModal(
            onDateRangeSelected = { dateRange ->
                onDateRangeSelected(dateRange) // Update the selected date range
                onShowDatePickerChange(false) // Close the DatePicker
            },
            onDismiss = { onShowDatePickerChange(false) } // Dismiss the DatePicker
        )
    }
}



@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(
                    text = "Select date range"
                )
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMyScreen() {
    HotelTransylvaniaTheme {
        HomeScreen(onSearchClick = { _, _, _, _ -> })
    }
}


