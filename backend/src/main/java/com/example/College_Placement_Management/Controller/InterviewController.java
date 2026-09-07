package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Interview_Schedule;
import com.example.College_Placement_Management.Service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviewSchedule")
public class InterviewController {
    @Autowired
    private InterviewService interviewService;

    @PostMapping("/insert/{aid}")
    public ResponseEntity<Interview_Schedule> insert(
            @RequestBody Interview_Schedule interviewSchedule,
            @PathVariable Long aid) {
        return new ResponseEntity<>(interviewService.insert(interviewSchedule, aid), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Interview_Schedule>> getData() {
        return ResponseEntity.ok(interviewService.getData());
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Interview_Schedule> getByid(@PathVariable Long id) {
        return ResponseEntity.ok(interviewService.getByid(id));
    }

    @PutMapping("/update")
    public ResponseEntity<Interview_Schedule> update(@RequestBody Interview_Schedule interviewSchedule) {
        return ResponseEntity.ok(interviewService.update(interviewSchedule));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Interview_Schedule> updateWithId(
            @PathVariable Long id,
            @RequestBody Interview_Schedule interviewSchedule) {
        interviewSchedule.setInterview_id(id);
        return ResponseEntity.ok(interviewService.update(interviewSchedule));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delById(@PathVariable Long id) {
        return ResponseEntity.ok(interviewService.delById(id));
    }
}
