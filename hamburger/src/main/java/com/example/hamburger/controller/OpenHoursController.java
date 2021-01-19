package com.example.hamburger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.hamburger.model.OpenHours;
import com.example.hamburger.repository.OpenHoursRepository;
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
public class OpenHoursController {

    @Autowired
    OpenHoursRepository openHoursRepository;

    @GetMapping("/openHours")
    public ResponseEntity<List<OpenHours>> getAllOpenHours(@RequestParam(required = false) String name) {
        try {
            List<OpenHours> openHours = new ArrayList<>();

            if (name == null)
                openHoursRepository.findAll().forEach(openHours::add);
            else
                openHoursRepository.findByDayContaining(name).forEach(openHours::add);

            if (openHours.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(openHours, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/openHours/{id}")
    public ResponseEntity<OpenHours> getOpenHoursById(@PathVariable("id") String id) {
        Optional<OpenHours> openHoursData = openHoursRepository.findById(id);

        if (openHoursData.isPresent()) {
            return new ResponseEntity<>(openHoursData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/openHours")
    public ResponseEntity<OpenHours> createOpenHours(@RequestBody OpenHours openHours) {
        try {
            OpenHours _openhours = openHoursRepository.save(openHours);
            return new ResponseEntity<>(_openhours, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/openHours/{id}")
    public ResponseEntity<OpenHours> updateOpenHours(@PathVariable("id") String id, @RequestBody OpenHours openHours) {
        Optional<OpenHours> openHoursData = openHoursRepository.findByOpenHoursId(id);

        if (openHoursData.isPresent()) {
            OpenHours _openHours = openHoursData.get();
            _openHours.setOpenTime(openHours.getOpenTime());
            _openHours.setCloseTime(openHours.getCloseTime());
            _openHours.setDay(openHours.getDay());
            return new ResponseEntity<>(openHoursRepository.save(_openHours), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/openHours/{id}")
    public ResponseEntity<HttpStatus> deleteOpenHours(@PathVariable("id") String id) {
        try {
            openHoursRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/openHours")
    public ResponseEntity<HttpStatus> deleteAllOpenHours() {
        try {
            openHoursRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
