package com.habibian.tsp.service.impl;

import com.habibian.tsp.dto.CommentDto;
import com.habibian.tsp.dto.PostDto;
import com.habibian.tsp.entity.Post;
import com.habibian.tsp.exception.ResourceNotFoundException;
import com.habibian.tsp.repository.PostRepository;
import com.habibian.tsp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Ali
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<PostDto> getAllPostWithPagination(int page, int size) {
        Page<Post> posts = postRepository.findAllWithPagination(PageRequest.of(page, size));

        return posts.map(post -> {
//            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(post, PostDto.class);
        });
    }

    @Override
    public PostDto getPostById(long postId) throws ResourceNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "ID", postId));

        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public Set<CommentDto> getAllCommentsByPostId(long postId) throws ResourceNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "ID", postId));

        return post.getComments().stream().map(comment ->
                modelMapper.map(comment, CommentDto.class)).collect(Collectors.toSet());
    }

    @Override
    public List<PostDto> getAllPostByTitleLike(String keyword) {
        List<Post> postList = postRepository.findAllByTitleContains(keyword);

        return postList.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public PostDto savePost(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);

        Post saveResult = postRepository.save(post);

        return modelMapper.map(saveResult, PostDto.class);
    }

    @Override
    public PostDto updatePostById(long postId, PostDto postDto) throws ResourceNotFoundException {
        Post postBeforeUpdate = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "ID", postId));

        postBeforeUpdate.setTitle(postDto.getTitle());
        postBeforeUpdate.setBody(postDto.getBody());

        Post updateResult = postRepository.save(postBeforeUpdate);

        return modelMapper.map(updateResult, PostDto.class);
    }

    @Override
    public void deletePostById(long postId) throws ResourceNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "ID", postId));

        postRepository.delete(post);
    }
}
