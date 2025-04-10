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
            @Argument String contactNumber
    ) {
        Hotel newHotel = new Hotel(name, location, rating, contactNumber);
        return hotelService.save(newHotel);
    }
}
