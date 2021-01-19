package com.example.hamburger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.hamburger.model.Menu;
import com.example.hamburger.repository.MenuRepository;
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
public class MenuController {

    @Autowired
    MenuRepository menuRepository;

    @GetMapping("/menu")
    public ResponseEntity<List<Menu>> getAllMenu(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        try {
            List<Menu> menu = new ArrayList<>();

            if (name == null && category == null)
                menuRepository.findAll().forEach(menu::add);
            else if(category == null)
                menuRepository.findByItemContaining(name).forEach(menu::add);
            else if(name == null)
                menuRepository.findByCategoryContaining(category).forEach(menu::add);

            if (menu.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(menu, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/menu/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable("id") String id) {
        Optional<Menu> menuData = menuRepository.findById(id);

        if (menuData.isPresent()) {
            return new ResponseEntity<>(menuData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/menu")
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
        try {
            Menu _menu = menuRepository.save(menu);
            return new ResponseEntity<>(_menu, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/menu/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable("id") String id, @RequestBody Menu menu) {
        Optional<Menu> menuData = menuRepository.findByMenuId(id);

        if (menuData.isPresent()) {
            Menu _menu = menuData.get();
            _menu.setCategory(menu.getCategory());
            _menu.setItem(menu.getItem());
            _menu.setCategory(menu.getCategory());
            _menu.setPrice(menu.getPrice());
            return new ResponseEntity<>(menuRepository.save(_menu), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/menu/{id}")
    public ResponseEntity<HttpStatus> deleteMenu(@PathVariable("id") String id) {
        try {
            menuRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/menu")
    public ResponseEntity<HttpStatus> deleteAllMenus() {
        try {
            menuRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
