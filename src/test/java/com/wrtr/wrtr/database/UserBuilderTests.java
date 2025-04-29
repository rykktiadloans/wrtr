package com.wrtr.wrtr.database;

import com.wrtr.wrtr.core.config.SecurityConfig;
import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.model.builders.UserBuilderFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserBuilderTests {
    @Autowired
    private UserBuilderFactory userBuilderFactory;
    @Autowired
    private SecurityConfig securityConfig;

    @Test
    public void userBuilderFactoryCanCreateBuilders() {
        assertDoesNotThrow(() -> this.userBuilderFactory.createUserBuilder());
    }

    // To be honest, now that I think about it, I don't think this one test specifically tests anything, but
    // I'll let it be I guess
    @Test
    public void userBuilderBasicallyWorks() {
        final var user = this.userBuilderFactory.createUserBuilder()
                .addUsername("username")
                .addEmail("user@mail.xyz")
                .addRole("user")
                .addPassword("password")
                .build();
        assertNotNull(user);
    }

    @Test
    public void userBuilderProperlyEncryptsPasswords() {
        final String password = "password";
        final User user = this.userBuilderFactory.createUserBuilder()
                .addUsername("username")
                .addEmail("user@mail.xyz")
                .addRole("user")
                .addPassword(password)
                .build();

        assertTrue(this.securityConfig.passwordEncoder().matches(password, user.getPassword()));
    }

    @Test
    public void userBuilderExplodesIfNoUsernameIsProvided() {
        assertThrows(IllegalStateException.class, () -> this.userBuilderFactory.createUserBuilder()
                .addEmail("user@mail.xyz")
                .addRole("user")
                .addPassword("password")
                .build());

    }

    @Test
    public void userBuilderExplodesIfNoEmailIsProvided() {
        assertThrows(IllegalStateException.class, () -> this.userBuilderFactory.createUserBuilder()
                .addUsername("username")
                .addRole("user")
                .addPassword("password")
                .build());

    }

    @Test
    public void userBuilderExplodesIfNoRoleIsProvided() {
        assertThrows(IllegalStateException.class, () -> this.userBuilderFactory.createUserBuilder()
                .addUsername("username")
                .addEmail("user@mail.xyz")
                .addPassword("password")
                .build());
    }

    @Test
    public void userBuilderExplodesIfNoPasswordIsProvided() {
        assertThrows(IllegalStateException.class, () -> this.userBuilderFactory.createUserBuilder()
                .addUsername("username")
                .addEmail("user@mail.xyz")
                .addRole("user")
                .build());
    }
}
