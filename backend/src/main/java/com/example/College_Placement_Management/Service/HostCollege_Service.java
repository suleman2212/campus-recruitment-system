package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.College;
import com.example.College_Placement_Management.Entity.HiringRequirement;
import com.example.College_Placement_Management.Entity.Host_College;
import com.example.College_Placement_Management.Repository.CollegeRepository;
import com.example.College_Placement_Management.Repository.HiringRRepository;
import com.example.College_Placement_Management.Repository.HostCollege_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostCollege_Service {
    @Autowired
    HostCollege_Repository hostCollegeRepository;
    @Autowired
    CollegeRepository collegeRepository;
    @Autowired
    HiringRRepository hiringRRepository;
    
    public Host_College insertdata(Host_College hostClg, Long rid, Long cid)
    {
        HiringRequirement hiringRequirement=hiringRRepository.findById(rid).orElse(null);
        College college=collegeRepository.findById(cid).orElse(null);
        hostClg.setHiringRequirement(hiringRequirement);
        hostClg.setCollege(college);
        return hostCollegeRepository.save(hostClg);
    }

    public List<Host_College> getdetails()
    {
        return hostCollegeRepository.findAll();
    }

    public Host_College getdetailsbyid(Long id)
    {
        return hostCollegeRepository.findById(id).orElse(null);
    }

    public Host_College updateHostClg(Long id, Host_College hostClg)
    {
        Host_College h = hostCollegeRepository.findById(id).orElse(null);

        h.setInterview_date(hostClg.getInterview_date());
        h.setVenue(hostClg.getVenue());
        h.setSelection_reason(hostClg.getSelection_reason());

        return hostCollegeRepository.save(h);
    }

    public String deleteHostClg(Long id)
    {
        hostCollegeRepository.deleteById(id);
        return "data deleted successfully";
    }
}
