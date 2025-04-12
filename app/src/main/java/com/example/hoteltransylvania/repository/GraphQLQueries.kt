package com.example.hoteltransylvania.repository

object GraphQLQueries {

    // A GraphQL query to fetch hotels
    val SEARCH_HOTELS_QUERY = """
        query getHotels {
            getAllHotels {
                id
                name
                location
                imageUrl
                rating
                pricePerNight
            }
        }
    """.trimIndent()

    fun getBookHotelMutation(
        checkInDate: String,
        checkOutDate: String,
        hotelName: String,
        location: String
    ): String {
        return """
            mutation MyMutation {
                addReservation(
                    checkInDate: "$checkInDate"
                    checkOutDate: "$checkOutDate"
                    hotelName: "$hotelName"
                    location: "$location"
                ) {
                    id
                }
            }
        """.trimIndent()
    }

    fun getReviewsQuery(hotelName: String): String {
        return """
        query {
            reviews(hotelName: "$hotelName") {
                texts
                ai_summary
            }
        }
    """.trimIndent()
    }

}
