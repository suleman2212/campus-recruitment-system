package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.Company;
import com.example.College_Placement_Management.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    public Company insert(Company company)
    {
        return companyRepository.save(company);
    }

    public List<Company> fetch()
    {
        return companyRepository.findAll();
    }

    public Company fetchbyid(Long id)
    {
        return companyRepository.findById(id).orElse(null);
    }

    public Company update(Long id, Company company)
    {
        Company c=companyRepository.findById(id).orElse(null);
        c.setCompany_name(company.getCompany_name());
        c.setEmail(company.getEmail());
        c.setPhone(company.getPhone());
        c.setWebsite(company.getWebsite());
        c.setLocation(company.getLocation());
        return companyRepository.save(c);

    }

    public String delete(Long id)
    {
        companyRepository.deleteById(id);
        return "data deleted successfully";
    }
}
