package com.example.controller;

import com.example.entity.Hotel;
import com.example.repository.HotelRepository;
import com.example.service.HotelService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.UUID;

@Controller
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @QueryMapping
    public Iterable<Hotel> getAllHotels() {
        return hotelService.findAll();
    }



    @MutationMapping
    public Hotel addHotel(
            @Argument String name,
            @Argument String location,
            @Argument String rating,
            @Argument String contactNumber,
            @Argument String imageUrl,
            @Argument Integer pricePerNight,
            @Argument Boolean isAvailable
            ) {
        Hotel newHotel = new Hotel(name, location, rating , imageUrl, pricePerNight, isAvailable);
        return hotelService.save(newHotel);
    }

    @MutationMapping
    public Hotel updateHotelAi(
            @Argument Integer id,
            @Argument String ai_summary) {
        Optional<Hotel> hotelOptional = hotelService.findById(id);
        if (hotelOptional.isPresent()) {
            Hotel hotel = hotelOptional.get();
            hotel.setAi_summary(ai_summary);
            return hotelService.save(hotel);
        } else {
            throw new RuntimeException("Hotel not found with id: " + id);
        }
}
}
