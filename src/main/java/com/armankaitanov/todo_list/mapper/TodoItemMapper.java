package com.armankaitanov.todo_list.mapper;

import com.armankaitanov.todo_list.dto.response.TodoItemResponseDto;
import com.armankaitanov.todo_list.model.entity.TodoItem;
import org.springframework.stereotype.Component;

@Component
public class TodoItemMapper {

    public TodoItemResponseDto toTodoItemResponseDto(TodoItem todoItem) {
        if(todoItem == null) {
            return null;
        }

        return new TodoItemResponseDto(
                todoItem.getId(),
                todoItem.getTitle(),
                todoItem.getDescription(),
                todoItem.getUser().getId()
        );
    }
}
