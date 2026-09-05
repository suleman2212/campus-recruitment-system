package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.Company;
import com.example.College_Placement_Management.Entity.HiringRequirement;
import com.example.College_Placement_Management.Entity.Job_Notification;
import com.example.College_Placement_Management.Repository.CompanyRepository;
import com.example.College_Placement_Management.Repository.HiringRRepository;
import com.example.College_Placement_Management.Repository.Job_nRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
        Company company=companyRepository.findById(cid).orElse(null);
        hiringRequirement.setCompany(company);
        HiringRequirement saved = hiringRRepository.save(hiringRequirement);

        // Automatically publish a notification the moment a company sends a
        // hiring requirement, so students / colleges see a pop-up alert.
        Job_Notification notification = new Job_Notification();
        notification.setHiringRequirement(saved);
        String companyName = company != null ? company.getCompany_name() : "A company";
        notification.setTitle(companyName + " is hiring: " + saved.getJobRole());
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
        return hiringRRepository.findById(id).orElse(null);
    }

    public HiringRequirement update(Long id, HiringRequirement hiringRequirement)
    {
        HiringRequirement h=hiringRRepository.findById(id).orElse(null);
        h.setJob_type(hiringRequirement.getJob_type());
        h.setJobRole(hiringRequirement.getJobRole());
        h.setRequired_candidates(hiringRequirement.getRequired_candidates());
        h.setRequired_skills(hiringRequirement.getRequired_skills());
        h.setMin_cgpa(hiringRequirement.getMin_cgpa());
        h.setEligible_branches(hiringRequirement.getEligible_branches());
        h.setTarget_region(hiringRequirement.getTarget_region());
        h.setApplication_deadline(hiringRequirement.getApplication_deadline());
        h.setCreated_date(hiringRequirement.getCreated_date());
        return hiringRRepository.save(h);

    }

    public String delete(Long id)
    {
        hiringRRepository.deleteById(id);
        return "data deleted successfully";
    }
}
