package com.example.hoteltransylvania.repository

object GraphQLQueries {

    // A GraphQL query to fetch hotels
    val SEARCH_HOTELS_QUERY = """
        query SearchHotels(${'$'}location: String!, ${'$'}checkIn: String!, ${'$'}checkOut: String!, ${'$'}guests: Int!) {
            hotels(location: ${'$'}location, checkIn: ${'$'}checkIn, checkOut: ${'$'}checkOut, guests: ${'$'}guests) {
                id
                name
                location
                imageUrl
                rating
                pricePerNight
            }
        }
    """.trimIndent()
}
