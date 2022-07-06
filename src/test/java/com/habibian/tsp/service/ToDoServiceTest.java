package com.habibian.tsp.service;

import com.habibian.tsp.entity.ToDo;
import com.habibian.tsp.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ToDoServiceImplTest {

    @Mock
    ToDoRepository toDoRepository;

    @InjectMocks
    ToDoService toDoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTodos() {
        List<ToDo> toDoList = creatListWithThreeToDo();

        when(toDoRepository.findAll()).thenReturn(toDoList);

        List<ToDo> todos = toDoService.getAllTodos();

        assertNotNull(todos);
        assertEquals(3, toDoList.size());
    }

    @Test
    void getAllByUserIdAndCompleted() {
        List<ToDo> toDoList = creatListWithThreeToDo();

        when(toDoRepository.findAllByUserIdAndCompleted(anyLong(), anyBoolean())).thenReturn(toDoList);

        List<ToDo> toDos = toDoService.getAllByUserIdAndCompleted(2L, true);

        assertNotNull(toDos);
        assertEquals(3, toDos.size());
    }

    private List<ToDo> creatListWithThreeToDo() {
        ToDo toDo1 = new ToDo(1L, 2L, "Title_1", true);
        ToDo toDo2 = new ToDo(2L, 2L, "Title_2", false);
        ToDo toDo3 = new ToDo(3L, 2L, "Title_3", true);

        List<ToDo> toDoList = new ArrayList<>();
        toDoList.add(toDo1);
        toDoList.add(toDo2);
        toDoList.add(toDo3);

        return toDoList;
    }
}