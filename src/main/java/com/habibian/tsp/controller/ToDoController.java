package com.habibian.tsp.controller;

import com.habibian.tsp.entity.ToDo;
import com.habibian.tsp.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ali
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class ToDoController {

    private final ToDoService toDoService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> loadAll(
            @RequestParam(defaultValue = "0") long userId,
            @RequestParam(defaultValue = "false") boolean completed) {

        if (userId == 0) {
            List<ToDo> todos = toDoService.getAllTodos();

            Map<String, Object> response = new HashMap<>(1);
            response.put("todos", todos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return loadAllByUserIdAndCompleted(userId, completed);
        }
    }

    private ResponseEntity<Map<String, Object>> loadAllByUserIdAndCompleted(long userId, boolean completed) {
        List<ToDo> todos = toDoService.getAllByUserIdAndCompleted(userId, completed);

        Map<String, Object> response = new HashMap<>(1);
        response.put("todos", todos);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
