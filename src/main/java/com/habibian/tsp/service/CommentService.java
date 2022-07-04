package com.habibian.tsp.service;

import com.habibian.tsp.dto.CommentDto;
import com.habibian.tsp.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Ali
 */
public interface CommentService {
    /**
     * Get all comments (with pagination)
     *
     * @param page (Page number)
     * @param size (Number of posts per page)
     * @return (List of all comments with pagination)
     */
    Page<CommentDto> getAllCommentsWithPagination(int page, int size);

    /**
     * Get comments of specific post by post id
     *
     * @param postId (The ID of the post we want to get its comments)
     * @return (list of comments for the post with the given id)
     * @throws ResourceNotFoundException (If no post find throws an exception)
     */
    List<CommentDto> getAllCommentsByPostId(long postId);

    /**
     * Save the comment in the database
     *
     * @param commentDto (Comment details that wants to save)
     * @return (The comment details with ID of the comment after being saved in the database)
     */
    CommentDto saveComment(CommentDto commentDto);

    /**
     * Update a comment by comment id
     * If updated successfully, this method will return CommentDto else will throw exception
     *
     * @param commentDto (Comment details that wants to update)
     * @param commentId  (The ID of the comment we want to update)
     * @return (The comment details after being updated in the database)
     * @throws ResourceNotFoundException (If no comment find throws an exception)
     */
    CommentDto updateCommentById(long commentId, CommentDto commentDto) throws ResourceNotFoundException;

    /**
     * Delete a comment by comment id
     *
     * @param commentId (The ID of the comment we want to delete)
     * @throws ResourceNotFoundException (If no comment find throws an exception)
     */
    void deleteCommentById(long commentId) throws ResourceNotFoundException;
}
