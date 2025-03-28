package com.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Use auto-increment for an int ID
    @Column(updatable = false, nullable = false)
    private int id;

    private String name;
    private String location;
    private String rating;
    private String contactNumber;

    @Version // Optimistic Locking Mechanism
    private int version;

    // Default Constructor (Required by JPA)
    public Hotel() {}

    // Full Constructor
    public Hotel(String name, String location, String rating, String contactNumber) {
        System.out.println("Generated UUID: " + this.getId());
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.contactNumber = contactNumber;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
