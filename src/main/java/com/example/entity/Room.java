package com.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class Room {
    @jakarta.persistence.Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    private String number;
    private String type;
    private int capacity;
    private double pricePerNight;
    private String status;

}
