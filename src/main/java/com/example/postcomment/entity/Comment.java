package com.example.postcomment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments_tbl")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Long parentId;
    private String userId;
    @UpdateTimestamp
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Like> likes;

    public Comment(String content, Long parentId, String userId) {
        this.content = content;
        this.parentId = parentId;
        this.userId = userId;
    }
}



