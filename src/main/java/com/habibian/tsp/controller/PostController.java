package com.habibian.tsp.controller;

import com.habibian.tsp.dto.CommentDto;
import com.habibian.tsp.dto.PostDto;
import com.habibian.tsp.service.impl.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ali
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostServiceImpl postService;
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<Map<String, Object>> loadAllPostWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PostDto> allPostWithPagination = postService.getAllPostWithPagination(page, size);
        List<PostDto> postDtoList = allPostWithPagination.getContent();

        Map<String, Object> response = new HashMap<>(1);
        response.put("posts", postDtoList);
        response.put("currentPage", allPostWithPagination.getNumber());
        response.put("totalItems", allPostWithPagination.getTotalElements());
        response.put("totalPages", allPostWithPagination.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> loadPostById(@PathVariable long postId) {
        PostDto response = postService.getPostById(postId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<Map<String, Object>> loadAllCommentsByPostId(@PathVariable long postId) {
        List<CommentDto> allCommentsByPostId = postService.getAllCommentsByPostId(postId);

        Map<String, Object> response = new HashMap<>(1);
        response.put("post_id", postId);
        response.put("totalComments", allCommentsByPostId.size());
        response.put("comments", allCommentsByPostId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> loadAllPostByTitleLike(@RequestParam String title) {
        List<PostDto> allPostByTitle = postService.getAllPostByTitleLike(title);

        Map<String, Object> response = new HashMap<>(1);
        response.put("posts", allPostByTitle.size());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDetails) {
        PostDto response = postService.savePost(postDetails);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostDto> update(@RequestBody PostDto postDto, @PathVariable long postId) {
        PostDto response = postService.updatePostById(postId, postDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable long postId) {
        postService.deletePostById(postId);

        return new ResponseEntity<>("post deleted", HttpStatus.OK);
    }
}
