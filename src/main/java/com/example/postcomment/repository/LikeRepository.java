package com.example.postcomment.repository;

import com.example.postcomment.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM Like l WHERE l.isLike = 1 and l.comment.id = ?1")
    Page<Like> findLikesByCommentId(Long commentId, Pageable pageable);

    @Query("SELECT l FROM Like l WHERE l.isLike = -1 and l.comment.id = ?1")
    Page<Like>findDislikesByCommentId(Long commentId, Pageable pageable);
}
