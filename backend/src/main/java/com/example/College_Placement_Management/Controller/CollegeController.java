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
    private CollegeService collegeService;

    @PostMapping("/insert")
    public ResponseEntity<College> insertdata(@RequestBody College college) {
        return new ResponseEntity<>(collegeService.insertdata(college), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<College>> fetch() {
        return ResponseEntity.ok(collegeService.fetch());
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<College> fetchdata(@PathVariable Long id) {
        return ResponseEntity.ok(collegeService.fetchdata(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<College> updateCollege(@PathVariable Long id, @RequestBody College college) {
        return ResponseEntity.ok(collegeService.updateCollege(id, college));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCollege(@PathVariable Long id) {
        return ResponseEntity.ok(collegeService.deleteCollege(id));
    }
}
