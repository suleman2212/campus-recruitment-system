package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Interview_Schedule;
import com.example.College_Placement_Management.Service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviewSchedule")
public class InterviewController {
    @Autowired
     InterviewService interviewService;

    @PostMapping("/insert/{aid}")
    public ResponseEntity<Interview_Schedule> insert(@RequestBody Interview_Schedule interviewSchedule, @PathVariable Long aid)
    {
        try {
            return ResponseEntity.ok(interviewService.insert(interviewSchedule, aid));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Interview_Schedule>> getData()
    {
        try {
            return ResponseEntity.ok(interviewService.getData());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Interview_Schedule> getByid(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(interviewService.getByid(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Interview_Schedule> update(@RequestBody Interview_Schedule interviewSchedule)
    {
        try {
            return ResponseEntity.ok(interviewService.update(interviewSchedule));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delById(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(interviewService.delById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}

