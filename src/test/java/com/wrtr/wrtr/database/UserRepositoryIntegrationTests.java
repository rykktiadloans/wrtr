package com.wrtr.wrtr.database;

import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.xml.crypto.Data;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Sql(scripts = {"classpath:test-init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class UserRepositoryIntegrationTests {
    @Autowired
    UserRepository userRepository;
    @Test
    public void getByUsernameCanFindUser(){
        User user = this.userRepository.getUserByUsername("email");
        assertNotNull(user);
    }

    @Test
    public void getByIdCanFindUser(){
        User user= this.userRepository.getUserById(UUID.fromString("bd3c743f-32d1-44a9-989d-4bc6a3caa902"));
        assertNotNull(user);
    }

}
