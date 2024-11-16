package com.armankaitanov.todo_list.service;

import com.armankaitanov.todo_list.dto.request.RegisterUserRequestDto;
import com.armankaitanov.todo_list.exception.EmailAlreadyExistsException;
import com.armankaitanov.todo_list.model.entity.User;
import com.armankaitanov.todo_list.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(UUID id) {
       return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s not found", id)));
    }

    @Override
    @Transactional
    public UUID saveUser(RegisterUserRequestDto dto) {
        emailValidate(dto.getEmail());

        User userToSave = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .isActive(true)
                .build();

        return userRepository.save(userToSave).getId();
    }

    private void emailValidate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(String.format("Email \"%s\" is already exists", email));
        }
    }

    @Override
    @Transactional
    public User updateUser(UUID id, User updatedUser) {
        User userToUpdate = getUserById(id);
        updateUserFields(userToUpdate, updatedUser);

        return userToUpdate;
    }

    private void updateUserFields(User userToUpdate, User updatedUser) {
        if(updatedUser.getFirstName() != null) {
            userToUpdate.setFirstName(updatedUser.getFirstName());
        }
        if(updatedUser.getLastName() != null) {
            userToUpdate.setLastName(updatedUser.getLastName());
        }
        if(updatedUser.getEmail() != null) {
            userToUpdate.setEmail(updatedUser.getEmail());
        }
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        User userToDelete = getUserById(id);
        userRepository.delete(userToDelete);
    }
}
