package com.example.hamburger.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "menu")
@Data
public class Menu extends Category{
    @Id
    private String menuId;
    private String item;
    private String price;
}
