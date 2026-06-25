package com.tatastrive.campus.centralized_campus_hiring.repo;

import com.tatastrive.campus.centralized_campus_hiring.entity.Placement_Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PR_Repo extends JpaRepository<Placement_Result,Long>
{
}
