package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Company;
import com.example.College_Placement_Management.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController
{
    @Autowired
    CompanyService companyService;

    @PostMapping("/insert")
    public ResponseEntity<Company> insert(@RequestBody Company company)
    {
        try {
            return ResponseEntity.ok(companyService.insert(company));
        } catch (Exception e) {
            return (ResponseEntity<Company>) ResponseEntity.badRequest();
        }
    }
    @GetMapping("/fetch")
    public ResponseEntity<List<Company>> fetch()
    {
        try {
            return ResponseEntity.ok(companyService.fetch());
        } catch (Exception e) {
            return (ResponseEntity<List<Company>>) ResponseEntity.badRequest();
        }
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Company> fetchbyid(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(companyService.fetchbyid(id));
        } catch (Exception e) {
            return (ResponseEntity<Company>) ResponseEntity.badRequest();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Company> update(@PathVariable Long id, @RequestBody Company company)
    {
        try {
            return ResponseEntity.ok(companyService.update(id, company));
        } catch (Exception e) {
            return (ResponseEntity<Company>) ResponseEntity.badRequest();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(companyService.delete(id));
        }catch (Exception e){
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
    }
}
