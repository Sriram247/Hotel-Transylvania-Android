package com.example.entity;

import jakarta.persistence.*;
import com.example.entity.*;

import java.util.Date;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String hotelName;
    
    
    
    public Reservation(String hotelName, String location, String checkInDate, String checkOutDate) {
        this.hotelName = hotelName;
        this.location = location;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String location;

    private String checkInDate;
    private String checkOutDate;

}
