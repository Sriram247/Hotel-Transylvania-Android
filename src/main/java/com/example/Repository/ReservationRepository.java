package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {}
