package com.greenhills.oauth2security.controller;

import com.greenhills.oauth2security.dto.Company;
import com.greenhills.oauth2security.dto.Department;
import com.greenhills.oauth2security.dto.LightweightCompany;
import com.greenhills.oauth2security.dto.LightweightDepartment;
import com.greenhills.oauth2security.service.CompanyService;
import com.greenhills.oauth2security.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/secured")
class CompanyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping(value = "companies", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Company> getAllCompanies() {
        LOGGER.debug("getAll called");
        return companyService.getAll();
    }

    @GetMapping(value = "companies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Company> getCompany(@PathVariable Long id) {
        LOGGER.debug("get called");
        Company company = companyService.get(id);
        return (company == null) ? ResponseEntity.notFound().build() : new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping(value = "company", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Company getCompany(@RequestBody String name) {
        LOGGER.debug("get called");
        return companyService.get(name);
    }

    @PostMapping(value = "companies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createCompany(@RequestBody Company company) {
        LOGGER.debug("create called");

        Optional<Long> id = companyService.create(company);

        return id.map(aLong -> new ResponseEntity<>(aLong, HttpStatus.CREATED)).orElseGet(() -> ResponseEntity.noContent().build());

    }

    @GetMapping(value = "/lightweight/company", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    LightweightCompany getLightweightCompany(@RequestBody String name) {
        LOGGER.debug("get called");
        return companyService.getLightweight(name);
    }

    @GetMapping(value = "department", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Department getDepartment(@RequestBody Long id) {
        LOGGER.debug("get Department called for id " + id);
        return departmentService.get(id);
    }

    @GetMapping(value = "/lightweight/department", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    LightweightDepartment getLightweightDepartment(@RequestBody Long id) {
        LOGGER.debug("get Department called for id " + id);
        return departmentService.getLightweight(id);
    }

    @GetMapping(value = "companies/{companyId}/departments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Department>> getDepartments(@PathVariable Long companyId) {
        LOGGER.debug("get called");
        Company company = companyService.get(companyId);

        if (company == null) return ResponseEntity.notFound().build();

        return new ResponseEntity<>(new ArrayList<>(company.getDepartments()), HttpStatus.OK);
    }

    @GetMapping(value = "companies/{companyId}/departments/{departmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> getDepartments(@PathVariable Long companyId, @PathVariable Long departmentId) {
        LOGGER.debug("get called");
        LightweightCompany company = companyService.getLightweight(companyId);

        if ((company == null) || (!company.getDepartmentIds().contains(departmentId))) return ResponseEntity.notFound().build();

        Department department = departmentService.get(departmentId);
        if (department == null) return ResponseEntity.notFound().build();

        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PostMapping(value = "companies/{companyId}/departments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createDepartment(@PathVariable Long companyId, @RequestBody LightweightDepartment department) {
        LOGGER.debug("create department");
        department.setCompanyId(companyId);
        Optional<Long> id = departmentService.create(department);

        return (id.isPresent()) ? new ResponseEntity<>(id.get(), HttpStatus.CREATED) : ResponseEntity.noContent().build();
    }


    @PutMapping(value = "companies/{companyId}/departments/{departmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public  @ResponseBody  Department createDepartment(@PathVariable Long companyId, @PathVariable Long departmentId, @RequestBody LightweightDepartment department) {
        LOGGER.debug("update department");
        department.setCompanyId(companyId);
        Optional<Long> id = departmentService.create(department);

        return null;
    }
}
