package com.greenhills.oauth2security.controller;


import com.greenhills.oauth2security.Application;
import com.greenhills.oauth2security.model.business.CompanyEntity;
import org.json.JSONObject;
import org.junit.After;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class CompanyControllerIntegrationTest {

    @PersistenceUnit()
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    final String readWriteClientName = "spring-security-oauth2-read-write-client";
    final String readWriteClientPassword = "spring-security-oauth2-read-write-client-password1234";
    final String readClientName = "spring-security-oauth2-read-client";
    final String readClientPassword = "spring-security-oauth2-read-client-password1234";

    final String adminUserName = "admin";
    final String adminUserPassword = "admin1234";
    final String readerUserName = "reader";
    final String readerUserPassword = "reader1234";

    /*
    following json expectedResult is equivalent to,
    [
      {
        "id": 1,
        "name": "Pepsi",
        "departments": [
          {
            "id": 3,
            "name": "Administration",
            "employees": [
              {
                "id": 3,
                "name": "Donald",
                "surname": "Tyler",
                "address": {
                  "id": 3,
                  "street": "Street Z",
                  "houseNumber": "3",
                  "zipCode": "12-343"
                }
              }
            ],
            "offices": [
              {
                "id": 4,
                "name": "OfficeEntity of A Los Angeles",
                "address": {
                  "id": 7,
                  "street": "Street XXX",
                  "houseNumber": "7",
                  "zipCode": "12-347"
                }
              }
            ]
          },
          {
            "id": 2,
            "name": "Research & Development",
            "employees": [
              {
                "id": 2,
                "name": "Robert",
                "surname": "James",
                "address": {
                  "id": 2,
                  "street": "Street Y",
                  "houseNumber": "2",
                  "zipCode": "12-342"
                }
              }
            ],
            "offices": [
              {
                "id": 3,
                "name": "OfficeEntity of R&D Boston",
                "address": {
                  "id": 6,
                  "street": "Street ZZ",
                  "houseNumber": "6",
                  "zipCode": "12-346"
                }
              }
            ]
          },
          {
            "id": 1,
            "name": "Sales & Marketing",
            "employees": [
              {
                "id": 1,
                "name": "John",
                "surname": "William",
                "address": {
                  "id": 1,
                  "street": "Street X",
                  "houseNumber": "1",
                  "zipCode": "12-341"
                }
              }
            ],
            "offices": [
              {
                "id": 1,
                "name": "OfficeEntity of S&M Boston",
                "address": {
                  "id": 4,
                  "street": "Street XX",
                  "houseNumber": "4",
                  "zipCode": "12-344"
                }
              },
              {
                "id": 2,
                "name": "OfficeEntity of S&M New York",
                "address": {
                  "id": 5,
                  "street": "Street YY",
                  "houseNumber": "5",
                  "zipCode": "12-345"
                }
              }
            ]
          }
        ],
        "cars": [
          {
            "id": 1,
            "registrationNumber": "XYZ10ABC"
          },
          {
            "id": 3,
            "registrationNumber": "XYZ12ABC"
          },
          {
            "id": 2,
            "registrationNumber": "XYZ11ABC"
          }
        ]
      },
      {
        "id": 2,
        "name": "Coca Cola",
        "departments": [
          {
            "id": 4,
            "name": "Human Resources",
            "employees": [],
            "offices": []
          }
        ],
        "cars": [
          {
            "id": 4,
            "registrationNumber": "XYZ13ABC"
          }
        ]
      },
      {
        "id": 3,
        "name": "Sprite",
        "departments": [
          {
            "id": 5,
            "name": "Sales & Marketing",
            "employees": [],
            "offices": []
          }
        ],
        "cars": []
      }
    ]
     */

    final String findAllCompaniesResponse = "[{\"id\":1,\"name\":\"Pepsi\",\"departments\":[{\"id\":3,\"name\":\"Administration\",\"employees\":[{\"id\":3,\"name\":\"Donald\",\"surname\":\"Tyler\",\"address\":{\"id\":3,\"street\":\"Street Z\",\"houseNumber\":\"3\",\"zipCode\":\"12-343\"}}],\"offices\":[{\"id\":4,\"name\":\"OfficeEntity of A Los Angeles\",\"address\":{\"id\":7,\"street\":\"Street XXX\",\"houseNumber\":\"7\",\"zipCode\":\"12-347\"}}]},{\"id\":2,\"name\":\"Research & Development\",\"employees\":[{\"id\":2,\"name\":\"Robert\",\"surname\":\"James\",\"address\":{\"id\":2,\"street\":\"Street Y\",\"houseNumber\":\"2\",\"zipCode\":\"12-342\"}}],\"offices\":[{\"id\":3,\"name\":\"OfficeEntity of R&D Boston\",\"address\":{\"id\":6,\"street\":\"Street ZZ\",\"houseNumber\":\"6\",\"zipCode\":\"12-346\"}}]},{\"id\":1,\"name\":\"Sales & Marketing\",\"employees\":[{\"id\":1,\"name\":\"John\",\"surname\":\"William\",\"address\":{\"id\":1,\"street\":\"Street X\",\"houseNumber\":\"1\",\"zipCode\":\"12-341\"}}],\"offices\":[{\"id\":1,\"name\":\"OfficeEntity of S&M Boston\",\"address\":{\"id\":4,\"street\":\"Street XX\",\"houseNumber\":\"4\",\"zipCode\":\"12-344\"}},{\"id\":2,\"name\":\"OfficeEntity of S&M New York\",\"address\":{\"id\":5,\"street\":\"Street YY\",\"houseNumber\":\"5\",\"zipCode\":\"12-345\"}}]}],\"cars\":[{\"id\":1,\"registrationNumber\":\"XYZ10ABC\"},{\"id\":3,\"registrationNumber\":\"XYZ12ABC\"},{\"id\":2,\"registrationNumber\":\"XYZ11ABC\"}]},{\"id\":2,\"name\":\"Coca Cola\",\"departments\":[{\"id\":4,\"name\":\"Human Resources\",\"employees\":[],\"offices\":[]}],\"cars\":[{\"id\":4,\"registrationNumber\":\"XYZ13ABC\"}]},{\"id\":3,\"name\":\"Sprite\",\"departments\":[{\"id\":5,\"name\":\"Sales & Marketing\",\"employees\":[],\"offices\":[]}],\"cars\":[]}]";
    @Autowired
    private MockMvc mockMvc;

    @After
    public void tearDown() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<CompanyEntity> resultSet = entityManager.createQuery("FROM CompanyEntity WHERE id > 3", CompanyEntity.class).getResultList();

        resultSet.forEach(entityManager::remove);
        entityManager.getTransaction().commit();
        entityManager.close();
    }


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
        final String expectedResponse = findAllCompaniesResponse;
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
        final String expectedResponse = findAllCompaniesResponse;
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
        assertThat(Long.parseLong(mvcResult.getResponse().getContentAsString())).isGreaterThan(3L);

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

    @Test
    public void testGetCompanyWhenFound() throws Exception {
        // arrange
        final String expectedResponse = "{\"id\":2,\"name\":\"Coca Cola\",\"departments\":[{\"id\":4,\"name\":\"Human Resources\",\"employees\":[],\"offices\":[]}],\"cars\":[{\"id\":4,\"registrationNumber\":\"XYZ13ABC\"}]}";
        final String token = getToken(readWriteClientName, readWriteClientPassword, adminUserName, adminUserPassword);

        // act
        MvcResult mvcResult = this.mockMvc.perform(
                get("/secured/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .content("Coca Cola")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualToIgnoringCase(expectedResponse);
    }

    @Test
    public void testGetCompanyWhenNotFound() throws Exception {
        // arrange
        final String expectedResponse = "";
        final String token = getToken(readWriteClientName, readWriteClientPassword, adminUserName, adminUserPassword);

        // act
        MvcResult mvcResult = this.mockMvc.perform(
                get("/secured/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content("missing")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    public void testCreateCompanyWhenNew() throws Exception {
        // arrange
        final String company = "{\"id\":null,\"name\":\"Green Hills\",\"departments\":[{\"id\":null,\"name\":\"Human Resources\",\"employees\":[],\"offices\":[]}],\"cars\":[]}";
        final String token = getToken(readWriteClientName, readWriteClientPassword, adminUserName, adminUserPassword);

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

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(Long.parseLong(mvcResult.getResponse().getContentAsString())).isGreaterThan(3L);
        mvcResult = this.mockMvc.perform(
                get("/secured/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content("Green Hills")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString()).isNotEmpty();
        assertThat(mvcResult.getResponse().getContentAsString()).contains("\"name\":\"Green Hills\"");
    }

    @Test
    public void testCreateCompanyWhenIdUndefined() throws Exception {
        // arrange
        final String company = "{\"name\":\"Green Hills\",\"departments\":[{\"id\":null,\"name\":\"Human Resources\",\"employees\":[],\"offices\":[]}],\"cars\":[]}";
        final String token = getToken(readWriteClientName, readWriteClientPassword, adminUserName, adminUserPassword);

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

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(Long.parseLong(mvcResult.getResponse().getContentAsString())).isGreaterThan(3L);
        mvcResult = this.mockMvc.perform(
                get("/secured/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content("Green Hills")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString()).isNotEmpty();
        assertThat(mvcResult.getResponse().getContentAsString()).contains("\"name\":\"Green Hills\"");
    }

    @Test
    public void testCreateCompanyDefinesAssociatedUser() throws Exception {
        // arrange
        final String company = "{\"name\":\"Green Hills\",\"departments\":[{\"id\":null,\"name\":\"Human Resources\",\"employees\":[],\"offices\":[]}],\"cars\":[]}";
        final String token = getToken(readWriteClientName, readWriteClientPassword, adminUserName, adminUserPassword);

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

        // assert
        entityManager = entityManagerFactory.createEntityManager();
        List<CompanyEntity> resultSet = entityManager.createQuery("FROM CompanyEntity WHERE id > 3", CompanyEntity.class).getResultList();

        Timestamp now = Timestamp.valueOf(LocalDate.now().atStartOfDay());
        assertThat(resultSet).hasSize(1)
                .extracting("createdBy", "modifiedBy", "createdAt", "updatedAt")
                .containsExactly(tuple("admin", "admin", now, now));
        entityManager.close();
    }


}