package com.example.hoteltransylvania.data

data class GraphQLRequest(
    val query: String,
    val variables: Map<String, Any?> = emptyMap()
)

data class HotelResponseWrapper(
    val data: HotelResponseData?
)

data class HotelResponseData(
    val hotels: List<Hotel>
)

data class Hotel(
    val id: Int,
    val name: String,
    val location: String,
    val imageUrl: String,
    val rating: Double,
    val pricePerNight: Double,
    val availability: Boolean
)

data class GuestInfo(
    val name: String,
    val gender: String
)

data class HotelReservationInput(
    val hotelId: String,
    val checkIn: String,
    val checkOut: String,
    val rooms: Int,
    val guests: List<GuestInfo>
)

data class ReserveHotelResponse(
    val confirmationNumber: String
)
