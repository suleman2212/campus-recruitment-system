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
public class Placement_result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long result_id;

    @OneToOne
    @JoinColumn(name="application_id")
    private Application application;

    // Selected / Not Selected / Waitlisted / Rejected
    private String selection_status;
    private String package_offered;
    private String remarks;
    private LocalDate result_date;
}
