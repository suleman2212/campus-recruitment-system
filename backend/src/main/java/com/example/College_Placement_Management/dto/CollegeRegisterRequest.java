package com.example.College_Placement_Management.dto;

import lombok.Data;

@Data
public class CollegeRegisterRequest {
    // login credentials
    private String username;
    private String password;

    // college profile
    private String cname;
    private String region;
    private String address;
    private String pofficer;
    private String pemail;
    private Long pnumber;
    private String infraScore;
    private Long studentStrength;
}
