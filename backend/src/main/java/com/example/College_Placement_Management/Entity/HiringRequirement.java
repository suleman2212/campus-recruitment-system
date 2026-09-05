package com.example.College_Placement_Management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HiringRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requirement_id;

    @OneToOne
    @JoinColumn(name="company_id")
    private Company company;
    
    private String jobRole;
    private String Job_type;
    private Long required_candidates;
    private String Required_skills;
    private Long min_cgpa;
    private String eligible_branches;
    private String target_region;
    private LocalDate application_deadline;
    private Long created_date;
}
