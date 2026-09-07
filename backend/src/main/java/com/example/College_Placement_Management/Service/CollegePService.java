package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.College;
import com.example.College_Placement_Management.Entity.College_Participation;
import com.example.College_Placement_Management.Entity.Company;
import com.example.College_Placement_Management.Entity.HiringRequirement;
import com.example.College_Placement_Management.Repository.CollegePRepository;
import com.example.College_Placement_Management.Repository.CollegeRepository;
import com.example.College_Placement_Management.Repository.CompanyRepository;
import com.example.College_Placement_Management.Repository.HiringRRepository;
import com.example.College_Placement_Management.configuration.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegePService {
    @Autowired
    CollegePRepository collegePRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    HiringRRepository hiringRRepository;
    @Autowired
    CollegeRepository collegeRepository;

    public College_Participation insertdata(College_Participation collegeParticipation, Long rid, Long cid)
    {
        HiringRequirement hiringRequirement = hiringRRepository.findById(rid)
                .orElseThrow(() -> new ResourceNotFoundException("Hiring requirement not found with id: " + rid));
        Company company = companyRepository.findById(cid)
                .orElse(hiringRequirement.getCompany());
        
        collegeParticipation.setHiringRequirement(hiringRequirement);
        collegeParticipation.setCompany(company);

        // If a college is attached in the payload, resolve and set it
        if (collegeParticipation.getCollege() != null && collegeParticipation.getCollege().getCid() != null) {
            College college = collegeRepository.findById(collegeParticipation.getCollege().getCid()).orElse(null);
            collegeParticipation.setCollege(college);
        }

        return collegePRepository.save(collegeParticipation);
    }

    public List<College_Participation> getdetails()
    {
        return collegePRepository.findAll();
    }

    public College_Participation getdetailsbyid(Long id)
    {
        return collegePRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("College participation not found with id: " + id));
    }

    public College_Participation updateClgParticipation(Long id, College_Participation collegeParticipation) {
        College_Participation c = collegePRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("College participation not found with id: " + id));

        if (collegeParticipation.getSelection_status() != null) {
            c.setSelection_status(collegeParticipation.getSelection_status());
        }
        if (collegeParticipation.getSelected_date() != null) {
            c.setSelected_date(collegeParticipation.getSelected_date());
        }
        if (collegeParticipation.getCollege() != null && collegeParticipation.getCollege().getCid() != null) {
            College college = collegeRepository.findById(collegeParticipation.getCollege().getCid()).orElse(null);
            c.setCollege(college);
        }

        return collegePRepository.save(c);
    }

    public String deleteClgParticipation(Long id)
    {
        if (!collegePRepository.existsById(id)) {
            throw new ResourceNotFoundException("College participation not found with id: " + id);
        }
        collegePRepository.deleteById(id);
        return "data deleted successfully";
    }
}
