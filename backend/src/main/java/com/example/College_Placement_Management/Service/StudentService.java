package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.College;
import com.example.College_Placement_Management.Entity.Student;
import com.example.College_Placement_Management.Repository.CollegeRepository;
import com.example.College_Placement_Management.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CollegeRepository collegeRepository;
    public Student insertdata(Student student,Long cid)
    {
        College college = collegeRepository.findById(cid).orElse(null);
        student.setCollege(college);
        return studentRepository.save(student);
    }

    public Student fetchdata(Long id)
    {
        return studentRepository.findById(id).orElse(null);
    }

    public Student updatedata(Long id, Student student)
    {
        Student s= studentRepository.findById(id).orElse(null);
        s.setName(student.getName());
        s.setEmail(student.getEmail());
        s.setPhone(student.getPhone());
        s.setBranch(student.getBranch());
        s.setCgpa(student.getCgpa());
        s.setGraduation_year((student.getGraduation_year()));
        s.setSkills(student.getSkills());
        s.setLinkdin(student.getLinkdin());
        s.setGithub(student.getGithub());
        s.setResume(student.getResume());
        return studentRepository.save(s);

    }

    public String deletedata(Long id)
    {
        studentRepository.deleteById(id);
        return "data deleted successfully";
    }

    public List<Student> fetch()
    {
        return studentRepository.findAll();
    }
}
