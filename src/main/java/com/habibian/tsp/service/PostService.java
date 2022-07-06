package com.habibian.tsp.service;

import com.habibian.tsp.entity.Comment;
import com.habibian.tsp.entity.Post;
import com.habibian.tsp.exception.ResourceNotFoundException;
import com.habibian.tsp.repository.PostRepository;
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
public class PostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;

    public Page<Post> getAllPostWithPagination(int page, int size) {
        return postRepository.findAll(PageRequest.of(page, size));
    }

    public Post getPostById(long postId) throws ResourceNotFoundException {
        return postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "ID", postId));
    }

    public List<Comment> getAllCommentsByPostId(long postId) throws ResourceNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "ID", postId));

        return post.getComments();
    }

    public List<Post> getAllPostByTitleLike(String keyword) {
        return postRepository.findAllByTitleContains(keyword);
    }

    public Post savePost(Post post) {
//        post.setComments(new ArrayList<>());
        return postRepository.save(post);
    }

    public void saveAll(List<Post> posts) {
        for (Post post : posts) {
            Post save = postRepository.save(post);
        }
    }

    public Post updatePostById(long postId, Post post) throws ResourceNotFoundException {
        Post postBeforeUpdate = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "ID", postId));

        postBeforeUpdate.setTitle(post.getTitle());
        postBeforeUpdate.setBody(post.getBody());

        return postRepository.save(postBeforeUpdate);
    }

    public void deletePostById(long postId) throws ResourceNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "ID", postId));

        postRepository.delete(post);
    }
}
