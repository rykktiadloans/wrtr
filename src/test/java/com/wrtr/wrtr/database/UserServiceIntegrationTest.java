package com.wrtr.wrtr.database;

import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Sql(scripts = {"classpath:test-init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;

    @Test
    public void canLoadByUsername(){
        assertDoesNotThrow(() -> this.userService.loadUserByUsername("email"));
    }

    @Test
    public void throwsIfNotFoundInLoadByUsername(){
        assertThrows(UsernameNotFoundException.class, () -> this.userService.loadUserByUsername("random garbage idk"));
    }

    @Test
    public void canGetByEmail(){
        assertDoesNotThrow(() -> this.userService.getUserByUsername("email"));
    }

    @Test
    public void throwsIfNotFoundInGetByEmail(){
        assertThrows(UsernameNotFoundException.class, () -> this.userService.getUserByUsername("extra garbage"));
    }

    @Test
    public void canGetById(){
        assertDoesNotThrow(() -> this.userService.getUserById(UUID.fromString("bd3c743f-32d1-44a9-989d-4bc6a3caa902")));
    }

    @Test
    public void throwsIfNotFoundInGetById(){
        assertThrows(UsernameNotFoundException.class, () -> this.userService.getUserById(UUID.fromString("6910518d-9ca8-48fe-a20a-1f2fae7943c2")));
    }
}
