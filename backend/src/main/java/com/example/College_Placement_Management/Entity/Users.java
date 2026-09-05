package com.example.College_Placement_Management.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String username;

    private String password;

    // STUDENT, COMPANY, COLLEGE or ADMIN
    private String role;

    // id of the linked Student / Company / College row (null for ADMIN)
    private Long refId;
}