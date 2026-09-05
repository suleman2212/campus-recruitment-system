package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.Application;
import com.example.College_Placement_Management.Entity.Placement_result;
import com.example.College_Placement_Management.Repository.ApplicationRepository;
import com.example.College_Placement_Management.Repository.PlacementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlacementService {
    @Autowired
    PlacementRepository placementRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    public Placement_result insert(Placement_result placementResult,Long aid)
    {
        Application application=applicationRepository.findById(aid).orElse(null);
        placementResult.setApplication(application);
        return placementRepository.save(placementResult);
    }

    public List<Placement_result> getData()
    {
        return placementRepository.findAll();
    }

    public Placement_result getByid(Long id)
    {
        return placementRepository.findById(id).orElse(null);
    }

    public Placement_result update(Placement_result placementResult , Long id)
    {
        Placement_result pr =placementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Placement result not found: " + id));
        pr.setSelection_status(placementResult.getSelection_status());
        pr.setPackage_offered(placementResult.getPackage_offered());
        pr.setRemarks(placementResult.getRemarks());
        pr.setResult_date(placementResult.getResult_date());
        return placementRepository.save(pr);
    }

    public String delById(Long id)
    {
        placementRepository.deleteById(id);
        return id+" is successfully deleted";
    }
}
