package com.tatastrive.campus.centralized_campus_hiring.service;

import com.tatastrive.campus.centralized_campus_hiring.entity.Interview_Schedule;
import com.tatastrive.campus.centralized_campus_hiring.repo.IS_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IS_Service
{
    @Autowired
    private IS_Repo isRepo;

    public Interview_Schedule insert(Interview_Schedule interviewSchedule)
    {
        return isRepo.save(interviewSchedule);
    }

    public List<Interview_Schedule> getData()
    {
        return isRepo.findAll();
    }

    public Interview_Schedule getByid(Long id)
    {
        return isRepo.findById(id).orElse(null);
    }

    public Interview_Schedule update(Interview_Schedule interviewSchedule)
    {
        Interview_Schedule in =isRepo.findById(interviewSchedule.getInterview_id()).orElse(null);
        in.setDate(interviewSchedule.getDate());
        in.setMode(interviewSchedule.getMode());
        in.setStatus(interviewSchedule.getStatus());
        in.setTime(interviewSchedule.getTime());
        in.setVenue(interviewSchedule.getVenue());
        in.setApplication_id(interviewSchedule.getApplication_id());
        in.setRound_name(interviewSchedule.getRound_name());
        return isRepo.save(in);
    }

    public String delById(Long id)
    {
        isRepo.deleteById(id);
        return id+" is Successfully deleted";
    }
}
