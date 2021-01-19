package com.example.hamburger.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document(collection = "reservations")
@Data
public class Reservation {
    @Id
    private String reservationId;
    private String fullName;
    private String contact;
    private String email;
    private Date dateOfBooking;
    private Date dateOfEvent;
    private String eventCategory;
    private String eventPackageSelected;
    private String status;
}