package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.*;
import com.example.service.*;
import com.example.entity.*;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class GuestController {
    @Autowired
    private GuestService guestService;

    @QueryMapping
    public List<Guest> getAllGuests() {
        return guestService.findAll();
    }

    @MutationMapping
    public Guest addGuest(
        @Argument String firstName,
            @Argument String gender) {
        Guest newGuest = new Guest(firstName, gender);
        return guestService.save(newGuest);
    }
}
