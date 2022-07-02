package com.habibian.tsp.service;

import com.habibian.tsp.entity.Comment;
import com.habibian.tsp.entity.Post;
import com.habibian.tsp.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

/**
 * @author Ali
 */
public interface PostService {

    /**
     * Get all posts (with pagination)
     *
     * @param page (Page number)
     * @param size (Number of posts per page)
     * @return (List of all posts with pagination)
     */
    Page<Post> getAllPostWithPagination(int page, int size);

    /**
     * Get post by post id
     *
     * @param postId (The ID of the post we want to get)
     * @return (Post with the given id)
     * @throws ResourceNotFoundException (If no post find throws an exception)
     */
    Post getPostById(long postId) throws ResourceNotFoundException;

    /**
     * Get comments of specific post by post id
     *
     * @param postId (The ID of the post we want to get its comments)
     * @return (list of comments for the post with the given id)
     * @throws ResourceNotFoundException (If no post find throws an exception)
     */
    Set<Comment> getAllCommentsByPostId(long postId) throws ResourceNotFoundException;

    /**
     * Get all posts that have the given keyword in their title
     *
     * @param keyword (The keyword that the post title contains)
     * @return (list of posts that contain the keyword in the title)
     */
    List<Post> getAllPostByTitleLike(String keyword);

    /**
     * Save the post in the database
     *
     * @param post (Post that wants to save)
     * @return (The post details with ID of the post after being saved in the database)
     */
    Post savePost(Post post);

    /**
     * Update a post by post id
     * If updated successfully, this method will return true else will return false
     *
     * @param post   (Post details that wants to update)
     * @param postId (The ID of the post we want to update)
     * @return (The post details after being updated in the database)
     * @throws ResourceNotFoundException (If no post find throws an exception)
     */
    Post updatePostById(long postId, Post post) throws ResourceNotFoundException;

    /**
     * Delete a post by post id
     *
     * @param postId (The ID of the post we want to delete)
     * @throws ResourceNotFoundException (If no post find throws an exception)
     */
    void deletePostById(long postId) throws ResourceNotFoundException;
}
