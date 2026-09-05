package com.example.College_Placement_Management.Repository;

import com.example.College_Placement_Management.Entity.Interview_Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepository extends JpaRepository<Interview_Schedule,Long> {
}
