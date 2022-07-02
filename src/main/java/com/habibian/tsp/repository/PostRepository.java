package com.habibian.tsp.repository;

import com.habibian.tsp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ali
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Find all posts(with pagination)
     *
     * @param pageable (Pageable object for pagination details)
     * @return (Posts with Pagination)
     */
    Page<Post> findAllWithPagination(Pageable pageable);

    /**
     * Find all posts that have the keyword in their title
     *
     * @param keyword (The keyword that the post title contains)
     * @return (list of posts that contain the keyword in the title)
     */
    List<Post> findAllByTitleContains(String keyword);
}
