@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hoteltransylvania.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hoteltransylvania.ui.theme.HotelTransylvaniaTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun HomeScreen(
    onSearchClick: (String, String, String,Int, Int) -> Unit
) {
    var location by remember { mutableStateOf("") }
    var rooms by remember { mutableIntStateOf(1) }
    var adults by remember { mutableIntStateOf(2) }
    var children by remember { mutableIntStateOf(0) }

    // currently set to april 12, 2025, future scope - to add current date dynamically
    val calendar = Calendar.getInstance().apply {
        set(2025, Calendar.APRIL, 12, 0, 0, 0)
    }
    val defaultCheckIn = calendar.timeInMillis
    calendar.add(Calendar.DAY_OF_MONTH, 1)
    val defaultCheckOut = calendar.timeInMillis

    var selectedDate by remember {
        mutableStateOf<Pair<Long?, Long?>>(Pair(defaultCheckIn, defaultCheckOut))
    }

    var showDatePicker by remember { mutableStateOf(false) }
    var showGuestSheet by remember { mutableStateOf(false) }


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
                .clip(RoundedCornerShape(6.dp))
        )

        Spacer(modifier = Modifier.height(10.dp))

        DateRangePickerField(
            selectedDateRange = selectedDate,
            onDateRangeSelected = { selectedDate = it },
            showDatePicker = showDatePicker,
            onShowDatePickerChange = { showDatePicker = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        //Guest Box Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showGuestSheet = true }
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(6.dp))
                .padding(16.dp)
        ) {
            Text("$rooms Room • $adults Adults • $children Children")
        }

        Spacer(modifier = Modifier.height(10.dp))
Button(
    onClick = {
        val guestCount = adults + children
        onSearchClick(location, selectedDate.first.toString(), selectedDate.second.toString(), rooms, guestCount)
    },
    modifier = Modifier
        .fillMaxWidth(),
    shape = RoundedCornerShape(6.dp)
) {
    Text("Search Hotels")
}
    }

    if (showGuestSheet) {
        ModalBottomSheet(
            onDismissRequest = { showGuestSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            GuestPickerSheet(
                rooms = rooms,
                adults = adults,
                children = children,
                onValueChange = { newRooms, newAdults, newChildren ->
                    rooms = newRooms
                    adults = newAdults
                    children = newChildren
                    showGuestSheet = false
                }
            )
        }
    }


}

fun formatDate(millis: Long?): String {
    return millis?.let {
        val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        sdf.format(Date(it))
    } ?: ""
}

@Composable
fun DateRangePickerField(
    selectedDateRange: Pair<Long?, Long?>,
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    showDatePicker: Boolean,
    onShowDatePickerChange: (Boolean) -> Unit
) {
    // Format the Long? dates to a readable date format or placeholder
    val checkInDate = formatDate(selectedDateRange.first) ?: "Check-in"
    val checkOutDate = formatDate(selectedDateRange.second) ?: "Check-out"

    // Row layout for check-in, arrow, and check-out dates
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onShowDatePickerChange(true) } // Trigger the DatePicker dialog
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(6.dp))
            .padding(16.dp)

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

@Composable
fun GuestPickerSheet(
    rooms: Int,
    adults: Int,
    children: Int,
    onValueChange: (Int, Int, Int) -> Unit
) {
    var localRooms by remember { mutableStateOf(rooms) }
    var localAdults by remember { mutableStateOf(adults) }
    var localChildren by remember { mutableStateOf(children) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Select Guests", style = MaterialTheme.typography.titleLarge)

        GuestCounter("Rooms", localRooms, onIncrement = { localRooms++ }, onDecrement = { if (localRooms > 1) localRooms-- })
        GuestCounter("Adults", localAdults, onIncrement = { localAdults++ }, onDecrement = { if (localAdults > 1) localAdults-- })
        GuestCounter("Children", localChildren, onIncrement = { localChildren++ }, onDecrement = { if (localChildren > 0) localChildren-- })

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onValueChange(localRooms, localAdults, localChildren)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Apply")
        }
    }
}

@Composable
fun GuestCounter(
    label: String,
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onDecrement) {
                Icon(Icons.Default.Close, contentDescription = "Remove")
            }
            Text("$value", modifier = Modifier.width(24.dp), textAlign = TextAlign.Center)
            IconButton(onClick = onIncrement) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMyScreen() {
    HotelTransylvaniaTheme {
        HomeScreen(onSearchClick = { _, _, _, _, _ -> })
    }
}


