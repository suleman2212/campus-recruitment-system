package com.example.College_Placement_Management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Student_id;

    @ManyToOne
    @JoinColumn(name="college_id")
    private College college;

    private String Name;
    private String Email;
    private Long Phone;
    private String branch;
    private Integer cgpa;
    private Long graduation_year;
    private String skills;
    private String linkdin;
    private String github;
    private String Resume;
}
