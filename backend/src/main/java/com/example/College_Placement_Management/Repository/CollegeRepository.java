package com.example.College_Placement_Management.Repository;

import com.example.College_Placement_Management.Entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends JpaRepository<College,Long> {
}
