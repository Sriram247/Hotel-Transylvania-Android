package com.example.hoteltransylvania

        import android.os.Bundle
        import android.widget.Toast
        import androidx.activity.ComponentActivity
        import androidx.activity.compose.setContent
        import androidx.activity.viewModels
        import androidx.compose.foundation.layout.*
        import androidx.compose.material3.*
        import androidx.compose.runtime.*
        import androidx.compose.ui.Modifier
        import androidx.compose.ui.unit.dp
        import com.example.hoteltransylvania.data.GuestInfo
        import com.example.hoteltransylvania.ui.screens.HotelConfirmationScreen
        import com.example.hoteltransylvania.ui.theme.HotelTransylvaniaTheme
        import com.example.hoteltransylvania.viewmodel.HotelReserveViewModel
        import com.example.hoteltransylvania.network.RetrofitInstance
        import com.example.hoteltransylvania.viewmodel.HotelReserveViewModelFactory

        class ConfirmationActivity : ComponentActivity() {

            private val reserveViewModel: HotelReserveViewModel by viewModels {
                HotelReserveViewModelFactory(RetrofitInstance.apiService)
            }

            @Suppress("DEPRECATION")
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)

                // Retrieve data from the intent
                val guestList = intent.getParcelableArrayListExtra<GuestInfo>("guestList") ?: arrayListOf()
                val checkIn = intent.getStringExtra("checkIn") ?: ""
                val checkOut = intent.getStringExtra("checkOut") ?: ""
                val hotelName = intent.getStringExtra("hotelName") ?: ""
                val location = intent.getStringExtra("location") ?: ""

                submitHotelDetails(
                    hotelName = hotelName,
                    hotelLocation = location,
                    checkIn = checkIn,
                    checkOut = checkOut
                )

            }

            private fun submitHotelDetails(
                hotelName: String,
                hotelLocation: String,
                checkIn: String,
                checkOut: String
            ) {
                reserveViewModel.reserveHotel(
                    hotelName = hotelName,
                    hotelLocation = hotelLocation,
                    checkIn = checkIn,
                    checkOut = checkOut,
                    onSuccess = { confirmationNumber ->
                        setContent {
                            HotelTransylvaniaTheme {
                                HotelConfirmationScreen(confirmationNumber = confirmationNumber)
                            }
                        }
                    },
                    onError = { errorMsg ->
                        Toast.makeText(this, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }