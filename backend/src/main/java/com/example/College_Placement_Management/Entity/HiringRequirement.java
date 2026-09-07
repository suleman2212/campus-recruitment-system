package com.example.College_Placement_Management.Entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HiringRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("requirement_id")
    @JsonAlias({"requirement_id", "requirementId", "id"})
    private Long requirement_id;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;
    
    @JsonProperty("jobRole")
    @JsonAlias({"jobRole", "job_role", "JobRole"})
    private String jobRole;

    @JsonProperty("job_type")
    @JsonAlias({"job_type", "jobType", "Job_type"})
    private String Job_type;

    @JsonProperty("required_candidates")
    @JsonAlias({"required_candidates", "requiredCandidates"})
    private Long required_candidates;

    @JsonProperty("required_skills")
    @JsonAlias({"required_skills", "requiredSkills", "Required_skills"})
    private String Required_skills;

    @JsonProperty("min_cgpa")
    @JsonAlias({"min_cgpa", "minCgpa"})
    private Double min_cgpa;

    @JsonProperty("eligible_branches")
    @JsonAlias({"eligible_branches", "eligibleBranches"})
    private String eligible_branches;

    @JsonProperty("target_region")
    @JsonAlias({"target_region", "targetRegion"})
    private String target_region;

    @JsonProperty("application_deadline")
    @JsonAlias({"application_deadline", "applicationDeadline"})
    private LocalDate application_deadline;

    @JsonProperty("created_date")
    @JsonAlias({"created_date", "createdDate"})
    private Long created_date;

    public String getJob_type() {
        return Job_type;
    }

    public void setJob_type(String job_type) {
        this.Job_type = job_type;
    }

    public String getRequired_skills() {
        return Required_skills;
    }

    public void setRequired_skills(String required_skills) {
        this.Required_skills = required_skills;
    }
}
