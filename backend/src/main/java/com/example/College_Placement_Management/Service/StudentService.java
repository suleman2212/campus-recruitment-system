package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.College;
import com.example.College_Placement_Management.Entity.Student;
import com.example.College_Placement_Management.Repository.CollegeRepository;
import com.example.College_Placement_Management.Repository.StudentRepository;
import com.example.College_Placement_Management.configuration.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CollegeRepository collegeRepository;

    public Student insertdata(Student student, Long cid)
    {
        College college = collegeRepository.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("College not found with id: " + cid));
        student.setCollege(college);
        return studentRepository.save(student);
    }

    public Student fetchdata(Long id)
    {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public Student updatedata(Long id, Student student)
    {
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        s.setName(student.getName());
        s.setEmail(student.getEmail());
        s.setPhone(student.getPhone());
        s.setBranch(student.getBranch());
        s.setCgpa(student.getCgpa());
        s.setGraduation_year(student.getGraduation_year());
        s.setSkills(student.getSkills());
        s.setLinkdin(student.getLinkdin() != null ? student.getLinkdin() : student.getLinkedin());
        s.setGithub(student.getGithub());
        s.setResume(student.getResume());
        return studentRepository.save(s);
    }

    public String deletedata(Long id)
    {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
        return "data deleted successfully";
    }

    public List<Student> fetch()
    {
        return studentRepository.findAll();
    }
}
