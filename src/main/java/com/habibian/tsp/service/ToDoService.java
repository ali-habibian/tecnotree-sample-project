package com.habibian.tsp.service;

import com.habibian.tsp.entity.ToDo;
import com.habibian.tsp.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ali
 */
@Service
@RequiredArgsConstructor
public class ToDoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToDoService.class);

    private final ToDoRepository toDoRepository;

    public List<ToDo> getAllTodos() {
        LOGGER.info("Starting getAllTodos...");

        return toDoRepository.findAll();
    }

    public List<ToDo> getAllByUserIdAndCompleted(long userId, boolean completed) {
        LOGGER.info("Starting getAllByUserIdAndCompleted...userId={}, completed={}", userId, completed);

        return toDoRepository.findAllByUserIdAndCompleted(userId, completed);
    }

    public void saveAll(List<ToDo> toDoList) {
        LOGGER.info("Starting saveAll...");

        toDoRepository.saveAll(toDoList);
    }
}
