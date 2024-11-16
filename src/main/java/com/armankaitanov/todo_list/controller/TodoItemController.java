package com.armankaitanov.todo_list.controller;

import com.armankaitanov.todo_list.dto.request.TodoItemRequestDto;
import com.armankaitanov.todo_list.dto.response.TodoItemResponseDto;
import com.armankaitanov.todo_list.mapper.TodoItemMapper;
import com.armankaitanov.todo_list.model.entity.TodoItem;
import com.armankaitanov.todo_list.service.TodoItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todo-items")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Validated
public class TodoItemController {

    private final TodoItemService todoItemService;

    private final TodoItemMapper todoItemMapper;

    @PostMapping
    public ResponseEntity<UUID> createTodoItem(@Valid @RequestBody TodoItemRequestDto dto) {
        return new ResponseEntity<>(todoItemService.save(dto), HttpStatus.CREATED);
    }

    @GetMapping("/user-id/{userId}")
    public ResponseEntity<List<TodoItemResponseDto>> getAllTodoItemsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(
                todoItemService.getAllTodoItemsByUserId(userId).stream()
                        .map(todoItemMapper::toTodoItemResponseDto)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItemResponseDto> getTodoItemById(@PathVariable UUID id) {
        return ResponseEntity.ok(todoItemMapper.toTodoItemResponseDto(todoItemService.getTodoItemById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoItemResponseDto> updateTodoItem(@PathVariable UUID id, @RequestBody TodoItem todoItem) {
        return ResponseEntity.ok(todoItemMapper.toTodoItemResponseDto(todoItemService.updateTodoItem(id, todoItem)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable UUID id) {
        todoItemService.deleteTodoItem(id);

        return ResponseEntity.ok().build();
    }
}
