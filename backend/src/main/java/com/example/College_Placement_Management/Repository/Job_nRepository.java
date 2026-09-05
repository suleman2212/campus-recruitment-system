package com.example.College_Placement_Management.Repository;

import com.example.College_Placement_Management.Entity.Job_Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface Job_nRepository extends JpaRepository<Job_Notification,Long> {
}
