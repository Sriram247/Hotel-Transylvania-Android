package com.example.service;

import com.example.entity.Hotel;
import com.example.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> findAll() {
        System.out.println("Fetching from database...");
        return hotelRepository.findAll();
    }

    public Optional<Hotel> findById(UUID id){
        return hotelRepository.findById(id.toString());
    }

    @Transactional
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }




}
