package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Application;
import com.example.College_Placement_Management.Service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// KEEP THIS ONE
import java.util.List;

// REMOVED: import org.hibernate.mapping.List;

@RestController
@RequestMapping("/application")
public class ApplicationController
{
    @Autowired
    ApplicationService applicationService;

    @PostMapping("/insert/{sid}/{rid}")
    public ResponseEntity<Application> insert(@RequestBody Application application, @PathVariable Long sid, @PathVariable Long rid)
    {
        try {
            return ResponseEntity.ok(applicationService.insert(application, sid, rid));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Application>> fetch()
    {
        try {
            return ResponseEntity.ok(applicationService.fetch());
        } catch (Exception e){
            // Fixed the type casting issue here as well
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Application> fetchbyid(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(applicationService.fetchbyid(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Application> update(@PathVariable Long id, @RequestBody Application application)
    {
        try {
            return ResponseEntity.ok(applicationService.update(id, application));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(applicationService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}