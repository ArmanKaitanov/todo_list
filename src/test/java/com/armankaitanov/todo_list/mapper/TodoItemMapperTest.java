package com.armankaitanov.todo_list.mapper;

import com.armankaitanov.todo_list.dto.response.TodoItemResponseDto;
import com.armankaitanov.todo_list.model.entity.TodoItem;
import com.armankaitanov.todo_list.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class TodoItemMapperTest {

    private TodoItemMapper todoItemMapper;

    @BeforeEach
    void setUp() {
        todoItemMapper = new TodoItemMapper();
    }

    @Test
    void toTodoItemResponseDto_shouldReturnDtoSuccessfully_whenValidTodoItemProvided() {
        // given
        User user = new User();
        user.setId(UUID.randomUUID());

        TodoItem todoItem = new TodoItem();
        todoItem.setId(UUID.randomUUID());
        todoItem.setTitle("Test Title");
        todoItem.setDescription("Test Description");
        todoItem.setUser(user);

        // when
        TodoItemResponseDto result = todoItemMapper.toTodoItemResponseDto(todoItem);

        // then
        assertNotNull(result);
        assertEquals(todoItem.getId(), result.getId());
        assertEquals(todoItem.getTitle(), result.getTitle());
        assertEquals(todoItem.getDescription(), result.getDescription());
        assertEquals(todoItem.getUser().getId(), result.getUserId());
    }

    @Test
    void toTodoItemResponseDto_shouldReturnNull_whenNullTodoItemProvided() {
        // when
        TodoItemResponseDto result = todoItemMapper.toTodoItemResponseDto(null);

        // then
        assertNull(result);
    }

    @Test
    void toTodoItemResponseDto_shouldHandleNullTitleAndDescriptionGracefully() {
        // given
        User user = new User();
        user.setId(UUID.randomUUID());

        TodoItem todoItem = new TodoItem();
        todoItem.setId(UUID.randomUUID());
        todoItem.setTitle(null);
        todoItem.setDescription(null);
        todoItem.setUser(user);

        // when
        TodoItemResponseDto result = todoItemMapper.toTodoItemResponseDto(todoItem);

        // then
        assertNotNull(result);
        assertEquals(todoItem.getId(), result.getId());
        assertNull(result.getTitle());
        assertNull(result.getDescription());
        assertEquals(user.getId(), result.getUserId());
    }
}
