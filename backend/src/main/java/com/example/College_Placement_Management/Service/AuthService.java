package com.example.College_Placement_Management.Service;

import com.example.College_Placement_Management.Entity.College;
import com.example.College_Placement_Management.Entity.Company;
import com.example.College_Placement_Management.Entity.Student;
import com.example.College_Placement_Management.Entity.Users;
import com.example.College_Placement_Management.Repository.CollegeRepository;
import com.example.College_Placement_Management.Repository.CompanyRepository;
import com.example.College_Placement_Management.Repository.StudentRepository;
import com.example.College_Placement_Management.Repository.UsersReop;
import com.example.College_Placement_Management.dto.AuthResponse;
import com.example.College_Placement_Management.dto.CollegeRegisterRequest;
import com.example.College_Placement_Management.dto.CompanyRegisterRequest;
import com.example.College_Placement_Management.dto.RegisterResponse;
import com.example.College_Placement_Management.dto.StudentRegisterRequest;
import com.example.College_Placement_Management.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UsersReop usersReop;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CollegeRepository collegeRepository;
    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    private void ensureUsernameFree(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username is required.");
        }
        if (usersReop.getByusername(username) != null) {
            throw new IllegalArgumentException("That username is already taken.");
        }
    }

    public RegisterResponse registerStudent(StudentRegisterRequest req) {
        ensureUsernameFree(req.getUsername());
        if (req.getCollegeId() == null) {
            throw new IllegalArgumentException("Please select your college.");
        }
        College college = collegeRepository.findById(req.getCollegeId())
                .orElseThrow(() -> new IllegalArgumentException("Selected college was not found."));

        Student student = new Student();
        student.setCollege(college);
        student.setName(req.getName());
        student.setEmail(req.getEmail());
        student.setPhone(req.getPhone());
        student.setBranch(req.getBranch());
        student.setCgpa(req.getCgpa());
        student.setGraduation_year(req.getGraduationYear());
        student.setSkills(req.getSkills());
        student.setLinkdin(req.getLinkedin());
        student.setGithub(req.getGithub());
        student.setResume(req.getResume());
        student = studentRepository.save(student);

        Users users = new Users();
        users.setUsername(req.getUsername());
        users.setPassword(passwordEncoder.encode(req.getPassword()));
        users.setRole("STUDENT");
        users.setRefId(student.getStudent_id());
        usersReop.save(users);

        return new RegisterResponse("Student account created successfully.", req.getUsername(), "STUDENT", student.getStudent_id());
    }

    public RegisterResponse registerCompany(CompanyRegisterRequest req) {
        ensureUsernameFree(req.getUsername());

        Company company = new Company();
        company.setCompany_name(req.getCompanyName());
        company.setLocation(req.getLocation());
        company.setEmail(req.getEmail());
        company.setWebsite(req.getWebsite());
        company.setPhone(req.getPhone());
        company = companyRepository.save(company);

        Users users = new Users();
        users.setUsername(req.getUsername());
        users.setPassword(passwordEncoder.encode(req.getPassword()));
        users.setRole("COMPANY");
        users.setRefId(company.getCompany_id());
        usersReop.save(users);

        return new RegisterResponse("Company account created successfully.", req.getUsername(), "COMPANY", company.getCompany_id());
    }

    public RegisterResponse registerCollege(CollegeRegisterRequest req) {
        ensureUsernameFree(req.getUsername());

        College college = new College();
        college.setCname(req.getCname());
        college.setRegion(req.getRegion());
        college.setAddresss(req.getAddress());
        college.setPofficer(req.getPofficer());
        college.setPemail(req.getPemail());
        college.setPnumber(req.getPnumber());
        college.setInfra_score(req.getInfraScore());
        college.setStudent_strength(req.getStudentStrength());
        college = collegeRepository.save(college);

        Users users = new Users();
        users.setUsername(req.getUsername());
        users.setPassword(passwordEncoder.encode(req.getPassword()));
        users.setRole("COLLEGE");
        users.setRefId(college.getCid());
        usersReop.save(users);

        return new RegisterResponse("College account created successfully.", req.getUsername(), "COLLEGE", college.getCid());
    }

    /**
     * Verifies credentials and, if valid, returns a signed JWT plus the
     * role/refId the frontend needs to route to the right dashboard.
     * Returns null on bad username/password so the controller can 401.
     */
    public AuthResponse login(String username, String rawPassword) {
        Users user = usersReop.getByusername(username);
        if (user == null || rawPassword == null) return null;
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) return null;

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("refId", user.getRefId());

        String token = jwtService.getToken(user.getUsername(), claims);
        return new AuthResponse(token, user.getUsername(), user.getRole(), user.getRefId());
    }
}
