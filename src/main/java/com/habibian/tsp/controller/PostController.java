package com.habibian.tsp.controller;

import com.habibian.tsp.dto.CommentDto;
import com.habibian.tsp.dto.PostCreateParam;
import com.habibian.tsp.dto.PostDto;
import com.habibian.tsp.dto.PostUpdateParam;
import com.habibian.tsp.entity.Comment;
import com.habibian.tsp.entity.Post;
import com.habibian.tsp.service.PostService;
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
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Map<String, Object>> loadAllPostWithPaginationOrByTitle(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String title) {

        if (title.isEmpty()) {
            Page<Post> allPostWithPagination = postService.getAllPostWithPagination(page, size);
            List<Post> posts = allPostWithPagination.getContent();

            // Convert to DTO
            List<PostDto> postDtoList = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>(4);
            response.put("posts", postDtoList);
            response.put("currentPage", allPostWithPagination.getNumber());
            response.put("totalItems", allPostWithPagination.getTotalElements());
            response.put("totalPages", allPostWithPagination.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return loadAllPostByTitleLike(title);
        }
    }

    private ResponseEntity<Map<String, Object>> loadAllPostByTitleLike(@RequestParam String title) {
        List<Post> allPostByTitle = postService.getAllPostByTitleLike(title);

        // Convert to DTO
        List<PostDto> postDtoList = allPostByTitle.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>(1);
        response.put("posts", postDtoList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> loadPostById(@PathVariable long postId) {
        Post post = postService.getPostById(postId);

        Map<String, Object> response = convertPostToMap(post);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<Map<String, Object>> loadAllCommentsByPostId(@PathVariable long postId) {
        List<Comment> allCommentsByPostId = postService.getAllCommentsByPostId(postId);

        // Convert to DTO
        List<CommentDto> commentDtoList = allCommentsByPostId.stream().map(comment ->
                modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>(1);
        response.put("post_id", postId);
        response.put("totalComments", commentDtoList.size());
        response.put("comments", commentDtoList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPost(@RequestBody PostCreateParam postDetails) {
        Post result = postService.savePost(postDetails);

        Map<String, Object> response = convertPostToMap(result);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> update(@RequestBody PostUpdateParam updateParam, @PathVariable long postId) {
        Post updatedPost = postService.updatePostById(postId, updateParam);

        Map<String, Object> response = convertPostToMap(updatedPost);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable long postId) {
        postService.deletePostById(postId);

        return new ResponseEntity<>("post deleted", HttpStatus.OK);
    }

    private Map<String, Object> convertPostToMap(Post post) {
        Map<String, Object> response = new HashMap<>(1);
        response.put("id", post.getId());
        response.put("userId", post.getUserId());
        response.put("title", post.getTitle());
        response.put("body", post.getBody());

        return response;
    }
}
