package com.example.College_Placement_Management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class College_Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @ManyToOne
    @JoinColumn(name="requirement_id")
    private HiringRequirement hiringRequirement;

    @ManyToOne
    @JoinColumn(name="Company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name="college_id")
    private College college;

    private String selection_status;
    private String selected_date;
}
