package com.example.entity;

import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    private Long hotelId;

    private String comment;

    public Review() {} // Empty constructor

    public Review(Long hotelId, String comment) {
        this.hotelId = hotelId;
        this.comment = comment;
    }

    // Getters and setters

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
