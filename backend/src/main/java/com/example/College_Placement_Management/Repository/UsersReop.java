package com.example.College_Placement_Management.Repository;

import com.example.College_Placement_Management.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersReop extends JpaRepository<Users,Integer>
{
    Users getByusername(String name);
}
