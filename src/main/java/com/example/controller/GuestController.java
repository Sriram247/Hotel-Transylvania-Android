package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.service.*;
import com.example.entity.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/guests")
public class GuestController {
    @Autowired
    private GuestService guestService;

    @GetMapping
    public List<Guest> findAll() {
        return guestService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Guest> findById(@PathVariable Long id) {
        return guestService.findById(id);
    }

    @PostMapping
    public Guest save(@RequestBody Guest guest) {
        return guestService.save(guest);
    }
}
