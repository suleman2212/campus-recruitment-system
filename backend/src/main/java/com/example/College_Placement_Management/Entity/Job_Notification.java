package com.example.College_Placement_Management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Job_Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notification_id;

    @ManyToOne
    @JoinColumn(name="requirement_id")
    private HiringRequirement hiringRequirement;

    private String Title;
    private String Discription;
    private String publish_date;

    // Helper getters and setters for seamless JSON serialization/deserialization
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        this.Discription = discription;
    }

    public String getDescription() {
        return Discription;
    }

    public void setDescription(String description) {
        this.Discription = description;
    }
}
