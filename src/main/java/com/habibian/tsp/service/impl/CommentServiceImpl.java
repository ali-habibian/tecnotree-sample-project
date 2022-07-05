package com.habibian.tsp.service.impl;

import com.habibian.tsp.dto.CommentDto;
import com.habibian.tsp.entity.Comment;
import com.habibian.tsp.exception.ResourceNotFoundException;
import com.habibian.tsp.repository.CommentRepository;
import com.habibian.tsp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ali
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Page<CommentDto> getAllCommentsWithPagination(int page, int size) {
        Page<Comment> comments = commentRepository.findAllWithPagination(PageRequest.of(page, size));

        if (comments == null) {
            return null;
        } else {
            return comments.map(comment -> {
                return modelMapper.map(comment, CommentDto.class);
            });
        }
    }

    /**
     * ToDo ResourceNotFoundException if post not find
     */
    @Override
    public List<CommentDto> getAllCommentsByPostId(long postId) {
        List<Comment> commentList = commentRepository.findAllByPostId(postId);

        if (commentList == null){
            return null;
        }else {
            return commentList.stream().map(
                            comment -> modelMapper.map(comment, CommentDto.class))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public CommentDto saveComment(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);

        Comment saveResult = commentRepository.save(comment);

        return modelMapper.map(saveResult, CommentDto.class);
    }

    @Override
    public CommentDto updateCommentById(long commentId, CommentDto commentDto) throws ResourceNotFoundException {
        Comment commentBeforeUpdate = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        commentBeforeUpdate.setName(commentDto.getName());
        commentBeforeUpdate.setEmail(commentDto.getEmail());
        commentBeforeUpdate.setBody(commentDto.getBody());

        Comment updateResult = commentRepository.save(commentBeforeUpdate);

        return modelMapper.map(updateResult, CommentDto.class);
    }

    @Override
    public void deleteCommentById(long commentId) throws ResourceNotFoundException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        commentRepository.delete(comment);
    }
}
