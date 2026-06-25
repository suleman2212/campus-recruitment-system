package com.tatastrive.campus.centralized_campus_hiring.controller;

import com.tatastrive.campus.centralized_campus_hiring.entity.Interview_Schedule;
import com.tatastrive.campus.centralized_campus_hiring.service.IS_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviewSchedule")
public class IS_Controller
{
    @Autowired
    private IS_Service isService;

    @PostMapping("/insert")
    public Interview_Schedule insert(@RequestBody Interview_Schedule interviewSchedule)
    {
        return isService.insert(interviewSchedule);
    }

    @GetMapping("/fetch")
    public List<Interview_Schedule> getData()
    {
        return isService.getData();
    }

    @GetMapping("/fetch/{id}")
    public Interview_Schedule getByid(@PathVariable Long id)
    {
        return isService.getByid(id);
    }

    @PostMapping("/update")
    public Interview_Schedule update(@RequestBody Interview_Schedule interviewSchedule)
    {
        return isService.update(interviewSchedule);
    }

    @DeleteMapping("/del/{id}")
    public String delById(@PathVariable Long id)
    {
        return isService.delById(id);
    }

}
