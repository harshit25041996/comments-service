package com.example.postcomment.service;

import com.example.postcomment.entity.Comment;
import com.example.postcomment.repository.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
@SpringBootTest
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    void testSave() {
        Comment comment = new Comment("test content", 1l, "test user");
        doReturn(comment).when(commentRepository).save(comment);
        Comment comment1 = commentService.save(comment);
        Assertions.assertEquals(comment, comment1);
    }

    @Test
    void testFindById() {
        Comment comment = new Comment(2l, "test content", 1l, "test user", LocalDateTime.MAX, null);
        doReturn(Optional.of(comment)).when(commentRepository).findById(2l);
        Optional<Comment> comment1 = commentService.findById(2l);
        Assertions.assertEquals(comment, comment1.get());
    }
}