package com.armankaitanov.todo_list.service;

import com.armankaitanov.todo_list.dto.request.TodoItemRequestDto;
import com.armankaitanov.todo_list.model.entity.TodoItem;
import com.armankaitanov.todo_list.model.entity.User;
import com.armankaitanov.todo_list.repository.TodoItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoItemServiceImplTest {

    @Mock
    private TodoItemRepository todoItemRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TodoItemServiceImpl todoItemService;

    private User user;
    private TodoItem todoItem;
    private TodoItemRequestDto todoItemRequestDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@example.com");

        todoItem = TodoItem.builder()
                .title("Test Title")
                .description("Test Description")
                .user(user)
                .build();

        todoItemRequestDto = new TodoItemRequestDto();
        todoItemRequestDto.setUserId(user.getId());
        todoItemRequestDto.setTitle("Test Title");
        todoItemRequestDto.setDescription("Test Description");
    }

    @Test
    void save_shouldSaveTodoItemSuccessfully_whenExistingUserProvided() {
        // given
        when(userService.getUserById(todoItemRequestDto.getUserId())).thenReturn(user);
        when(todoItemRepository.save(todoItem)).thenReturn(todoItem);

        // when
        todoItemService.save(todoItemRequestDto);

        // then
        verify(userService, times(1)).getUserById(todoItemRequestDto.getUserId());
        verify(todoItemRepository, times(1)).save(todoItem);
    }

    @Test
    void save_shouldThrowEntityNotFoundException_whenNonExistingUserProvided() {
        // given
        when(userService.getUserById(todoItemRequestDto.getUserId())).thenThrow(new EntityNotFoundException());

        // when // then
        assertThrows(EntityNotFoundException.class, () -> todoItemService.save(todoItemRequestDto));
    }

    @Test
    void getAllTodoItemsByUserId_shouldReturnTodoItems_whenExistingUserProvided() {
        // given
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(todoItemRepository.findByUser(user)).thenReturn(List.of(todoItem));

        // when
        List<TodoItem> todoItems = todoItemService.getAllTodoItemsByUserId(user.getId());

        // then
        assertNotNull(todoItems);
        assertEquals(1, todoItems.size());
        assertEquals(todoItem, todoItems.get(0));
        verify(todoItemRepository, times(1)).findByUser(user);
    }

    @Test
    void getAllTodoItemsByUserId_shouldThrowEntityNotFoundException_whenNonExistingUserProvided() {
        // given
        when(userService.getUserById(user.getId())).thenThrow(EntityNotFoundException.class);

        // when // then
        assertThrows(EntityNotFoundException.class, () -> todoItemService.getAllTodoItemsByUserId(user.getId()));
    }

    @Test
    void getTodoItemById_shouldReturnTodoItemSuccessfully_whenExistingTodoItemIdProvided() {
        // given
        when(todoItemRepository.findById(todoItem.getId())).thenReturn(Optional.of(todoItem));

        // when
        TodoItem foundTodoItem = todoItemService.getTodoItemById(todoItem.getId());

        // then
        assertNotNull(foundTodoItem);
        assertEquals(todoItem, foundTodoItem);
        verify(todoItemRepository, times(1)).findById(todoItem.getId());
    }

    @Test
    void getTodoItemById_shouldThrowEntityNotFoundException_whenNonExistingTodoItemIdProvided() {
        // given
        when(todoItemRepository.findById(todoItem.getId())).thenReturn(Optional.empty());

        // when // then
        assertThrows(EntityNotFoundException.class, () -> todoItemService.getTodoItemById(todoItem.getId()));
    }

    @Test
    void updateTodoItem_shouldUpdateTodoItemSuccessfully_whenExistingTodoItemIdProvided() {
        // given
        UUID todoItemId = UUID.randomUUID();
        TodoItem updatedTodoItem = TodoItem.builder()
                .id(todoItemId)
                .title("Updated Title")
                .description("Updated Description")
                .build();

        when(todoItemRepository.findById(todoItemId)).thenReturn(Optional.of(todoItem));

        // when
        TodoItem result = todoItemService.updateTodoItem(todoItemId, updatedTodoItem);

        // then
        assertNotNull(result);
        assertEquals(updatedTodoItem.getTitle(), result.getTitle());
        assertEquals(updatedTodoItem.getDescription(), result.getDescription());
        verify(todoItemRepository, times(1)).findById(todoItemId);
    }

    @Test
    void updateTodoItem_shouldUpdateOnlyTitle_whenOnlyUpdatedTitleProvided() {
        // given
        UUID todoItemId = UUID.randomUUID();
        TodoItem updatedTodoItem = TodoItem.builder()
                .id(todoItemId)
                .title("Updated Title")
                .build();
        String oldDescription = todoItem.getDescription();

        when(todoItemRepository.findById(todoItemId)).thenReturn(Optional.of(todoItem));

        // when
        TodoItem result = todoItemService.updateTodoItem(todoItemId, updatedTodoItem);

        // then
        assertNotNull(result);
        assertEquals(updatedTodoItem.getTitle(), result.getTitle());
        assertEquals(oldDescription, result.getDescription());
        verify(todoItemRepository, times(1)).findById(todoItemId);
    }

    @Test
    void updateTodoItem_shouldUpdateOnlyDescription_whenOnlyUpdatedDescriptionProvided() {
        // given
        UUID todoItemId = UUID.randomUUID();
        TodoItem updatedTodoItem = TodoItem.builder()
                .id(todoItemId)
                .description("Updated Description")
                .build();
        String oldTitle = todoItem.getTitle();

        when(todoItemRepository.findById(todoItemId)).thenReturn(Optional.of(todoItem));

        // when
        TodoItem result = todoItemService.updateTodoItem(todoItemId, updatedTodoItem);

        // then
        assertNotNull(result);
        assertEquals(updatedTodoItem.getDescription(), result.getDescription());
        assertEquals(oldTitle, result.getTitle());
        verify(todoItemRepository, times(1)).findById(todoItemId);
    }

    @Test
    void updateTodoItem_shouldThrowEntityNotFoundException_whenNonExistingTodoItemIdProvided() {
        // given
        UUID nonExistingTodoItemId = UUID.randomUUID();
        when(todoItemRepository.findById(nonExistingTodoItemId)).thenReturn(Optional.empty());

        // when // then
        assertThrows(EntityNotFoundException.class, () -> todoItemService.updateTodoItem(nonExistingTodoItemId, todoItem));
    }

    @Test
    void deleteTodoItem_shouldDeleteTodoItemSuccessfully_whenExistingTodoItemIdProvided() {
        // given
        UUID todoItemId = UUID.randomUUID();

        when(todoItemRepository.findById(todoItemId)).thenReturn(Optional.of(todoItem));

        // when
        todoItemService.deleteTodoItem(todoItemId);

        // then
        verify(todoItemRepository, times(1)).delete(todoItem);
    }

    @Test
    void deleteTodoItem_shouldThrowEntityNotFoundException_whenNonExistingTodoItemIdProvided() {
        // given
        UUID nonExistingTodoItemId = UUID.randomUUID();
        when(todoItemRepository.findById(nonExistingTodoItemId)).thenReturn(Optional.empty());

        // when // then
        assertThrows(EntityNotFoundException.class, () -> todoItemService.deleteTodoItem(nonExistingTodoItemId));
        verify(todoItemRepository, never()).delete(any(TodoItem.class));
    }
}
