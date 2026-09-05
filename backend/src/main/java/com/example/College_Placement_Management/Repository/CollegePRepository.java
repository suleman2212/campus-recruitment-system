package com.example.College_Placement_Management.Repository;

import com.example.College_Placement_Management.Entity.College_Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegePRepository extends JpaRepository<College_Participation, Long> {
}
