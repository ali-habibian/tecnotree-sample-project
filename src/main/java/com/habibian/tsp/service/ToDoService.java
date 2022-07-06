package com.habibian.tsp.service;

import com.habibian.tsp.entity.ToDo;
import com.habibian.tsp.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ali
 */
@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public List<ToDo> getAllTodos() {
        return toDoRepository.findAll();
    }

    public List<ToDo> getAllByUserIdAndCompleted(long userId, boolean completed) {
        return toDoRepository.findAllByUserIdAndCompleted(userId, completed);
    }

    public void saveAll(List<ToDo> toDoList) {
        toDoRepository.saveAll(toDoList);
    }
}
