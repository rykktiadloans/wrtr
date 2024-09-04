package com.wrtr.wrtr.database;

import com.wrtr.wrtr.core.exceptions.PostNotFoundException;
import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.model.Resource;
import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.service.PostService;
import com.wrtr.wrtr.core.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Sql(scripts = {"classpath:test-init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PostServiceIntegrationTest {
    @Autowired
    PostService postService;

    @Autowired
    UserService userService;
    @Test
    public void ableToSavePostAndAttachments(){
        Post post = new Post("contento", userService.getUserByEmail("email"));
        Resource resource = new Resource("path", "name");
        resource.setPost(post);
        post.getResourceSet().add(resource);
        this.postService.save(post);

        assertNotNull(post.getId());
    }

    @Test
    public void canGetAllThePostsMadeByUser(){
        User user = this.userService.getUserByEmail("email");
        var posts = this.postService.getPostsMadeByUser(user);
        assertEquals(posts.size(), 2);
    }

    @Test
    public void canGetPostById(){
        assertDoesNotThrow(() -> {
            Post post = this.postService.getPostById(UUID.fromString("bc19d892-f486-466b-8a46-6a9181b23e76"));
        });
    }

    @Test
    public void willThrowIfWrongIdSupplied(){
        assertThrows(PostNotFoundException.class, () -> {
            Post post = this.postService.getPostById(UUID.fromString("bd3c743f-32d1-44a9-989d-4bc6a3caa902"));
        });
    }

    @Test
    public void canDeletePost(){
        try {
            this.postService.deletePost(this.postService.getPostById(UUID.fromString("bc19d892-f486-466b-8a46-6a9181b23e76")));
        } catch (PostNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertThrows(PostNotFoundException.class, () -> {
            this.postService.getPostById(UUID.fromString("bc19d892-f486-466b-8a46-6a9181b23e76"));
        });

    }

}
