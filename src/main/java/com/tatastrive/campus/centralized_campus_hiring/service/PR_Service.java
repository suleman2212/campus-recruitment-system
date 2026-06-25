package com.tatastrive.campus.centralized_campus_hiring.service;

import com.tatastrive.campus.centralized_campus_hiring.entity.Placement_Result;
import com.tatastrive.campus.centralized_campus_hiring.repo.PR_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PR_Service
{

    @Autowired
    private PR_Repo prRepo;

    public Placement_Result insert(Placement_Result placementResult)
    {
        //prRepo.findById(placementResult.getResult_id()).orElse(null);
        return prRepo.save(placementResult);
    }

    public List<Placement_Result> getData()
    {
        return prRepo.findAll();
    }

    public Placement_Result getByid(Long id)
    {
        return prRepo.findById(id).orElse(null);
    }

    public Placement_Result update(Placement_Result placementResult)
    {
        Placement_Result pr =prRepo.findById(placementResult.getResult_id()).orElse(null);
        pr.setApplication_id(placementResult.getApplication_id());
        pr.setRemarks(placementResult.getRemarks());
        pr.setPackage_offered(placementResult.getPackage_offered());
        pr.setResult_data(placementResult.getResult_data());
        pr.setResult_id(placementResult.getResult_id());
        pr.setSelection_status(placementResult.getSelection_status());
        return prRepo.save(pr);
    }

    public String delById(Long id)
    {
        prRepo.deleteById(id);
        return id+" is successfully deleted";
    }
}
