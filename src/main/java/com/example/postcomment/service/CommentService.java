package com.example.postcomment.service;

import com.example.postcomment.entity.Comment;
import com.example.postcomment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(new Comment(comment.getContent(), comment.getParentId(), comment.getUserId()));
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> findByParentId(Long parent_id, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("createdAt"));
        Page<Comment> comments = commentRepository.findByParentId(parent_id, pageable);
        return comments.getContent();
    }

    public Comment update(Long id, Comment comment) {
        Comment comment1 = commentRepository.findById(id).get();
        comment1.setContent(comment.getContent());
        return commentRepository.save(comment);
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
        commentRepository.deleteAll(commentRepository.findAllByParentId(id));
    }

    public List<Comment> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Comment> comments = commentRepository.findAll(pageable);
        List<Comment> comments1 = comments.getContent();
        return comments1;
    }
}
