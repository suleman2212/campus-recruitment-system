package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.College;
import com.example.College_Placement_Management.Service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/college")
public class CollegeController {
    @Autowired
    CollegeService collegeService;

    @PostMapping("/insert")
    public ResponseEntity<College> insertdata(@RequestBody College college)
    {
        try {
            return ResponseEntity.ok(collegeService.insertdata(college));
        } catch (Exception e) {
            return (ResponseEntity<College>) ResponseEntity.badRequest();
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<College>> fetch()
    {
        try {
            return ResponseEntity.ok(collegeService.fetch());
        }catch (Exception e){
            return (ResponseEntity<List<College>>) ResponseEntity.badRequest();
        }
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<College> fetchdata(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(collegeService.fetchdata(id));
        }catch (Exception e){
            return (ResponseEntity<College>) ResponseEntity.badRequest();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<College> updateCollege(@PathVariable Long id, @RequestBody College college)
    {
        try {
            return ResponseEntity.ok(collegeService.updateCollege(id, college));
        }catch (Exception e){
            return (ResponseEntity<College>) ResponseEntity.badRequest();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCollege(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(collegeService.deleteCollege(id));
        } catch (Exception e) {
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
    }
}
