package com.example.entity;

import jakarta.persistence.*;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Use auto-increment for an int ID
    @Column(updatable = false, nullable = false)
    private int id;

    private String name;
    private String location;
    private String rating;
    private String imageUrl;
    private Integer pricePerNight;
    private Boolean isAvailable = true;
    private String ai_summary;

    /**
     * @return the ai_summary
     */
    public String getAi_summary() {
        return ai_summary;
    }

    /**
     * @param ai_summary the ai_summary to set
     */
    public void setAi_summary(String ai_summary) {
        this.ai_summary = ai_summary;
    }

    public Hotel() {}

    public Hotel(String name, String location, String rating, String imageUrl, Integer pricePerNight, Boolean isAvailable) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
    }


}
