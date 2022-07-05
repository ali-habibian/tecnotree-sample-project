package com.habibian.tsp.service.impl;

import com.habibian.tsp.dto.CommentDto;
import com.habibian.tsp.dto.PostDto;
import com.habibian.tsp.entity.Comment;
import com.habibian.tsp.entity.Post;
import com.habibian.tsp.exception.ResourceNotFoundException;
import com.habibian.tsp.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

class PostServiceImplTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPostWithPagination() {
        List<Post> postList = createListWithThreePost();

        Page<Post> postPage = new PageImpl<>(postList);

        when(postRepository.findAllWithPagination(any(Pageable.class))).thenReturn(postPage);

        Page<PostDto> postDtoPage = postService.getAllPostWithPagination(1, 3);

        assertEquals(3, postDtoPage.getTotalElements());
    }

    @Test
    void getPostById_PostExists() {
        Post post = createSamplePostWithoutComment(1L, 2L, "hello", "hello body");

        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        PostDto postDto = postService.getPostById(1);

        assertNotNull(postDto);
        assertEquals("Hello", postDto.getTitle());
    }

    @Test
    void getPostById_PostNotExists() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.getPostById(1L));
    }

    @Test
    void getAllCommentsByPostId_PostExists_HaveComments() {
        Post post = createSamplePostWithThreeComment(1L, 2L, "hello", "hello body");

        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        List<CommentDto> commentDtoSet = postService.getAllCommentsByPostId(1);

        assertNotNull(commentDtoSet);
        assertEquals(3, commentDtoSet.size());
    }

    @Test
    void getAllCommentsByPostId_PostExists_DoesNotHaveComments() {
        Post post = createSamplePostWithoutComment(1L, 2L, "hello", "hello body");

        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        List<CommentDto> commentDtoSet = postService.getAllCommentsByPostId(1);

        assertNull(commentDtoSet);
    }

    @Test
    void getAllCommentsByPostId_PostNotExists() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.getAllCommentsByPostId(1L));
    }

    @Test
    void getAllPostByTitleLike_FindSomePosts() {
        List<Post> postList = createListWithThreePost();

        when(postRepository.findAllByTitleContains(anyString())).thenReturn(postList);

        List<PostDto> postDtoList = postService.getAllPostByTitleLike("title");

        assertNotNull(postDtoList);
        assertEquals(3, postDtoList.size());
    }

    @Test
    void getAllPostByTitleLike_NotFindAnyPosts() {
        when(postRepository.findAllByTitleContains(anyString())).thenReturn(null);

        List<PostDto> postDtoList = postService.getAllPostByTitleLike("title");

        assertNull(postDtoList);
    }

    @Test
    void savePost() {
        Post post = createSamplePostWithoutComment(12L, 3L, "Post Title", "Post Body");

        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDto postDto = new PostDto();
        postDto.setUserId(3);
        postDto.setTitle("Post Title");
        postDto.setBody("Post Body");

        PostDto savePost = postService.savePost(postDto);

        assertNotNull(savePost);
        assertEquals(12, savePost.getId());
    }

    @Test
    void updatePostById_PostExists() {
        Post post = createSamplePostWithThreeComment(12L, 3L, "Post Title", "Post Body");

        given(postRepository.findById(12L)).willReturn(Optional.of(post));

        PostDto postUpdateParamDto = new PostDto();
        postUpdateParamDto.setTitle("Updated Post Title");
        postUpdateParamDto.setBody("Post Body");

        given(postRepository.save(post)).willReturn(post);
        post.setTitle(postUpdateParamDto.getTitle());
        post.setBody(postUpdateParamDto.getBody());

        PostDto updatePostByIdDto = postService.updatePostById(12L, postUpdateParamDto);

        assertEquals(12L, updatePostByIdDto.getId());
        assertEquals(3L, updatePostByIdDto.getUserId());
        assertEquals("Updated Post Title", updatePostByIdDto.getTitle());
        assertEquals("Post Body", updatePostByIdDto.getBody());
    }

    @Test
    void updatePostById_PostNotExists() {
        given(postRepository.findById(anyLong())).willReturn(Optional.empty());

        PostDto postUpdateParamDto = new PostDto();
        postUpdateParamDto.setTitle("Updated Post Title");
        postUpdateParamDto.setBody("Post Body");

        assertThrows(ResourceNotFoundException.class, () -> postService.updatePostById(12L, postUpdateParamDto));
    }

    @Test
    void deletePostById_PostExists() {
        Post post = new Post();
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        willDoNothing().given(postRepository).delete(any(Post.class));

        postService.deletePostById(1);

        verify(postRepository, times(1)).delete(any(Post.class));
    }

    @Test
    void deletePostById_PostNotExists() {
        Post post = new Post();
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.deletePostById(1));
    }

    private Post createSamplePostWithThreeComment(Long postId, Long userId, String title, String body) {
        Post post = new Post();
        post.setId(postId);
        post.setUserId(userId);
        post.setTitle(title);
        post.setBody(body);


        Comment comment1 = new Comment(1L, post, "comment1", "comment1@mail.com", "Comment1 body");
        Comment comment2 = new Comment(2L, post, "comment2", "comment2@mail.com", "Comment2 body");
        Comment comment3 = new Comment(3L, post, "comment3", "comment3@mail.com", "Comment3 body");

        Set<Comment> commentSet = new HashSet<>();
        commentSet.add(comment1);
        commentSet.add(comment2);
        commentSet.add(comment3);

        post.setComments(commentSet);

        return post;
    }

    private Post createSamplePostWithoutComment(Long postId, Long userId, String title, String body) {
        Post post = new Post();
        post.setId(postId);
        post.setUserId(userId);
        post.setTitle(title);
        post.setBody(body);

        return post;
    }

    private List<Post> createListWithThreePost() {
        Post post1 = createSamplePostWithoutComment(1L, 1L, "title_1", "post body_1");
        Post post2 = createSamplePostWithoutComment(2L, 2L, "title_2", "post body_2");
        Post post3 = createSamplePostWithoutComment(3L, 1L, "title_3", "post body_3");

        List<Post> postList = new ArrayList<>();
        postList.add(post1);
        postList.add(post2);
        postList.add(post3);

        return postList;
    }
}