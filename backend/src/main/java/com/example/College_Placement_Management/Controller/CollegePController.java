package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.College_Participation;
import com.example.College_Placement_Management.Service.CollegePService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participation")
public class CollegePController {
    @Autowired
    CollegePService collegePService;

    @PostMapping("/insert/{rid}/{cid}")
    public ResponseEntity<College_Participation> insertdata(@RequestBody College_Participation collegeParticipation, @PathVariable Long rid, @PathVariable Long cid)
    {
        try {
            return ResponseEntity.ok(collegePService.insertdata(collegeParticipation, rid, cid));
        }catch (Exception e){
            return (ResponseEntity<College_Participation>) ResponseEntity.badRequest();
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<College_Participation>> getdetails()
    {
        try {
            return ResponseEntity.ok(collegePService.getdetails());
        }catch (Exception e){
            return (ResponseEntity<List<College_Participation>>) ResponseEntity.badRequest();
        }
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<College_Participation> getdetailsbyid(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(collegePService.getdetailsbyid(id));
        } catch (Exception e) {
            return (ResponseEntity<College_Participation>) ResponseEntity.badRequest();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<College_Participation> updateClgParticipation(@PathVariable Long id, @RequestBody College_Participation collegeParticipation )
    {
        try {
            return ResponseEntity.ok(collegePService.updateClgParticipation(id, collegeParticipation));
        } catch (Exception e) {
            return (ResponseEntity<College_Participation>) ResponseEntity .badRequest();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClgParticipation(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(collegePService.deleteClgParticipation(id));
        } catch (Exception e) {
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
}
}
