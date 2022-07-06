package com.habibian.tsp;

import com.habibian.tsp.entity.Post;
import com.habibian.tsp.service.AsyncService;
import com.habibian.tsp.service.CommentService;
import com.habibian.tsp.service.PostService;
import com.habibian.tsp.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Ali
 */
@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppRunner.class);

    private final AsyncService asyncService;
    private final PostService postService;
    private final CommentService commentService;
    private final ToDoService toDoService;

    @Override
    public void run(String... args) throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        CompletableFuture<List<Post>> posts = CompletableFuture.supplyAsync(() -> {
            List<Post> postList = new ArrayList<>();
            try {
                CompletableFuture<List<Post>> listCompletableFuture = asyncService.fetchAndSavePosts();
                postList = listCompletableFuture.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return postList;
        });

        posts.thenAccept(s -> {
            try {
                asyncService.fetchAndSaveComments();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        posts.thenAccept(s -> {
            try {
                asyncService.fetchAndSaveToDos();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Print results, including elapsed time
        LOGGER.info("Elapsed time: " + (System.currentTimeMillis() - start));
    }
}
