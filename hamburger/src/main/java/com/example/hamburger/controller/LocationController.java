package com.example.hamburger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

import com.example.hamburger.repository.LocationRepository;
import com.example.hamburger.model.Location;

@Log4j2
@RestController
@RequestMapping("/api")
public class LocationController {

    @Autowired
    LocationRepository locationRepository;

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations(@RequestParam(required = false) String name) {
        try {
            List<Location> location = new ArrayList<>();

            if (name == null){
                log.info("No parameter passed to get the locations");
                locationRepository.findAll().forEach(location::add);
            }

            else
                locationRepository.findByNameContaining(name).forEach(location::add);

            if (location.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(location, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/locations/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable("id") String id) {
        Optional<Location> locationData = locationRepository.findById(id);

        if (locationData.isPresent()) {
            return new ResponseEntity<>(locationData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/locations")
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        try {
            Location _location = locationRepository.save(location);
            return new ResponseEntity<>(_location, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/locations/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable("id") String id, @RequestBody Location location) {
        Optional<Location> locationData = locationRepository.findByLocationId(id);

        if (locationData.isPresent()) {
            Location _location = locationData.get();
            _location.setName(location.getName());
            _location.setLatitude(location.getLatitude());
            _location.setLongitude(location.getLongitude());
            _location.setPhone(location.getPhone());
            _location.setAddress(location.getAddress());
            return new ResponseEntity<>(locationRepository.save(_location), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<HttpStatus> deleteLocation(@PathVariable("id") String id) {
        try {
            locationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/locations")
    public ResponseEntity<HttpStatus> deleteAllLocations() {
        try {
            locationRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
