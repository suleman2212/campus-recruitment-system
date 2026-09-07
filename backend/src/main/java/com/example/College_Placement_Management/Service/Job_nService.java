package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.HiringRequirement;
import com.example.College_Placement_Management.Entity.Job_Notification;
import com.example.College_Placement_Management.Repository.HiringRRepository;
import com.example.College_Placement_Management.Repository.Job_nRepository;
import com.example.College_Placement_Management.configuration.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Job_nService {
    @Autowired
    Job_nRepository jobNRepository;
    @Autowired
    HiringRRepository hiringRRepository;

    public Job_Notification insertdata(Job_Notification jobNotification, Long rid)
    {
        HiringRequirement hiringRequirement = hiringRRepository.findById(rid)
                .orElseThrow(() -> new ResourceNotFoundException("Hiring requirement not found with id: " + rid));
        jobNotification.setHiringRequirement(hiringRequirement);
        return jobNRepository.save(jobNotification);
    }

    public List<Job_Notification> fetch()
    {
        return jobNRepository.findAll();
    }

    public Job_Notification fetchbyid(Long id)
    {
        return jobNRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job notification not found with id: " + id));
    }

    public Job_Notification update(Long id, Job_Notification jobNotification)
    {
        Job_Notification j = jobNRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job notification not found with id: " + id));
        if (jobNotification.getTitle() != null) j.setTitle(jobNotification.getTitle());
        if (jobNotification.getDiscription() != null) j.setDiscription(jobNotification.getDiscription());
        if (jobNotification.getPublish_date() != null) j.setPublish_date(jobNotification.getPublish_date());
        return jobNRepository.save(j);
    }

    public String delete(Long id)
    {
        if (!jobNRepository.existsById(id)) {
            throw new ResourceNotFoundException("Job notification not found with id: " + id);
        }
        jobNRepository.deleteById(id);
        return "data deleted successfully";
    }
}
