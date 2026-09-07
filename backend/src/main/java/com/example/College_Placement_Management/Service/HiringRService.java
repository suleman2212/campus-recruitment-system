package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.Company;
import com.example.College_Placement_Management.Entity.HiringRequirement;
import com.example.College_Placement_Management.Entity.Job_Notification;
import com.example.College_Placement_Management.Repository.CompanyRepository;
import com.example.College_Placement_Management.Repository.HiringRRepository;
import com.example.College_Placement_Management.Repository.Job_nRepository;
import com.example.College_Placement_Management.configuration.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HiringRService {
    @Autowired
    HiringRRepository hiringRRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    Job_nRepository jobNRepository;

    public HiringRequirement insert(HiringRequirement hiringRequirement, Long cid)
    {
        Company company = companyRepository.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + cid));
        hiringRequirement.setCompany(company);
        if (hiringRequirement.getCreated_date() == null) {
            hiringRequirement.setCreated_date(System.currentTimeMillis());
        }
        HiringRequirement saved = hiringRRepository.save(hiringRequirement);

        // Automatically publish a notification the moment a company posts a
        // hiring requirement, so students / colleges see a pop-up alert.
        Job_Notification notification = new Job_Notification();
        notification.setHiringRequirement(saved);
        String companyName = company.getCompany_name() != null ? company.getCompany_name() : "A company";
        notification.setTitle(companyName + " is hiring: " + safe(saved.getJobRole()));
        notification.setDiscription(
                "New " + safe(saved.getJob_type()) + " opening for " + safe(saved.getJobRole()) +
                " at " + companyName +
                (saved.getRequired_candidates() != null ? " — " + saved.getRequired_candidates() + " openings" : "") +
                (saved.getEligible_branches() != null ? ", eligible branches: " + saved.getEligible_branches() : "") +
                (saved.getApplication_deadline() != null ? ". Apply before " + saved.getApplication_deadline() : "")
        );
        notification.setPublish_date(LocalDate.now().toString());
        jobNRepository.save(notification);

        return saved;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    public List<HiringRequirement> fetch()
    {
        return hiringRRepository.findAll();
    }

    public HiringRequirement fetchbyid(Long id)
    {
        return hiringRRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hiring requirement not found with id: " + id));
    }

    public HiringRequirement update(Long id, HiringRequirement hiringRequirement)
    {
        HiringRequirement h = hiringRRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hiring requirement not found with id: " + id));
        h.setJob_type(hiringRequirement.getJob_type());
        h.setJobRole(hiringRequirement.getJobRole());
        h.setRequired_candidates(hiringRequirement.getRequired_candidates());
        h.setRequired_skills(hiringRequirement.getRequired_skills());
        h.setMin_cgpa(hiringRequirement.getMin_cgpa());
        h.setEligible_branches(hiringRequirement.getEligible_branches());
        h.setTarget_region(hiringRequirement.getTarget_region());
        h.setApplication_deadline(hiringRequirement.getApplication_deadline());
        if (hiringRequirement.getCreated_date() != null) {
            h.setCreated_date(hiringRequirement.getCreated_date());
        }
        return hiringRRepository.save(h);
    }

    public String delete(Long id)
    {
        if (!hiringRRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hiring requirement not found with id: " + id);
        }
        hiringRRepository.deleteById(id);
        return "data deleted successfully";
    }
}
