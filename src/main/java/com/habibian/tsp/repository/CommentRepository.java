package com.habibian.tsp.repository;

import com.habibian.tsp.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ali
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Find all comments of specific post by post id
     *
     * @param postId (ID of post to get its comments)
     * @return (List of all comments for specific post)
     */
    List<Comment> findAllByPostId(Long postId);
}
