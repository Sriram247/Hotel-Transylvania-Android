package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.service.*;
import com.example.entity.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<Review> findAll() {
        return reviewService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Review> findById(@PathVariable Long id) {
        return reviewService.findById(id);
    }

    @PostMapping
    public Review save(@RequestBody Review review) {
        return reviewService.save(review);
    }
}