package com.habibian.tsp.service;

import com.habibian.tsp.dto.CommentDto;
import com.habibian.tsp.entity.Comment;
import com.habibian.tsp.entity.Post;
import com.habibian.tsp.exception.ResourceNotFoundException;
import com.habibian.tsp.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ali
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    public Page<Comment> getAllCommentsWithPagination(int page, int size) {
        return commentRepository.findAll(PageRequest.of(page, size));
    }

    /**
     * ToDo ResourceNotFoundException if post not find
     */
    public List<Comment> getAllCommentsByPostId(long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    public Comment saveComment(Comment comment) {
        Post post = postService.getPostById(comment.getPost().getId());

        post.addComment(comment);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    public Comment updateCommentById(long commentId, Comment comment) throws ResourceNotFoundException {
        Comment commentBeforeUpdate = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        commentBeforeUpdate.setName(comment.getName());
        commentBeforeUpdate.setEmail(comment.getEmail());
        commentBeforeUpdate.setBody(comment.getBody());

        return commentRepository.save(commentBeforeUpdate);
    }

    public void deleteCommentById(long commentId) throws ResourceNotFoundException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        commentRepository.delete(comment);
    }

    public void saveAll(List<CommentDto> commentDtoList) {
        for (CommentDto commentDto : commentDtoList) {
            Post post = postService.getPostById(commentDto.getPostId());

            Comment comment = new Comment();
            comment.setPost(post);
            comment.setName(commentDto.getName());
            comment.setEmail(commentDto.getEmail());
            comment.setBody(commentDto.getBody());
            comment.setId(commentDto.getId());

            commentRepository.save(comment);
        }
    }
}
