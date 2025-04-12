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
    val imageUrl: String,
    val rating: Double?,
    val pricePerNight: Double?,
    val availability: Boolean,
    val ai_summary: String?,
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


data class ReviewResponseWrapper(
    val data: ReviewData?
)

data class ReviewData(
    val getAllReviews: List<ReviewList>?
)

data class ReviewList(
    val hotelId: Int,
    val comment: String
)



