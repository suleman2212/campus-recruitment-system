package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.Application;
import com.example.College_Placement_Management.Entity.HiringRequirement;
import com.example.College_Placement_Management.Entity.Student;
import com.example.College_Placement_Management.Repository.ApplicationRepository;
import com.example.College_Placement_Management.Repository.HiringRRepository;
import com.example.College_Placement_Management.Repository.StudentRepository;
import com.example.College_Placement_Management.configuration.ResourceNotFoundException;
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

    public Application insert(Application application, Long sid, Long rid)
    {
        Student student = studentRepository.findById(sid)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + sid));
        HiringRequirement hiringRequirement = hiringRRepository.findById(rid)
                .orElseThrow(() -> new ResourceNotFoundException("Hiring requirement not found with id: " + rid));
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
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
    }

    public Application update(Long id, Application application)
    {
        Application a = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        if (application.getApplication_status() != null) {
            a.setApplication_status(application.getApplication_status());
        }
        if (application.getApplied_date() != null) {
            a.setApplied_date(application.getApplied_date());
        }
        return applicationRepository.save(a);
    }

    public String delete(Long id)
    {
        if (!applicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Application not found with id: " + id);
        }
        applicationRepository.deleteById(id);
        return "data deleted successfully";
    }
}
