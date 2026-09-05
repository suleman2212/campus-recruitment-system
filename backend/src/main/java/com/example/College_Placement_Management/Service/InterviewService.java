package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.Application;
import com.example.College_Placement_Management.Entity.Interview_Schedule;
import com.example.College_Placement_Management.Repository.ApplicationRepository;
import com.example.College_Placement_Management.Repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewService {
    @Autowired
    InterviewRepository interviewRepository;
    @Autowired
    ApplicationRepository applicationRepository;

    public Interview_Schedule insert(Interview_Schedule interviewSchedule, Long aid)
    {
        Application application=applicationRepository.findById(aid).orElse(null);
        interviewSchedule.setApplication(application);
        return interviewRepository.save(interviewSchedule);
    }

    public List<Interview_Schedule> getData()
    {
        return interviewRepository.findAll();
    }

    public Interview_Schedule getByid(Long id)
    {
        return interviewRepository.findById(id).orElse(null);
    }

    public Interview_Schedule update(Interview_Schedule interviewSchedule)
    {
        Interview_Schedule in =interviewRepository.findById(interviewSchedule.getInterview_id())
                .orElseThrow(() -> new RuntimeException("Interview schedule not found: " + interviewSchedule.getInterview_id()));
        in.setDate(interviewSchedule.getDate());
        in.setMode(interviewSchedule.getMode());
        in.setStatus(interviewSchedule.getStatus());
        in.setTime(interviewSchedule.getTime());
        in.setVenue(interviewSchedule.getVenue());
        in.setRound_name(interviewSchedule.getRound_name());
        return interviewRepository.save(in);
    }

    public String delById(Long id)
    {
        interviewRepository.deleteById(id);
        return id+" is Successfully deleted";
    }
}
