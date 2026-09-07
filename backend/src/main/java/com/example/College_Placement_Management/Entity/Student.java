package com.example.College_Placement_Management.Entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("student_id")
    @JsonAlias({"student_id", "Student_id", "id"})
    private Long Student_id;

    @ManyToOne
    @JoinColumn(name="college_id")
    private College college;

    @JsonProperty("name")
    @JsonAlias({"name", "Name"})
    private String Name;

    @JsonProperty("email")
    @JsonAlias({"email", "Email"})
    private String Email;

    @JsonProperty("phone")
    @JsonAlias({"phone", "Phone"})
    private Long Phone;

    private String branch;
    private Double cgpa;
    private Long graduation_year;
    private String skills;

    @JsonProperty("linkdin")
    @JsonAlias({"linkdin", "linkedin", "Linkedin", "Linkdin"})
    private String linkdin;

    private String github;

    @JsonProperty("resume")
    @JsonAlias({"resume", "Resume"})
    private String Resume;

    public Long getStudent_id() {
        return Student_id;
    }

    public void setStudent_id(Long student_id) {
        this.Student_id = student_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public Long getPhone() {
        return Phone;
    }

    public void setPhone(Long phone) {
        this.Phone = phone;
    }

    public String getResume() {
        return Resume;
    }

    public void setResume(String resume) {
        this.Resume = resume;
    }

    public String getLinkedin() {
        return linkdin;
    }

    public void setLinkedin(String linkedin) {
        this.linkdin = linkedin;
    }
}
