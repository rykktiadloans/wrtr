package com.wrtr.wrtr.database;

import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.model.dto.PostDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostTests {
    @Test
    public void postDtoDetectsExceedingContent(){
        PostDto postDto = new PostDto("x".repeat(Post.CONTENT_SIZE + 1), null);
        assertTrue(postDto.isContentTooLarge());
    }
}
