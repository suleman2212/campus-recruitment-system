package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Placement_result;
import com.example.College_Placement_Management.Service.PlacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/PlacementResult")
public class PlacementController {
    @Autowired
    private PlacementService placementService;

    @PostMapping("/insert/{aid}")
    public ResponseEntity<Placement_result> insert(
            @RequestBody Placement_result placementResult,
            @PathVariable Long aid) {
        return new ResponseEntity<>(placementService.insert(placementResult, aid), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Placement_result>> getData() {
        return ResponseEntity.ok(placementService.getData());
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Placement_result> getByid(@PathVariable Long id) {
        return ResponseEntity.ok(placementService.getByid(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Placement_result> update(
            @RequestBody Placement_result placementResult,
            @PathVariable Long id) {
        return ResponseEntity.ok(placementService.update(placementResult, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delById(@PathVariable Long id) {
        return ResponseEntity.ok(placementService.delById(id));
    }
}
