package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Application;
import com.example.College_Placement_Management.Service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/insert/{sid}/{rid}")
    public ResponseEntity<Application> insert(
            @RequestBody Application application,
            @PathVariable Long sid,
            @PathVariable Long rid) {
        return new ResponseEntity<>(applicationService.insert(application, sid, rid), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Application>> fetch() {
        return ResponseEntity.ok(applicationService.fetch());
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Application> fetchbyid(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.fetchbyid(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Application> update(
            @PathVariable Long id,
            @RequestBody Application application) {
        return ResponseEntity.ok(applicationService.update(id, application));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.delete(id));
    }
}