package com.armankaitanov.todo_list.service;

import com.armankaitanov.todo_list.dto.request.RegisterUserRequestDto;
import com.armankaitanov.todo_list.model.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(UUID id);

    UUID saveUser(RegisterUserRequestDto dto);

    User updateUser(UUID id, User userDetails);

    void deleteUser(UUID id);
}

