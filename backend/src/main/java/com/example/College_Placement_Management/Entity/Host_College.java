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
public class Host_College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("hid")
    @JsonAlias({"hid", "id"})
    private Long hid;

    @ManyToOne
    @JoinColumn(name="requirement_id")
    private HiringRequirement hiringRequirement;

    @ManyToOne
    @JoinColumn(name="college_id")
    private College college;

    @JsonProperty("interview_date")
    @JsonAlias({"interview_date", "interviewDate"})
    private String interview_date;

    @JsonProperty("venue")
    @JsonAlias({"venue", "Venue"})
    private String venue;

    @JsonProperty("selection_reason")
    @JsonAlias({"selection_reason", "selectionReason", "Selection_reason"})
    private String Selection_reason;

    public String getSelection_reason() {
        return Selection_reason;
    }

    public void setSelection_reason(String selection_reason) {
        this.Selection_reason = selection_reason;
    }
}
