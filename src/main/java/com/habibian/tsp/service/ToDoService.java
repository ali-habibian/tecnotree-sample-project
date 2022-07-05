package com.habibian.tsp.service;

import com.habibian.tsp.dto.ToDoDto;
import com.habibian.tsp.entity.ToDo;

import java.util.List;

/**
 * @author Ali
 */
public interface ToDoService {
    /**
     * Get all to-do’s
     *
     * @return (List of TodoDto)
     */
    List<ToDoDto> getAllTodos();

    /**
     * Get all to-do’s of specific user by user id and completed field
     *
     * @param userId    (ID of the user who created this ToDo_)
     * @param completed (A Boolean value that indicates whether the ToDo_ is finished or not
     * @return (List of ToDoDto that match the given conditions ( userId and completed)
     */
    List<ToDoDto> getAllByUserIdAndCompleted(long userId, boolean completed);
}
