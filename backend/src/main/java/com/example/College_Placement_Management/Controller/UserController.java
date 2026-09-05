package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Users;
import com.example.College_Placement_Management.Service.AuthService;
import com.example.College_Placement_Management.Service.UserService;
import com.example.College_Placement_Management.dto.AuthResponse;
import com.example.College_Placement_Management.dto.CollegeRegisterRequest;
import com.example.College_Placement_Management.dto.CompanyRegisterRequest;
import com.example.College_Placement_Management.dto.RegisterResponse;
import com.example.College_Placement_Management.dto.StudentRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    // ---- role-specific self-registration ----

    @PostMapping("/register/student")
    public ResponseEntity<?> registerStudent(@RequestBody StudentRegisterRequest request) {
        try {
            RegisterResponse response = authService.registerStudent(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not create the student account.");
        }
    }

    @PostMapping("/register/company")
    public ResponseEntity<?> registerCompany(@RequestBody CompanyRegisterRequest request) {
        try {
            RegisterResponse response = authService.registerCompany(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not create the company account.");
        }
    }

    @PostMapping("/register/college")
    public ResponseEntity<?> registerCollege(@RequestBody CollegeRegisterRequest request) {
        try {
            RegisterResponse response = authService.registerCollege(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not create the college account.");
        }
    }

    // ---- login ----

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        AuthResponse response = authService.login(user.getUsername(), user.getPassword());
        if (response == null) {
            return ResponseEntity.status(401).body("Invalid username or password.");
        }
        return ResponseEntity.ok(response);
    }

    // ---- admin / directory ----

    @GetMapping("/fetch/{id}")
    public Users fetchByid(@PathVariable Integer id)
    {
        return userService.fetch(id);
    }

    @GetMapping("/fetch")
    public List<Users> fetch()
    {
        return userService.fetch();
    }
}
