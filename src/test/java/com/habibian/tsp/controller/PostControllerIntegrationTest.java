package com.habibian.tsp.controller;

import com.habibian.tsp.TecnotreeSampleProjectApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TecnotreeSampleProjectApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void loadAllPostWithPaginationOrByTitle() {
        ResponseEntity<Object> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/posts",Object.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void loadPostById() throws InterruptedException {
        Thread.sleep(3000);
        ResponseEntity<Object> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/posts/1",Object.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void loadPostById_PostNotExists() throws InterruptedException {
        Thread.sleep(3000);
        ResponseEntity<Object> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/posts/101",Object.class);
        assertEquals(500, responseEntity.getStatusCodeValue());
    }
}