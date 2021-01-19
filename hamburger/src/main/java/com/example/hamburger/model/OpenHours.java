package com.example.hamburger.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hours")
@Data
public class OpenHours {
    @Id
    private String openHoursId;
    private String day;
    private String openTime;
    private String closeTime;
}

