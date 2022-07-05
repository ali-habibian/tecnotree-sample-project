package com.habibian.tsp.service.impl;

import com.habibian.tsp.dto.ToDoDto;
import com.habibian.tsp.entity.ToDo;
import com.habibian.tsp.repository.ToDoRepository;
import com.habibian.tsp.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ali
 */
@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository toDoRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<ToDoDto> getAllTodos() {
        List<ToDo> todoList = toDoRepository.findAll();

        return todoList.stream().map(todo -> modelMapper.map(todo, ToDoDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ToDoDto> getAllByUserIdAndCompleted(long userId, boolean completed) {
        List<ToDo> toDoList = toDoRepository.findAllByUserIdAndCompletedOrderByIdAsc(userId, completed);

        if (toDoList == null) {
            return null;
        } else {
            return toDoList.stream().map(todo -> modelMapper.map(todo, ToDoDto.class)).collect(Collectors.toList());
        }
    }
}
