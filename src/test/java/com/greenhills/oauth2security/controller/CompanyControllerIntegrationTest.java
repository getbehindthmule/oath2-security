package com.greenhills.oauth2security.controller;


import com.greenhills.oauth2security.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class CompanyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testInitialisation() {
        assertThat(true).isTrue();
    }


    @Test
    public void testGetToken() throws Exception{
        // arrange
        final String expectedResultContainsToken = "\"access_token\"";

        // act
        MvcResult mvcResult = this.mockMvc.perform(
                post("/oauth/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header(HttpHeaders.AUTHORIZATION,"Basic c3ByaW5nLXNlY3VyaXR5LW9hdXRoMi1yZWFkLXdyaXRlLWNsaWVudDpzcHJpbmctc2VjdXJpdHktb2F1dGgyLXJlYWQtd3JpdGUtY2xpZW50LXBhc3N3b3JkMTIzNA==")
                        .param("grant_type", "password")
                        .param("username", "admin")
                        .param("password", "admin1234")
                        .param("client_id", "spring-security-oauth2-read-write-client")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentAsString()).contains(expectedResultContainsToken);

    }


    @Test
    public void testSimpleGetAll() throws Exception{
        // arrange
        String expectedResponse = "{}";

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/secured/company")
        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualToIgnoringCase(expectedResponse);
    }
}