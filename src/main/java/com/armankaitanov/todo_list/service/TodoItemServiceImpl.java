package com.armankaitanov.todo_list.service;

import com.armankaitanov.todo_list.dto.request.TodoItemRequestDto;
import com.armankaitanov.todo_list.model.entity.TodoItem;
import com.armankaitanov.todo_list.model.entity.User;
import com.armankaitanov.todo_list.repository.TodoItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TodoItemServiceImpl implements TodoItemService {

    private final TodoItemRepository todoItemRepository;

    private final UserService userService;

    @Override
    @Transactional
    public UUID save(TodoItemRequestDto dto) {
        User user = userService.getUserById(dto.getUserId());

        TodoItem todoItem = TodoItem.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .user(user)
                .build();

        return todoItemRepository.save(todoItem).getId();
    }

    @Override
    public List<TodoItem> getAllTodoItemsByUserId(UUID userId) {
        User user = userService.getUserById(userId);

        return todoItemRepository.findByUser(user);
    }

    @Override
    public TodoItem getTodoItemById(UUID id) {
        return todoItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("TodoItem with id %s not found", id)));
    }

    @Override
    @Transactional
    public TodoItem updateTodoItem(UUID id, TodoItem updatedTodoItem) {
        TodoItem todoItemToUpdate = getTodoItemById(id);
        updateTodoItemFields(todoItemToUpdate, updatedTodoItem);

        return todoItemToUpdate;
    }

    @Override
    @Transactional
    public void deleteTodoItem(UUID id) {
        todoItemRepository.findById(id).ifPresent(todoItemRepository::delete);
    }

    private void updateTodoItemFields(TodoItem todoItemToUpdate, TodoItem updatedTodoItem) {
        if(updatedTodoItem.getTitle() != null) {
            todoItemToUpdate.setTitle(updatedTodoItem.getTitle());
        }
        if(updatedTodoItem.getDescription() != null) {
            todoItemToUpdate.setDescription(updatedTodoItem.getDescription());
        }
    }


}
