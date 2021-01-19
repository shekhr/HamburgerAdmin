package com.example.hamburger.repository;

import java.util.List;
import java.util.Optional;

import com.example.hamburger.model.OpenHours;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface OpenHoursRepository extends MongoRepository<OpenHours, String> {
    List<OpenHours> findByDayContaining(String name);
    Optional<OpenHours> findByOpenHoursId(String id);
}
