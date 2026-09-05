package com.example.College_Placement_Management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Job_Notification
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long notification_id;

    @OneToOne
    @JoinColumn(name="requirement_id")
    private HiringRequirement hiringRequirement;

private String Title;
private String Discription;
private String publish_date;

}

