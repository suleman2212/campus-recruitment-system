package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.Users;
import com.example.College_Placement_Management.Repository.UsersReop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService
{
    @Autowired
    private UsersReop repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Users users = repo.getByusername(username);
        if (users == null) {
            throw new UsernameNotFoundException("No user found with username " + username);
        }
        return User
                .builder()
                .username(users.getUsername())
                .password(users.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }

    public Users fetch(Integer id)
    {
        return repo.findById(id).orElse(null);
    }

    public List<Users> fetch()
    {
        return repo.findAll();
    }
}
