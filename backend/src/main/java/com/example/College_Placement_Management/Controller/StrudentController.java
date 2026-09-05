package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Student;
import com.example.College_Placement_Management.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StrudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/insert/{cid}")
    public ResponseEntity<Student> insertdata(@RequestBody Student student, @PathVariable Long  cid)
    {
        try {
            return ResponseEntity.ok(studentService.insertdata(student, cid));
        } catch (Exception e) {
            return (ResponseEntity<Student>) ResponseEntity.badRequest();
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Student>> fetch()
    {
        try {
            return ResponseEntity.ok(studentService.fetch());
        } catch (Exception e) {
            return (ResponseEntity<List<Student>>) ResponseEntity.badRequest();
        }
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Student> fetchdata(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(studentService.fetchdata(id));
        } catch (Exception e) {
            return (ResponseEntity<Student>) ResponseEntity.badRequest();
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updatedata(@PathVariable Long id,@RequestBody Student student)
    {
        try {
            return ResponseEntity.ok(studentService.updatedata(id, student));
        }catch (Exception e)
        {
            return (ResponseEntity<Student>) ResponseEntity.badRequest();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletedata(@PathVariable Long id)
    {
        try {
            return ResponseEntity.ok(studentService.deletedata(id));
        }catch (Exception e)
        {
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
    }



}
