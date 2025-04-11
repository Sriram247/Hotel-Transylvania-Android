package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import com.example.service.*;
import com.example.entity.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.stereotype.Controller;

class ReservationInput {
    public String hotelName;
    public String location;
    public String checkInDate;
    public String checkOutDate;
}

@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;


    @QueryMapping
    public List<Reservation> getAllReservations() {
        return reservationService.findAll();
    }

    @MutationMapping
    public Reservation addReservation(
        @Argument String hotelName,
        @Argument String location,
        @Argument String checkInDate,
        @Argument String checkOutDate
        ) {
        Reservation reservation = new Reservation(hotelName, location, checkInDate , checkOutDate);
        
        
        return reservationService.save(reservation);
    }
}

