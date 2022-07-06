package com.habibian.tsp.service;

import com.habibian.tsp.dto.CommentCreateParam;
import com.habibian.tsp.dto.CommentDto;
import com.habibian.tsp.dto.CommentUpdateParam;
import com.habibian.tsp.entity.Comment;
import com.habibian.tsp.entity.Post;
import com.habibian.tsp.exception.ResourceNotFoundException;
import com.habibian.tsp.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final PostService postService;

    public Page<Comment> getAllCommentsWithPagination(int page, int size) {
        LOGGER.info("Starting getAllCommentsWithPagination...page={}, size={}", page, size);

        return commentRepository.findAll(PageRequest.of(page, size));
    }

    public List<Comment> getAllCommentsByPostId(long postId) {
        LOGGER.info("Starting getAllCommentsByPostId...postId={}", postId);

        return commentRepository.findAllByPostId(postId);
    }

    public Comment saveComment(CommentCreateParam createParam) {
        LOGGER.info("Starting saveComment...createParam={}", createParam);

        Post post = postService.getPostById(createParam.getPostId());

        Comment comment = new Comment();
        comment.setBody(createParam.getBody());
        comment.setEmail(createParam.getEmail());
        comment.setName(createParam.getName());
        comment.setPost(post);

        post.addComment(comment);

        return commentRepository.save(comment);
    }

    public Comment updateCommentById(long commentId, CommentUpdateParam updateParam) throws ResourceNotFoundException {
        LOGGER.info("Starting updateCommentById...commentId={}, updateParam={}", commentId, updateParam);

        Comment commentBeforeUpdate = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        commentBeforeUpdate.setName(updateParam.getName());
        commentBeforeUpdate.setEmail(updateParam.getEmail());
        commentBeforeUpdate.setBody(updateParam.getBody());

        return commentRepository.save(commentBeforeUpdate);
    }

    public void deleteCommentById(long commentId) throws ResourceNotFoundException {
        LOGGER.info("Starting deleteCommentById...commentId={}", commentId);

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        Post post = postService.getPostById(comment.getPost().getId());

        post.removeComment(comment);

        commentRepository.delete(comment);
    }

    public void saveAll(List<CommentDto> commentDtoList) {
        LOGGER.info("Starting saveAll...");

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
