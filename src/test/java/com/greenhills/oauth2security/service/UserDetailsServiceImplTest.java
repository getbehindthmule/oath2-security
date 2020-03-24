package com.greenhills.oauth2security.service;


import com.greenhills.oauth2security.model.security.User;
import com.greenhills.oauth2security.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("WeakerAccess")
public class UserDetailsServiceImplTest {
    private final UserRepository userRepository = mock(UserRepository.class);

    private UserDetailsServiceImpl sut;

    @Before
    public void setUp() {
        sut = new UserDetailsServiceImpl();
        sut.setUserRepository(userRepository);
    }

    @Test
    public void testNullUserResultsInException() {
        // arrange
        final String userName = "bob";
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        // act
        Throwable thrown = catchThrowable( () -> sut.loadUserByUsername(userName));

        // assert
        assertThat(thrown).hasMessage(userName);
    }

    @Test
    public void testUserIsReturned() {
        // arrange
        final String userName = "bob";
        final User expectedUser = new User();
        expectedUser.setAccountExpired(false);
        expectedUser.setAccountLocked(false);
        expectedUser.setAuthorities(Collections.emptyList());
        expectedUser.setCredentialsExpired(false);
        expectedUser.setEnabled(true);
        expectedUser.setId(1L);
        expectedUser.setPassword("cunning-password");
        expectedUser.setUsername(userName);
        when(userRepository.findByUsername(userName)).thenReturn(expectedUser);

        // act
        final UserDetails actualUser = sut.loadUserByUsername(userName);

        // assert
        assertThat(actualUser).isEqualTo(expectedUser);
    }

}