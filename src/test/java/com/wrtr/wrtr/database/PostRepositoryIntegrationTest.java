package com.wrtr.wrtr.database;

import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.repository.PostRepository;
import com.wrtr.wrtr.core.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Sql(scripts = {"classpath:test-init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class PostRepositoryIntegrationTest {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Test
    public void canRepositoryGetAllPostsByUser(){
        User user = this.userService.getUserByEmail("email");
        List<Post> posts = this.postRepository.getPostsMadeByUser(user);
        assertTrue(posts.size() == 2);
    }

    @Test
    public void areThePostsOrderedCorrectly(){
        User user = this.userService.getUserByEmail("email");
        List<Post> posts = this.postRepository.getPostsMadeByUser(user);
        assertTrue(posts.get(0).getContent().equals("content2"));
    }
}
