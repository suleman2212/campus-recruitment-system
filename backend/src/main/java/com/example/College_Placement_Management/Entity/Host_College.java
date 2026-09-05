package com.example.College_Placement_Management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Host_College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hid;

    @ManyToOne
    @JoinColumn(name="requirement_id")
    private HiringRequirement hiringRequirement;

    @ManyToOne
    @JoinColumn(name="college_id")
    private College college;

    private String interview_date;
    private String venue;
    private String Selection_reason;
}
