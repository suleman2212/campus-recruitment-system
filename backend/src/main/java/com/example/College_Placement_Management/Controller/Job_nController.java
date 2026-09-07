package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Job_Notification;
import com.example.College_Placement_Management.Service.Job_nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/JobNotification")
public class Job_nController {
    @Autowired
    private Job_nService jobNService;

    @PostMapping("/insert/{rid}")
    public ResponseEntity<Job_Notification> insertdata(
            @RequestBody Job_Notification jobNotification,
            @PathVariable Long rid) {
        return new ResponseEntity<>(jobNService.insertdata(jobNotification, rid), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Job_Notification>> fetch() {
        return ResponseEntity.ok(jobNService.fetch());
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Job_Notification> fetchbyid(@PathVariable Long id) {
        return ResponseEntity.ok(jobNService.fetchbyid(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Job_Notification> update(
            @PathVariable Long id,
            @RequestBody Job_Notification jobNotification) {
        return ResponseEntity.ok(jobNService.update(id, jobNotification));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(jobNService.delete(id));
    }
}
