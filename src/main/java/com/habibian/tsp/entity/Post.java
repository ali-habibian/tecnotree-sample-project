package com.habibian.tsp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ali
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false)
    private String body;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post")
    private Set<Comment> comments;

    public Post(long userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    private void addComment(Comment comment) {
        if (comments == null) {
            comments = new HashSet<>();
        }

        comments.add(comment);
        comment.setPost(this);
    }
}
