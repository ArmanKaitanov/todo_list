package com.armankaitanov.todo_list.controller;

import com.armankaitanov.todo_list.dto.request.RegisterUserRequestDto;
import com.armankaitanov.todo_list.dto.response.UserResponseDto;
import com.armankaitanov.todo_list.mapper.UserMapper;
import com.armankaitanov.todo_list.model.entity.User;
import com.armankaitanov.todo_list.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    // Endpoint to create or update a user
    @PostMapping
    public ResponseEntity<UUID> createUser(@Valid @RequestBody RegisterUserRequestDto dto) {
        return new ResponseEntity<>(userService.saveUser(dto), HttpStatus.CREATED);
    }

    // Endpoint to get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userMapper.toUserResponseDto(userService.getUserById(id)));
    }

    // Endpoint to get all users
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers().stream()
                .map(userMapper::toUserResponseDto)
                .toList());
    }

    // Endpoint to delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);

        return ResponseEntity.ok().build();
    }

    // Endpoint to update a user by ID
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID id, @RequestBody User updatedUser) {
        return ResponseEntity.ok(userMapper.toUserResponseDto(userService.updateUser(id, updatedUser)));
    }
}
