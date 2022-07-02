package com.habibian.tsp.repository;

import com.habibian.tsp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Ali
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Find all posts that have the keyword in their title
     *
     * @param keyword (The keyword that the post title contains)
     * @return (An optional list of posts that contain the keyword in the title)
     */
    public Optional<List<Post>> findAllByTitleContainsOrderByIdAsc(String keyword);
}
