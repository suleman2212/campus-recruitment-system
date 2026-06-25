package com.tatastrive.campus.centralized_campus_hiring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class Placement_Result
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long result_id;
    private Long application_id;
    private String selection_status;
    private String package_offered;
    private String remarks;
    private Date result_data;


}
