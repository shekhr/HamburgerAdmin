package com.example.hamburger.repository;

import java.util.List;
import java.util.Optional;

import com.example.hamburger.model.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MenuRepository extends MongoRepository<Menu, String> {
    List<Menu> findByItemContaining(String name);
    Optional<Menu> findByMenuId(String id);
    List<Menu> findByCategoryContaining(String name);
}
