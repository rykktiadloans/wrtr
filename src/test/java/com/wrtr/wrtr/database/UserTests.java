package com.wrtr.wrtr.database;

import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.model.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserTests {
    @Test
    public void userDetectsExceedingUsernames(){
        User user = new User();
        user.setUsername("x".repeat(User.USERNAME_SIZE + 1));
        user.setPassword("");
        user.setBio("");
        assertTrue(user.anySizeExceeds());
    }
    @Test
    public void userDetectsExceedingPasswords(){
        User user = new User();
        user.setUsername("");
        user.setPassword("x".repeat(User.PASSWORD_SIZE + 1));
        user.setBio("");
        assertTrue(user.anySizeExceeds());
    }
    @Test
    public void userDetectsExceedingBios(){
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setBio("x".repeat(User.BIO_SIZE + 1));
        assertTrue(user.anySizeExceeds());
    }


    @Test
    public void userDetectsMultipleExceedingFields(){
        User user = new User();
        user.setUsername("x".repeat(User.USERNAME_SIZE+ 1));
        user.setPassword("x".repeat(User.PASSWORD_SIZE + 1));
        user.setBio("x".repeat(User.BIO_SIZE + 1));
        assertTrue(user.anySizeExceeds());
    }

    @Test
    public void userDoesntIncludeMaxValuesAsExceeds(){
        User user = new User();
        user.setUsername("x".repeat(User.USERNAME_SIZE));
        user.setPassword("x".repeat(User.PASSWORD_SIZE));
        user.setBio("x".repeat(User.BIO_SIZE));
        assertFalse(user.anySizeExceeds());
    }
    @Test
    public void userDtoDetectsExceedingUsernames(){
        UserDto userDto = new UserDto("x".repeat(User.USERNAME_SIZE + 1), "", null);
        assertTrue(userDto.isUsernameTooLarge());
    }
    @Test
    public void userDtoDetectsExceedingBios(){
        UserDto userDto = new UserDto("", "x".repeat(User.BIO_SIZE + 1), null);
        assertTrue(userDto.isBioTooLarge());
    }
}
