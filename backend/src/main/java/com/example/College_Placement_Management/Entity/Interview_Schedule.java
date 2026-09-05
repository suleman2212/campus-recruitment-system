package com.example.College_Placement_Management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity


public class Interview_Schedule
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interview_id;

    @ManyToOne
    @JoinColumn(name="application_id")
    private Application application;

    private String round_name;
    private LocalDate date;
    private LocalTime time;
    private String venue;
    private String mode;
    private String status;
}
