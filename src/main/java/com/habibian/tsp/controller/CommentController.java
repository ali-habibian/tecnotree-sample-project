package com.habibian.tsp.controller;

import com.habibian.tsp.dto.CommentDto;
import com.habibian.tsp.entity.Comment;
import com.habibian.tsp.entity.Post;
import com.habibian.tsp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ali
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Map<String, Object>> loadAllWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") long postId) {

        if (postId == 0) {
            Page<Comment> commentPage = commentService.getAllCommentsWithPagination(page, size);

            List<Comment> comments = commentPage.getContent();

            // Convert to DTO list
            List<CommentDto> commentDtoList = comments.stream().map(comment ->
                    modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>(4);
            response.put("comments", commentDtoList);
            response.put("currentPage", commentPage.getNumber());
            response.put("totalItems", commentPage.getTotalElements());
            response.put("totalPages", commentPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return loadByPostId(postId);
        }

    }

    private ResponseEntity<Map<String, Object>> loadByPostId(long postId) {
        List<Comment> comments = commentService.getAllCommentsByPostId(postId);

        // Convert to DTO list
        List<CommentDto> commentDtoList = comments.stream().map(comment ->
                modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>(1);
        response.put("comments", commentDtoList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createComment(@RequestBody CommentDto commentDetails) {
        Comment comment = commentService.saveComment(commentDetails);

        Map<String, Object> response = convertCommentToMap(comment);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Map<String, Object>> update(@RequestBody Comment comment, @PathVariable long commentId) {
        Comment updatedComment = commentService.updateCommentById(commentId, comment);

        Map<String, Object> response = convertCommentToMap(updatedComment);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@PathVariable long commentId) {
        commentService.deleteCommentById(commentId);

        return new ResponseEntity<>("comment deleted", HttpStatus.OK);
    }

    private Map<String, Object> convertCommentToMap(Comment comment) {
        Map<String, Object> response = new HashMap<>(1);
        response.put("id", comment.getId());
        response.put("email", comment.getEmail());
        response.put("name", comment.getName());
        response.put("body", comment.getBody());

        return response;
    }
}
