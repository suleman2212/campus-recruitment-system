package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.College_Participation;
import com.example.College_Placement_Management.Service.CollegePService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participation")
public class CollegePController {
    @Autowired
    private CollegePService collegePService;

    @PostMapping("/insert/{rid}/{cid}")
    public ResponseEntity<College_Participation> insertdata(
            @RequestBody College_Participation collegeParticipation,
            @PathVariable Long rid,
            @PathVariable Long cid) {
        return new ResponseEntity<>(collegePService.insertdata(collegeParticipation, rid, cid), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<College_Participation>> getdetails() {
        return ResponseEntity.ok(collegePService.getdetails());
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<College_Participation> getdetailsbyid(@PathVariable Long id) {
        return ResponseEntity.ok(collegePService.getdetailsbyid(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<College_Participation> updateClgParticipation(
            @PathVariable Long id,
            @RequestBody College_Participation collegeParticipation) {
        return ResponseEntity.ok(collegePService.updateClgParticipation(id, collegeParticipation));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClgParticipation(@PathVariable Long id) {
        return ResponseEntity.ok(collegePService.deleteClgParticipation(id));
    }
}
