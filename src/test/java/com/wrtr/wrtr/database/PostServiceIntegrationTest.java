package com.wrtr.wrtr.database;

import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.model.Resource;
import com.wrtr.wrtr.core.service.PostService;
import com.wrtr.wrtr.core.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Sql(scripts = {"classpath:test-init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class PostServiceIntegrationTest {
    @Autowired
    PostService postService;

    @Autowired
    UserService userService;
    @Test
    public void ableToSavePostAndAttachments(){
        Post post = new Post("contento", userService.getUserByEmail("email"));
        Resource resource = new Resource("path");
        resource.setPost(post);
        post.getResourceSet().add(resource);
        this.postService.save(post);

        assertNotNull(post.getId());
    }

}
