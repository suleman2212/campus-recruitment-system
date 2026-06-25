package com.tatastrive.campus.centralized_campus_hiring.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.util.Timer;

@Entity
@Data
public class Interview_Schedule
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interview_id;
    //@Column(name = "")
    private Long application_id;
    private String round_name;
    private Date date;
    private Time time;
    private String venue;
    private String mode;
    private String status;

}
