package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.HiringRequirement;
import com.example.College_Placement_Management.Service.HiringRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/HiringR")
public class HiringRController {
    @Autowired
    private HiringRService hiringRService;

    @PostMapping("/insert/{cid}")
    public ResponseEntity<HiringRequirement> insert(@RequestBody HiringRequirement hiringRequirement, @PathVariable Long cid) {
        return new ResponseEntity<>(hiringRService.insert(hiringRequirement, cid), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<HiringRequirement>> fetch() {
        return ResponseEntity.ok(hiringRService.fetch());
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<HiringRequirement> fetchbyid(@PathVariable Long id) {
        return ResponseEntity.ok(hiringRService.fetchbyid(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HiringRequirement> update(@PathVariable Long id, @RequestBody HiringRequirement hiringRequirement) {
        return ResponseEntity.ok(hiringRService.update(id, hiringRequirement));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(hiringRService.delete(id));
    }
}
