package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Job_Notification;
import com.example.College_Placement_Management.Service.Job_nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/JobNotification")
public class Job_nController
{
    @Autowired
    Job_nService jobNService;

    @PostMapping("/insert/{rid}")
    public ResponseEntity<Job_Notification> insertdata(@RequestBody Job_Notification jobNotification, @PathVariable Long rid)
    {
        try {
            return ResponseEntity.ok(jobNService.insertdata(jobNotification, rid));
        } catch (Exception e) {
            return (ResponseEntity<Job_Notification>) ResponseEntity.badRequest();
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Job_Notification>> fetch()
    {
        try {
            return ResponseEntity.ok(jobNService.fetch());
        } catch (Exception e) {
            return (ResponseEntity<List<Job_Notification>>) ResponseEntity.badRequest();
        }
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Job_Notification> fetchbyid(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(jobNService.fetchbyid(id));
        }catch (Exception e){
            return (ResponseEntity<Job_Notification>) ResponseEntity.badRequest();
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Job_Notification> update(@PathVariable Long id, @RequestBody Job_Notification jobNotification)
    {
        try {
            return ResponseEntity.ok(jobNService.update(id, jobNotification));
        } catch (Exception e) {
            return (ResponseEntity<Job_Notification>) ResponseEntity.badRequest();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(jobNService.delete(id));
        }catch (Exception e){
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
    }
}
