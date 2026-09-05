package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Company;
import com.example.College_Placement_Management.Entity.HiringRequirement;
import com.example.College_Placement_Management.Service.HiringRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/HiringR")
public class HiringRController {
    @Autowired
    HiringRService hiringRService;

    @PostMapping("/insert/{cid}")
    public ResponseEntity<HiringRequirement> insert(@RequestBody HiringRequirement hiringRequirement, @PathVariable Long cid)
    {
        try {
            return ResponseEntity.ok(hiringRService.insert(hiringRequirement, cid));
        } catch (Exception e) {
            return (ResponseEntity<HiringRequirement>) ResponseEntity.badRequest();
        }
    }
    @GetMapping("/fetch")
    public ResponseEntity<List<HiringRequirement>> fetch()
    {
        try {
            return ResponseEntity.ok(hiringRService.fetch());
        } catch (Exception e) {
            return (ResponseEntity<List<HiringRequirement>>) ResponseEntity.badRequest();
        }
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<HiringRequirement> fetchbyid(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(hiringRService.fetchbyid(id));
        } catch (Exception e) {
            return (ResponseEntity<HiringRequirement>) ResponseEntity.badRequest();
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<HiringRequirement> upadte(@PathVariable Long id,@RequestBody HiringRequirement hiringRequirement)
    {
        try {
            return ResponseEntity.ok(hiringRService.update(id, hiringRequirement));
        } catch (Exception e) {
            return (ResponseEntity<HiringRequirement>) ResponseEntity.badRequest();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(hiringRService.delete(id));
        }catch (Exception e){
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
    }

}
