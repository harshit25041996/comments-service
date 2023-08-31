package com.example.postcomment.controller;

import com.example.postcomment.entity.Comment;
import com.example.postcomment.exception.ResourceNotFoundException;
import com.example.postcomment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // to create a post or a comment on the post or reply on the comments
    @PostMapping("/comments")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment comment1 = commentService.save(comment);
        return new ResponseEntity<>(comment1, HttpStatus.CREATED);
    }

    // to get a particular post or a comment
    @GetMapping("/comment/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable("id") Long id) {
        Comment comment =commentService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No comment with id: "+ id));
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    // to get all the comments on a post or all the replies on a comment
    @GetMapping("/comments/{parent_id}")
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable("parent_id") Long parentId, @RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "3") int size) {
        List<Comment> comments = commentService.findByParentId(parentId, page, size);
        if(comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    //update the comment or post
    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("id") Long id, @RequestBody Comment comment) {
        Comment comment1 = commentService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No comment with id: " + id));
        commentService.update(id, comment);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    // deletes a post or a comment and the child posts also get deleted
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") Long id) {
        Comment comment = commentService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No comment with id: "+id));
        commentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //get all the comments
    @GetMapping("/comments/all")
    public ResponseEntity<List<Comment>> getAllComments(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "3") int size) {
        List<Comment> comments = commentService.findAll(page, size);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
