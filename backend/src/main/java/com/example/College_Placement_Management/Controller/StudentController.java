package com.example.College_Placement_Management.Controller;

import com.example.College_Placement_Management.Entity.Student;
import com.example.College_Placement_Management.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/insert/{cid}")
    public ResponseEntity<Student> insertdata(@RequestBody Student student, @PathVariable Long cid) {
        return new ResponseEntity<>(studentService.insertdata(student, cid), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Student>> fetch() {
        return ResponseEntity.ok(studentService.fetch());
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Student> fetchdata(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.fetchdata(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updatedata(@PathVariable Long id, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updatedata(id, student));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletedata(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.deletedata(id));
    }
}
