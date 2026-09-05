package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.College_Participation;
import com.example.College_Placement_Management.Entity.Company;
import com.example.College_Placement_Management.Entity.HiringRequirement;
import com.example.College_Placement_Management.Repository.CollegePRepository;
import com.example.College_Placement_Management.Repository.CompanyRepository;
import com.example.College_Placement_Management.Repository.HiringRRepository;
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

    public College_Participation insertdata(College_Participation collegeParticipation, Long rid, Long cid)
    {
        HiringRequirement hiringRequirement=hiringRRepository.findById(rid).orElse(null);
        Company company=companyRepository.findById(cid).orElse(null);
        collegeParticipation.setHiringRequirement(hiringRequirement);
        collegeParticipation.setCompany(company);
        return collegePRepository.save(collegeParticipation);
    }

    public List<College_Participation> getdetails()
    {
        return collegePRepository.findAll();
    }

    public College_Participation getdetailsbyid(Long id)
    {
        return collegePRepository.findById(id).orElse(null);
    }

    public College_Participation updateClgParticipation(Long id, College_Participation collegeParticipation) {
        College_Participation c = collegePRepository.findById(id).orElse(null);

        c.setSelection_status(collegeParticipation.getSelection_status());
        c.setSelected_date(collegeParticipation.getSelected_date());

        return collegePRepository.save(c);
    }

    public String deleteClgParticipation(Long id)
    {
        collegePRepository.deleteById(id);
        return "data deleted successfully";
    }
}
