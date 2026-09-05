package com.example.College_Placement_Management.dto;

import lombok.Data;

@Data
public class CompanyRegisterRequest {
    // login credentials
    private String username;
    private String password;

    // company profile
    private String companyName;
    private String location;
    private String email;
    private String website;
    private Long phone;
}
