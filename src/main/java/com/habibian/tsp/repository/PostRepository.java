package com.habibian.tsp.repository;

import com.habibian.tsp.entity.Comment;
import com.habibian.tsp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ali
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Find all posts that have the keyword in their title
     *
     * @param keyword (The keyword that the post title contains)
     * @return (list of posts that contain the keyword in the title)
     */
    List<Post> findAllByTitleContains(String keyword);
}
