package com.habibian.tsp.service;

import com.habibian.tsp.entity.Comment;
import com.habibian.tsp.entity.Post;
import com.habibian.tsp.exception.ResourceNotFoundException;
import com.habibian.tsp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author Ali
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Page<Post> getAllPostWithPagination(int page, int size) {
        return postRepository.findAllWithPagination(PageRequest.of(page, size));
    }

    @Override
    public Post getPostById(long postId) throws ResourceNotFoundException {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
    }

    @Override
    public Set<Comment> getAllCommentsByPostId(long postId) throws ResourceNotFoundException {
        Post post = getPostById(postId);
        return post.getComments();
    }

    @Override
    public List<Post> getAllPostByTitleLike(String keyword) {
        return postRepository.findAllByTitleContains(keyword);
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePostById(long postId, Post post) throws ResourceNotFoundException {
        Post postBeforeUpdate = getPostById(postId);

        postBeforeUpdate.setTitle(post.getTitle());
        postBeforeUpdate.setBody(post.getBody());

        return postRepository.save(postBeforeUpdate);
    }

    @Override
    public void deletePostById(long postId) throws ResourceNotFoundException {
        Post post = getPostById(postId);
        postRepository.delete(post);
    }
}
