package com.tatastrive.campus.centralized_campus_hiring.repo;

import com.tatastrive.campus.centralized_campus_hiring.entity.Interview_Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IS_Repo extends JpaRepository<Interview_Schedule,Long> {
}
