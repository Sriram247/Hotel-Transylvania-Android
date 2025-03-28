
package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.repository.*;
import com.example.entity.*;
import java.util.List;
import java.util.Optional;

@Service
public class GuestService {
    @Autowired
    private GuestRepository guestRepository;

    public List<Guest> findAll() {
        return guestRepository.findAll();
    }

    public Optional<Guest> findById(Long id) {
        return guestRepository.findById(id);
    }

    public Guest save(Guest guest) {
        return guestRepository.save(guest);
    }
}