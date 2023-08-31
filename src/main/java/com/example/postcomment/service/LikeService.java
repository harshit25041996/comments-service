package com.example.postcomment.service;

import com.example.postcomment.entity.Like;
import com.example.postcomment.repository.CommentRepository;
import com.example.postcomment.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private CommentRepository commentRepository;

    public Like save(Like like, Long commentId) {
        return commentRepository.findById(commentId)
                .map(comment-> {
                    like.setComment(comment);
                    return likeRepository.save(like);
                }).orElseThrow(()->new RuntimeException("Comment not found with id: "+ commentId));
    }

    public List<Like> findLikesByCommentId(Long commentId, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Like> likes = likeRepository.findLikesByCommentId(commentId, pageable);
        return likes.getContent();
    }

    public List<Like> findDislikesByCommentId(Long commentId,  int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Like> dislikes = likeRepository.findDislikesByCommentId(commentId, pageable);
        return dislikes.getContent();
    }
}
