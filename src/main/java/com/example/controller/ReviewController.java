package com.example.controller;

import com.example.entity.Review;
import com.example.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @MutationMapping
    public Review addReview(@Argument Long hotelId, @Argument String comment) {
        Review review = new Review(hotelId, comment);
        return reviewRepository.save(review);
    }
    @QueryMapping
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @QueryMapping
    public List<Review> getReviewsByHotelId(@Argument Long hotelId) {
        List<Review> reviews = reviewRepository.findByHotelId(hotelId);
        if (reviews.isEmpty()) return null;
        else return reviews;

    }

/*     private String generateAISummary(List<String> reviews) {
        if (reviews.isEmpty()) return "No reviews available.";
        if (reviews.size() == 1) return reviews.get(0);

        return "Guests generally had " +
                (reviews.stream().anyMatch(r -> r.toLowerCase().contains("bad") || r.toLowerCase().contains("poor"))
                        ? "mixed experiences."
                        : "positive experiences.");
    } */
}
