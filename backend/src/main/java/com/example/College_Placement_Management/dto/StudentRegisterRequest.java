package com.example.College_Placement_Management.dto;

import lombok.Data;

@Data
public class StudentRegisterRequest {
    // login credentials
    private String username;
    private String password;

    // student profile
    private Long collegeId;
    private String name;
    private String email;
    private Long phone;
    private String branch;
    private Double cgpa;
    private Long graduationYear;
    private String skills;
    private String linkedin;
    private String github;
    private String resume;
}
