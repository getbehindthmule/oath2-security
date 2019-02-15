package com.greenhills.oauth2security.controller;


import com.greenhills.oauth2security.Application;
import org.json.JSONObject;
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

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class CompanyControllerIntegrationTest {

    final String readWriteClientName = "spring-security-oauth2-read-write-client";
    final String readWriteClientPassword = "spring-security-oauth2-read-write-client-password1234";
    final String readClientName = "spring-security-oauth2-read-client";
    final String readClientPassword = "spring-security-oauth2-read-client-password1234";

    final String adminUserName = "admin";
    final String adminUserPassword = "admin1234";
    final String readerUserName = "reader";
    final String readerUserPassword = "reader1234";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testInitialisation() {
        assertThat(true).isTrue();
    }

    private String getToken(String clientUserName, String clientPassword, String userName, String userPassword) throws Exception {
        final String encodedClientCredentials = new String(Base64.getEncoder().encode((clientUserName + ":" + clientPassword).getBytes()));
        final String authorizationValue = "Basic " + encodedClientCredentials;
        MvcResult mvcResult = this.mockMvc.perform(
                post("/oauth/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header(HttpHeaders.AUTHORIZATION, authorizationValue)
                        .param("grant_type", "password")
                        .param("username", userName)
                        .param("password", userPassword)

        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        return new JSONObject(mvcResult.getResponse().getContentAsString()).getString("access_token");
    }

    @Test
    public void testGetToken() throws Exception {
        // arrange
        final String encodedClientCredentials = new String(Base64.getEncoder().encode((readWriteClientName + ":" + readWriteClientPassword).getBytes()));
        final String authorizationValue = "Basic " + encodedClientCredentials;
        // act
        MvcResult mvcResult = this.mockMvc.perform(
                post("/oauth/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header(HttpHeaders.AUTHORIZATION, authorizationValue)
                        .param("grant_type", "password")
                        .param("username", adminUserName)
                        .param("password", adminUserPassword)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsonResponse = new JSONObject(mvcResult.getResponse().getContentAsString());

        // assert
        assertThat(jsonResponse.getString("access_token")).isNotNull();
    }


    @Test
    public void testAccessRestWithReadWriteScope() throws Exception {
        // arrange
        final String expectedResponse = "[{\"id\":1,\"name\":\"green hills\",\"departments\":null,\"cars\":null}]";
        final String token = getToken(readWriteClientName, readWriteClientPassword, adminUserName, adminUserPassword);

        // act
        MvcResult mvcResult = this.mockMvc.perform(
                get("/secured/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualToIgnoringCase(expectedResponse);
    }

    @Test
    public void testAccessRestWithReadScope() throws Exception {
        // arrange
        final String expectedResponse = "[{\"id\":1,\"name\":\"green hills\",\"departments\":null,\"cars\":null}]";
        final String token = getToken(readClientName, readClientPassword, adminUserName, adminUserPassword);

        // act
        MvcResult mvcResult = this.mockMvc.perform(
                get("/secured/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualToIgnoringCase(expectedResponse);
    }

    @Test
    public void testAccessRestWithNoCredentials() throws Exception {
        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(
                get("/secured/all")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(401);
    }

    @Test
    public void testAccessRestWithBadCredentials() throws Exception {
        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(
                get("/secured/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer wibble")
        )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(401);
    }

    @Test
    public void testPostAccessWithReadWriteCredentialsAndReadWriteUserIsSuccessful() throws Exception {
        // arrange
        final String token = getToken(readWriteClientName, readWriteClientPassword, adminUserName, adminUserPassword);
        final String company = "{\n" +
                "  \"id\" : 1,\n" +
                "  \"name\": \"green hills\",\n" +
                "  \"department\" : null,\n" +
                "  \"cars\" : null\n" +
                "}";

        // act
        MvcResult mvcResult = this.mockMvc.perform(
                post("/secured/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(company)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualToIgnoringCase("true");

    }

    @Test
    public void testPostAccessWithReadCredentialsIsBlockedWithInsufficientScope() throws Exception {
        // arrange
        final String token = getToken(readClientName, readClientPassword, adminUserName, adminUserPassword);
        final String company = "{\n" +
                "  \"id\" : 1,\n" +
                "  \"name\": \"green hills\",\n" +
                "  \"department\" : null,\n" +
                "  \"cars\" : null\n" +
                "}";

        // act
        MvcResult mvcResult = this.mockMvc.perform(
                post("/secured/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(company)
        )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // assert
        JSONObject jsonResponse = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(403);
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");

        assertThat(jsonResponse.getString("error")).isEqualTo("insufficient_scope");
        assertThat(jsonResponse.getString("error_description")).isEqualTo("Insufficient scope for this resource");
        assertThat(jsonResponse.getString("scope")).isEqualTo("write");

    }

    @Test
    public void testPostAccessWithReadWriteCredentialsAndReadOnlyUserIsAccessDenied() throws Exception {
        // arrange
        final String token = getToken(readWriteClientName, readWriteClientPassword, readerUserName, readerUserPassword);
        final String company = "{\n" +
                "  \"id\" : 1,\n" +
                "  \"name\": \"green hills\",\n" +
                "  \"department\" : null,\n" +
                "  \"cars\" : null\n" +
                "}";

        // act
        MvcResult mvcResult = this.mockMvc.perform(
                post("/secured/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(company)
        )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // assert
        JSONObject jsonResponse = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(403);
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");

        assertThat(jsonResponse.getString("error")).isEqualTo("access_denied");
        assertThat(jsonResponse.getString("error_description")).isEqualTo("Access is denied");

    }
}