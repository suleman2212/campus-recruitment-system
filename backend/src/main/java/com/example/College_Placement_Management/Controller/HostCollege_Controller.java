package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Host_College;
import com.example.College_Placement_Management.Service.HostCollege_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hostclg")
public class HostCollege_Controller {
    @Autowired
    private HostCollege_Service hostCollegeService;

    @PostMapping("/insert/{rid}/{cid}")
    public ResponseEntity<Host_College> insertdata(
            @RequestBody Host_College hostClg,
            @PathVariable Long rid,
            @PathVariable Long cid) {
        return new ResponseEntity<>(hostCollegeService.insertdata(hostClg, rid, cid), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Host_College>> getdetails() {
        return ResponseEntity.ok(hostCollegeService.getdetails());
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Host_College> getdetailsbyid(@PathVariable Long id) {
        return ResponseEntity.ok(hostCollegeService.getdetailsbyid(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Host_College> updateHostClg(
            @PathVariable Long id,
            @RequestBody Host_College hostClg) {
        return ResponseEntity.ok(hostCollegeService.updateHostClg(id, hostClg));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHostClg(@PathVariable Long id) {
        return ResponseEntity.ok(hostCollegeService.deleteHostClg(id));
    }
}
