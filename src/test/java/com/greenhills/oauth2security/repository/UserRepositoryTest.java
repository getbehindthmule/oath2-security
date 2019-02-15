package com.greenhills.oauth2security.repository;


import com.greenhills.oauth2security.model.security.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testEntriesCanBeRetrieved() {
        // arrange

        // act
        User actualUser =  userRepository.findByUsername("admin");

        // assert
        assertThat(actualUser.getUsername()).isEqualTo("admin");
        assertThat(actualUser.getPassword()).isEqualTo("$2a$08$qvrzQZ7jJ7oy2p/msL4M0.l83Cd0jNsX6AJUitbgRXGzge4j035ha");
        assertThat(actualUser.getAuthorities())
                .hasSize(9)
                .extracting("name")
                .contains(
                        "COMPANY_CREATE",
                        "COMPANY_READ",
                        "COMPANY_UPDATE",
                        "COMPANY_DELETE",
                        "DEPARTMENT_CREATE",
                        "DEPARTMENT_READ",
                        "DEPARTMENT_UPDATE",
                        "DEPARTMENT_DELETE",
                        "ALL_COMPANY_READER"
                );

    }

}