package com.armankaitanov.todo_list.service;

import com.armankaitanov.todo_list.dto.request.TodoItemRequestDto;
import com.armankaitanov.todo_list.model.entity.TodoItem;

import java.util.List;
import java.util.UUID;

public interface TodoItemService {

    UUID save(TodoItemRequestDto dto);

    List<TodoItem> getAllTodoItemsByUserId(UUID userId);

    TodoItem getTodoItemById(UUID id);

    TodoItem updateTodoItem(UUID id, TodoItem todoItem);

    void deleteTodoItem(UUID id);
}
