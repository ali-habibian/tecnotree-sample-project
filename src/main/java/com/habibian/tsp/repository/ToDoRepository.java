package com.habibian.tsp.repository;

import com.habibian.tsp.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ali
 */
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    /**
     * Find all to-do’s of specific user by user id and completed field
     *
     * @param userId    (ID of the user who created this ToDo_)
     * @param completed (A Boolean value that indicates whether the ToDo_ is finished or not
     * @return (An optional list of ToDos that match the given conditions ( userId and completed)
     */
    public List<ToDo> findAllByUserIdAndCompleted(Long userId, Boolean completed);
}
