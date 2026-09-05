package com.example.College_Placement_Management.Repository;

import com.example.College_Placement_Management.Entity.Host_College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostCollege_Repository extends JpaRepository<Host_College, Long> {
}
