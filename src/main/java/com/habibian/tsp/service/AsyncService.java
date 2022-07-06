package com.habibian.tsp.service;

import com.habibian.tsp.dto.CommentDto;
import com.habibian.tsp.entity.Post;
import com.habibian.tsp.entity.ToDo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Ali
 */
@Service
@RequiredArgsConstructor
public class AsyncService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncService.class);

    private final RestTemplate restTemplate;

    private final PostService postService;
    private final CommentService commentService;
    private final ToDoService toDoService;

    @Async("taskExecutor")
    public CompletableFuture<List<Post>> fetchAndSavePosts() throws ExecutionException, InterruptedException {
        LOGGER.info("Fetching posts...");

        final long start = System.currentTimeMillis();

        String url = "https://jsonplaceholder.typicode.com/posts";
        Post[] postArray = restTemplate.getForObject(url, Post[].class);

        assert postArray != null;
        LOGGER.info("Saving a list of posts of size {} records", postArray.length);

        List<Post> postList = Arrays.asList(postArray);

        CompletableFuture<List<Post>> posts = CompletableFuture.completedFuture(postList);

        postService.saveAll(posts.get());

        return posts;
    }

    @Async("taskExecutor")
    public CompletableFuture<List<CommentDto>> fetchAndSaveComments() throws ExecutionException, InterruptedException {
        LOGGER.info("Fetching comments...");

        final long start = System.currentTimeMillis();

        String url = "https://jsonplaceholder.typicode.com/comments";
        CommentDto[] commentDtoArray = restTemplate.getForObject(url, CommentDto[].class);

        assert commentDtoArray != null;
        LOGGER.info("Saving a list of comments of size {} records", commentDtoArray.length);

        List<CommentDto> commentDtoList = Arrays.asList(commentDtoArray);

        CompletableFuture<List<CommentDto>> comments = CompletableFuture.completedFuture(commentDtoList);

        commentService.saveAll(comments.get());

        return comments;
    }

    @Async("taskExecutor")
    public CompletableFuture<List<ToDo>> fetchAndSaveToDos() throws ExecutionException, InterruptedException {
        LOGGER.info("Fetching todos...");

        final long start = System.currentTimeMillis();

        String url = "https://jsonplaceholder.typicode.com/todos";
        ToDo[] toDoArray = restTemplate.getForObject(url, ToDo[].class);

        assert toDoArray != null;
        LOGGER.info("Saving a list of todos of size {} records", toDoArray.length);

        List<ToDo> toDoList = Arrays.asList(toDoArray);

        CompletableFuture<List<ToDo>> toDos = CompletableFuture.completedFuture(toDoList);

        toDoService.saveAll(toDos.get());

        return toDos;
    }
}
