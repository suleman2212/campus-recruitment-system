package com.example.College_Placement_Management.Repository;

import com.example.College_Placement_Management.Entity.Placement_result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacementRepository extends JpaRepository<Placement_result,Long> {
}
