package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.Application;
import com.example.College_Placement_Management.Entity.HiringRequirement;
import com.example.College_Placement_Management.Entity.Student;
import com.example.College_Placement_Management.Repository.ApplicationRepository;
import com.example.College_Placement_Management.Repository.HiringRRepository;
import com.example.College_Placement_Management.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    HiringRRepository hiringRRepository;

    public Application insert(Application application,Long sid, Long rid)
    {
        Student student=studentRepository.findById(sid).orElse(null);
        HiringRequirement hiringRequirement=hiringRRepository.findById(rid).orElse(null);
        application.setStudent(student);
        application.setHiringRequirement(hiringRequirement);
         return applicationRepository.save(application);
    }

    public List<Application> fetch()
    {
        return applicationRepository.findAll();
    }

    public Application fetchbyid(Long id)
    {
        return applicationRepository.findById(id).orElse(null);
    }

    public Application update(Long id, Application application)
    {
        Application a= applicationRepository.findById(id).orElse(null);
        a.setApplication_status(application.getApplication_status());
        a.setApplied_date(application.getApplied_date());
        return applicationRepository.save(a);
    }

    public String delete(Long id)
    {
        applicationRepository.deleteById(id);
        return "data deleted successfully";
    }
}
