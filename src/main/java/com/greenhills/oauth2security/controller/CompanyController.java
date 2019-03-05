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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secured")
public class CompanyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Company> getAll() {
        LOGGER.debug("getAll called");
        return companyService.getAll();
    }

    @GetMapping(value = "company", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Company get(@RequestBody String name) {
        LOGGER.debug("get called");
        return companyService.get(name);
    }

    @PostMapping(value = "company", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long create(@RequestBody Company company) {
        LOGGER.debug("create called");

        return companyService.create(company);
    }

    @GetMapping(value = "/lightweight/company", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody LightweightCompany getLightweight(@RequestBody String name) {
        LOGGER.debug("get called");
        return companyService.getLightweight(name);
    }

    @GetMapping(value = "department", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Department get(@RequestBody Long id) {
        LOGGER.debug("get Department called for id " + id);
        return departmentService.get(id);
    }

    @GetMapping(value = "/lightweight/department", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    LightweightDepartment getLightweight(@RequestBody Long id) {
        LOGGER.debug("get Department called for id " + id);
        return departmentService.getLightweight(id);
    }

}
