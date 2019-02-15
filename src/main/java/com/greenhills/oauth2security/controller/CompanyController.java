package com.greenhills.oauth2security.controller;

import com.greenhills.oauth2security.dto.Company;
import com.greenhills.oauth2security.service.CompanyService;
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

    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Company> getAll() {
        LOGGER.debug("getAll called");
        return companyService.getAll();
    }

    @GetMapping(value = "company", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Company get(@RequestBody String name) {
        LOGGER.debug("getAll called");
        return companyService.get(name);
    }

    @PostMapping(value = "company", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean create(@RequestBody Company company) {

        companyService.create(company);

        return true;

    }



}