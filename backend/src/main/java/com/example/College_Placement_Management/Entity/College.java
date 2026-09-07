package com.example.College_Placement_Management.Entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @JsonProperty("cname")
    @JsonAlias({"cname", "Cname"})
    private String Cname;

    private String region;

    @JsonProperty("addresss")
    @JsonAlias({"address", "addresss", "Address", "Addresss"})
    private String addresss;

    private String pofficer;
    private String pemail;
    private Long pnumber;
    private String infra_score;
    private Long student_strength;

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        this.Cname = cname;
    }

    public String getAddress() {
        return addresss;
    }

    public void setAddress(String address) {
        this.addresss = address;
    }
}
