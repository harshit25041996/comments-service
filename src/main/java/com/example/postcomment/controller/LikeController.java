package com.example.postcomment.controller;

import com.example.postcomment.entity.Like;
import com.example.postcomment.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class LikeController {
    @Autowired
    private LikeService likeService;

    // like or dislike a post or a comment
    @PostMapping("/likes/{commentId}")
    public ResponseEntity<Like> createLike(@RequestBody Like like, @PathVariable("commentId") long commentId) {
        Like like1 = likeService.save(like, commentId);
        return new ResponseEntity<>(like1, HttpStatus.CREATED);
    }

    // get the likes on a comment id
    @GetMapping("/likes/{commentId}")
    public ResponseEntity<List<Like>> getLikesOnComment(@PathVariable("commentId") Long commentId, @RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "3") int size) {
        List<Like> likes = likeService.findLikesByCommentId(commentId, page, size);
        if(likes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }

    // get the dislikes on a comment id
    @GetMapping("/dislikes/{commentId}")
    public ResponseEntity<List<Like>> getDislikesOnComment(@PathVariable("commentId") Long commentId, @RequestParam(defaultValue = "1") int page,
                                                           @RequestParam(defaultValue = "3") int size) {
        List<Like> likes = likeService.findDislikesByCommentId(commentId, page, size);
        if(likes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }
}
