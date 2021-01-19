package com.example.hamburger.model;

import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;


@Data
public class Category {
    private String category;
}
