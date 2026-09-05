package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Host_College;
import com.example.College_Placement_Management.Service.HostCollege_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hostclg")
public class HostCollege_Controller {
    @Autowired
    HostCollege_Service hostCollegeService;

    @PostMapping("/insert/{rid}/{cid}")
    public ResponseEntity<Host_College> insertdata(@RequestBody Host_College hostClg,@PathVariable Long rid, @PathVariable Long cid)
    {
        try {
            return ResponseEntity.ok(hostCollegeService.insertdata(hostClg, rid, cid));
        } catch (Exception e) {
            return (ResponseEntity<Host_College>) ResponseEntity.badRequest();
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Host_College>> getdetails()
    {
        try {
            return ResponseEntity.ok(hostCollegeService.getdetails());
        } catch (Exception e) {
            return (ResponseEntity<List<Host_College>>) ResponseEntity.badRequest();
        }
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Host_College> getdetailsbyid(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(hostCollegeService.getdetailsbyid(id));
        } catch (Exception e) {
            return (ResponseEntity<Host_College>) ResponseEntity.badRequest();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Host_College> updateHostClg(@PathVariable Long id, @RequestBody Host_College hostClg)
    {
        try {
            return ResponseEntity.ok(hostCollegeService.updateHostClg(id, hostClg));
        } catch (Exception e) {
            return (ResponseEntity<Host_College>) ResponseEntity.badRequest();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHostClg(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(hostCollegeService.deleteHostClg(id));
        } catch (Exception e) {
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
    }
}

