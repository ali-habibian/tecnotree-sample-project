package com.habibian.tsp.repository;

import com.habibian.tsp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ali
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
