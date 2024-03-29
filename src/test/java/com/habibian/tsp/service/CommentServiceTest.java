package com.habibian.tsp.service;

import com.habibian.tsp.dto.CommentCreateParam;
import com.habibian.tsp.dto.CommentUpdateParam;
import com.habibian.tsp.entity.Comment;
import com.habibian.tsp.entity.Post;
import com.habibian.tsp.exception.ResourceNotFoundException;
import com.habibian.tsp.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    PostService postService;

    @InjectMocks
    CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCommentsWithPagination() {
        List<Comment> commentList = createListWithThreeComment();

        Page<Comment> commentPage = new PageImpl<>(commentList);

        when(commentRepository.findAll(any(Pageable.class))).thenReturn(commentPage);

        Page<Comment> result = commentService.getAllCommentsWithPagination(1, 3);

        assertEquals(3, result.getTotalElements());
    }

    @Test
    void getAllCommentsByPostId_PostExists() {
        List<Comment> commentList = createListWithThreeComment();

        when(commentRepository.findAllByPostId(anyLong())).thenReturn(commentList);

        List<Comment> comments = commentService.getAllCommentsByPostId(1L);

        assertNotNull(comments);
        assertEquals(3, comments.size());
    }

    @Test
    void getAllCommentsByPostId_PostNotExists() {
        when(commentRepository.findAllByPostId(anyLong())).thenReturn(null);

        List<Comment> comments = commentService.getAllCommentsByPostId(1L);

        assertNull(comments);
    }

    @Test
    void saveComment() {
        Comment comment = createComment();

        given(commentRepository.save(any(Comment.class))).willReturn(comment);

        CommentCreateParam createParam = new CommentCreateParam();
        createParam.setBody("body");
        createParam.setEmail("email");
        createParam.setName("name");
        createParam.setPostId(1L);

        when(postService.getPostById(anyLong())).thenReturn(new Post());

        Comment savedComment = commentService.saveComment(createParam);

        assertNotNull(savedComment);
        assertEquals(1L, savedComment.getId());
    }

    @Test
    void updateCommentById_CommentExists() {
        Comment comment = createComment();

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

        CommentUpdateParam updateParam = new CommentUpdateParam();
        updateParam.setName("name");
        updateParam.setBody("body");
        updateParam.setEmail("email");

        given(commentRepository.save(any(Comment.class))).willReturn(comment);
        comment.setName(updateParam.getName());
        comment.setBody(updateParam.getBody());
        comment.setEmail(updateParam.getEmail());

        Comment commentDtoAfterUpdate = commentService.updateCommentById(1L, updateParam);

        assertEquals("name", commentDtoAfterUpdate.getName());
    }

    @Test
    void updateCommentById_CommentNotExists() {
        given(commentRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commentService.updateCommentById(1L, new CommentUpdateParam()));
    }

    @Test
    void deleteCommentById_CommentExists() {
        Post post = new Post();
        post.setId(1L);

        Comment comment = new Comment();
        comment.setPost(post);

        post.addComment(comment);

        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        when(postService.getPostById(anyLong())).thenReturn(post);

        willDoNothing().given(commentRepository).delete(any(Comment.class));

        commentService.deleteCommentById(1);

        verify(commentRepository, times(1)).delete(any(Comment.class));
    }

    @Test
    void deleteCommentById_CommentNotExists() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commentService.deleteCommentById(1));
    }

    private List<Comment> createListWithThreeComment() {
        Post post1 = createSamplePostWithoutComment(1L, 1L, "title_1", "post body_1");

        Comment comment1 = new Comment(1L, post1, "Comment_1", "email@mail.com", "Comment_1_Body");
        Comment comment2 = new Comment(2L, post1, "Comment_2", "email@mail.com", "Comment_2_Body");
        Comment comment3 = new Comment(3L, post1, "Comment_3", "email@mail.com", "Comment_2_Body");

        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment3);

        return commentList;
    }

    private Comment createComment() {
        Post post1 = createSamplePostWithoutComment(1L, 1L, "title_1", "post body_1");
        return new Comment(1L, post1, "Comment_1", "email@mail.com", "Comment_1_Body");
    }

    private Post createSamplePostWithoutComment(Long postId, Long userId, String title, String body) {
        Post post = new Post();
        post.setId(postId);
        post.setUserId(userId);
        post.setTitle(title);
        post.setBody(body);

        return post;
    }
}