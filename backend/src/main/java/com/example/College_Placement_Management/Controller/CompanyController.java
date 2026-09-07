package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Company;
import com.example.College_Placement_Management.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("/insert")
    public ResponseEntity<Company> insert(@RequestBody Company company) {
        return new ResponseEntity<>(companyService.insert(company), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Company>> fetch() {
        return ResponseEntity.ok(companyService.fetch());
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Company> fetchbyid(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.fetchbyid(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Company> update(@PathVariable Long id, @RequestBody Company company) {
        return ResponseEntity.ok(companyService.update(id, company));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.delete(id));
    }
}
