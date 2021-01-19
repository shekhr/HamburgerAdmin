package com.example.hamburger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.hamburger.model.Reservation;
import com.example.hamburger.repository.ReservationRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@Log4j2
@RestController
@RequestMapping("/api")
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations(@RequestParam(required = false) String name) {
        try {
            List<Reservation> reservation = new ArrayList<>();

            if (name == null)
                reservationRepository.findAll().forEach(reservation::add);
            else
                reservationRepository.findByFullNameContaining(name).forEach(reservation::add);

            if (reservation.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") String id) {
        Optional<Reservation> reservationData = reservationRepository.findById(id);

        if (reservationData.isPresent()) {
            return new ResponseEntity<>(reservationData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        try {
            Reservation _location = reservationRepository.save(reservation);
            return new ResponseEntity<>(_location, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable("id") String id, @RequestBody Reservation reservation) {
        Optional<Reservation> reservationData = reservationRepository.findByReservationId(id);

        if (reservationData.isPresent()) {
            Reservation _reservation = reservationData.get();
            _reservation.setContact(reservation.getContact());
            _reservation.setDateOfBooking(reservation.getDateOfBooking());
            _reservation.setEmail(reservation.getEmail());
            _reservation.setDateOfEvent(reservation.getDateOfEvent());
            _reservation.setEventCategory(reservation.getEventCategory());
            _reservation.setFullName(reservation.getFullName());
            _reservation.setEventPackageSelected(reservation.getEventPackageSelected());
            _reservation.setStatus(reservation.getStatus());
            return new ResponseEntity<>(reservationRepository.save(_reservation), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<HttpStatus> deleteReservation(@PathVariable("id") String id) {
        try {
            reservationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/reservations")
    public ResponseEntity<HttpStatus> deleteAllReservations() {
        try {
            reservationRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
