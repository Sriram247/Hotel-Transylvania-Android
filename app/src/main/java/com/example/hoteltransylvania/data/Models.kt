package com.example.hoteltransylvania.data
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class GraphQLRequest(
    val query: String,
    val variables: Map<String, Any?> = emptyMap()
)

data class HotelResponseWrapper(
    val data: HotelResponseData?
)

data class HotelResponseData(
    val getAllHotels: List<Hotel>
)

@Parcelize
data class Hotel(
    val id: Int,
    val name: String,
    val location: String?,
    val imageUrl: String = "https://www.shutterstock.com/image-photo/mysterious-eerie-ambience-inside-motel-600nw-2454376659.jpg",
    val rating: Double?,
    val pricePerNight: Double?,
    val availability: Boolean
) : Parcelable


@Parcelize
data class GuestInfo(
    val name: String,
    val gender: String
) : Parcelable


data class ReserveHotelResponse(
    val data: AddReservationData
)

data class AddReservationData(
    val addReservation: Reservation
)

data class Reservation(
    val id: String
)


//reviews
data class ReviewsResponse(
    val data: ReviewData
)

data class ReviewData(
    val reviews: List<String>,
    val summary: String
)


