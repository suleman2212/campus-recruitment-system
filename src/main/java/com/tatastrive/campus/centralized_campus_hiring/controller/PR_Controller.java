package com.tatastrive.campus.centralized_campus_hiring.controller;

import com.tatastrive.campus.centralized_campus_hiring.entity.Interview_Schedule;
import com.tatastrive.campus.centralized_campus_hiring.entity.Placement_Result;
import com.tatastrive.campus.centralized_campus_hiring.service.PR_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/PlacementResult")
public class PR_Controller
{
    @Autowired
    private PR_Service prService;

    @PostMapping("/insert")
    public Placement_Result insert(@RequestBody Placement_Result placementResult)
    {
        return prService.insert(placementResult);
    }

    @GetMapping("/fetch")
    public List<Placement_Result> getData()
    {
        return prService.getData();
    }

    @GetMapping("/fetch/{id}")
    public Placement_Result getByid(@PathVariable Long id)
    {
        return prService.getByid(id);
    }

    @PostMapping("/update")
    public Placement_Result update(@RequestBody Placement_Result placementResult)
    {
        return prService.update(placementResult);
    }

    @DeleteMapping("/del/{id}")
    public String delById(@PathVariable Long id)
    {
        return prService.delById(id);
    }

}
