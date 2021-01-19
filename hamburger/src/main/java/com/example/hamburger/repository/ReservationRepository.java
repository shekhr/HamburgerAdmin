package com.example.hamburger.repository;

import java.util.List;
import java.util.Optional;

import com.example.hamburger.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
    List<Reservation> findByFullNameContaining(String name);
    Optional<Reservation> findByReservationId(String id);
}

