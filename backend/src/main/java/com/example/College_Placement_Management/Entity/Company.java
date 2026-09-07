package com.example.College_Placement_Management.Entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("company_id")
    @JsonAlias({"company_id", "Company_id", "companyId", "id"})
    private Long company_id;

    @JsonProperty("company_name")
    @JsonAlias({"company_name", "companyName", "Company_name"})
    private String company_name;

    @JsonProperty("location")
    @JsonAlias({"location", "Location"})
    private String Location;

    private String email;
    private String website;
    private Long phone;

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        this.Location = location;
    }
}
