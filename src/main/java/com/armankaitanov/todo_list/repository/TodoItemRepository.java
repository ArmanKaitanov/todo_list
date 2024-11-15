package com.armankaitanov.todo_list.repository;

import com.armankaitanov.todo_list.model.entity.TodoItem;
import com.armankaitanov.todo_list.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TodoItemRepository extends JpaRepository<TodoItem, UUID> {

    List<TodoItem> findByUser(User user);
}
