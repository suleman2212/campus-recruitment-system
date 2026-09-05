package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Placement_result;
import com.example.College_Placement_Management.Service.PlacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/PlacementResult")
public class PlacementController {
    @Autowired
    PlacementService placementService;

    @PostMapping("/insert/{aid}")
    public ResponseEntity<Placement_result> insert(@RequestBody Placement_result placementResult, @PathVariable Long aid)
    {
        try {
            return ResponseEntity.ok(placementService.insert(placementResult, aid));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Placement_result>> getData()
    {
        try {
            return ResponseEntity.ok(placementService.getData());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Placement_result> getByid(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(placementService.getByid(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Placement_result> update(@RequestBody Placement_result placementResult, @PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(placementService.update(placementResult, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delById(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(placementService.delById(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}


