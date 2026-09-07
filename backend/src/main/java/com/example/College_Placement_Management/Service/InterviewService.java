package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.Application;
import com.example.College_Placement_Management.Entity.Interview_Schedule;
import com.example.College_Placement_Management.Repository.ApplicationRepository;
import com.example.College_Placement_Management.Repository.InterviewRepository;
import com.example.College_Placement_Management.configuration.ResourceNotFoundException;
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
        Application application = applicationRepository.findById(aid)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + aid));
        interviewSchedule.setApplication(application);
        return interviewRepository.save(interviewSchedule);
    }

    public List<Interview_Schedule> getData()
    {
        return interviewRepository.findAll();
    }

    public Interview_Schedule getByid(Long id)
    {
        return interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview schedule not found with id: " + id));
    }

    public Interview_Schedule update(Interview_Schedule interviewSchedule)
    {
        if (interviewSchedule.getInterview_id() == null) {
            throw new IllegalArgumentException("interview_id is required for updating an interview schedule.");
        }
        Interview_Schedule in = interviewRepository.findById(interviewSchedule.getInterview_id())
                .orElseThrow(() -> new ResourceNotFoundException("Interview schedule not found: " + interviewSchedule.getInterview_id()));
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
        if (!interviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Interview schedule not found with id: " + id);
        }
        interviewRepository.deleteById(id);
        return id + " is Successfully deleted";
    }
}
