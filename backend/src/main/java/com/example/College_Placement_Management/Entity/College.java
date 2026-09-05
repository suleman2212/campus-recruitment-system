package com.example.College_Placement_Management.Entity;

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
public class College
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    private String Cname;
    private String region;
    private String addresss;
    private String pofficer;
    private String pemail;
    private Long   pnumber;
    private String infra_score;
    private Long   student_strength;
}
