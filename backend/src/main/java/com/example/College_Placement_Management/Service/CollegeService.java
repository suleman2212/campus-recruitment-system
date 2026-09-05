package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.College;
import com.example.College_Placement_Management.Repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeService {
    @Autowired
    CollegeRepository collegeRepository;
    public College insertdata(College college)
    {
        return collegeRepository.save(college);
    }

    public List<College> fetch()
    {
        return collegeRepository.findAll();
    }

    public College fetchdata(Long id)
    {
        return collegeRepository.findById(id).orElse(null);

    }

    public College updateCollege(Long id, College college)
    {
        College c = collegeRepository.findById(id).orElse(null);

        c.setCname(college.getCname());
        c.setRegion(college.getRegion());
        c.setAddresss(college.getAddresss());
        c.setPofficer(college.getPofficer());
        c.setPemail(college.getPemail());
        c.setPnumber(college.getPnumber());
        c.setInfra_score(college.getInfra_score());
        c.setStudent_strength(college.getStudent_strength());

        return collegeRepository.save(c);
    }

    public String deleteCollege(Long id)
    {
        collegeRepository.deleteById(id);
        return "Data deleted successfully";
    }
}
