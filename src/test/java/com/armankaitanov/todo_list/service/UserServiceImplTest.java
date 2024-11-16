package com.armankaitanov.todo_list.service;

import com.armankaitanov.todo_list.dto.request.RegisterUserRequestDto;
import com.armankaitanov.todo_list.exception.EmailAlreadyExistsException;
import com.armankaitanov.todo_list.model.entity.User;
import com.armankaitanov.todo_list.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private RegisterUserRequestDto registerUserRequestDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("encodedPassword");
        user.setActive(true);

        registerUserRequestDto = new RegisterUserRequestDto();
        registerUserRequestDto.setFirstName("John");
        registerUserRequestDto.setLastName("Doe");
        registerUserRequestDto.setEmail("john.doe@example.com");
        registerUserRequestDto.setPassword("plainPassword");
    }

    @Test
    void getAllUsers_shouldReturnListOfUsersSuccessfully() {
        // given
        when(userRepository.findAll()).thenReturn(List.of(user));

        // when
        List<User> users = userService.getAllUsers();

        // then
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_shouldReturnUserSuccessfully_whenExistingUserIdProvided() {
        // given
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // when
        User foundUser = userService.getUserById(user.getId());

        // then
        assertNotNull(foundUser);
        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void getUserById_shouldThrowEntityNotFoundException_whenNonExistingUserIdProvided() {
        // given
        UUID nonExistingUserId = UUID.randomUUID();
        when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());

        // when // then
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(nonExistingUserId));
    }

    @Test
    void saveUser_shouldSaveUserSuccessfully_whenUniqueUserEmailProvided() {
        // given
        when(userRepository.existsByEmail(registerUserRequestDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerUserRequestDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // when
        UUID savedUserId = userService.saveUser(registerUserRequestDto);

        // then
        assertNotNull(savedUserId);
        assertEquals(user.getId(), savedUserId);
        verify(userRepository, times(1)).existsByEmail(registerUserRequestDto.getEmail());
        verify(passwordEncoder, times(1)).encode(registerUserRequestDto.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void saveUser_shouldThrowEmailAlreadyExistsException_whenUserEmailAlreadyExists() {
        // given
        when(userRepository.existsByEmail(registerUserRequestDto.getEmail())).thenReturn(true);

        // when // then
        assertThrows(EmailAlreadyExistsException.class, () -> userService.saveUser(registerUserRequestDto));
    }

    @Test
    void updateUser_shouldUpdateUserSuccessfully_whenExistingUserIdProvided() {
        // given
        UUID userId = UUID.randomUUID();
        User updatedUser = new User();
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Doe");
        updatedUser.setEmail("jane.doe@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        User result = userService.updateUser(userId, updatedUser);

        // then
        assertNotNull(result);
        assertEquals(updatedUser.getFirstName(), result.getFirstName());
        assertEquals(updatedUser.getLastName(), result.getLastName());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void updateUser_shouldUpdateOnlyFirstName_whenOnlyUpdatedFirstNameProvided() {
        // given
        UUID userId = UUID.randomUUID();
        User updatedUser = new User();
        updatedUser.setFirstName("UpdatedFirstName");
        String oldLastName = user.getLastName();
        String oldEmail = user.getEmail();
        String oldPassword = user.getPassword();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        User result = userService.updateUser(userId, updatedUser);

        // then
        assertNotNull(result);
        assertEquals(updatedUser.getFirstName(), result.getFirstName());
        assertEquals(oldLastName, result.getLastName());
        assertEquals(oldEmail, result.getEmail());
        assertEquals(oldPassword, result.getPassword());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void updateUser_shouldUpdateOnlyLastName_whenOnlyUpdatedLastNameProvided() {
        // given
        UUID userId = UUID.randomUUID();
        User updatedUser = new User();
        updatedUser.setLastName("UpdatedLastName");
        String oldFirstName = user.getFirstName();
        String oldEmail = user.getEmail();
        String oldPassword = user.getPassword();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        User result = userService.updateUser(userId, updatedUser);

        // then
        assertNotNull(result);
        assertEquals(updatedUser.getLastName(), result.getLastName());
        assertEquals(oldFirstName, result.getFirstName());
        assertEquals(oldEmail, result.getEmail());
        assertEquals(oldPassword, result.getPassword());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void updateUser_shouldUpdateOnlyEmail_whenOnlyUpdatedEmailProvided() {
        // given
        UUID userId = UUID.randomUUID();
        User updatedUser = new User();
        updatedUser.setEmail("UpdatedEmail");
        String oldFirstName = user.getFirstName();
        String oldLastName = user.getLastName();
        String oldPassword = user.getPassword();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        User result = userService.updateUser(userId, updatedUser);

        // then
        assertNotNull(result);
        assertEquals(updatedUser.getEmail(), result.getEmail());
        assertEquals(oldFirstName, result.getFirstName());
        assertEquals(oldLastName, result.getLastName());
        assertEquals(oldPassword, result.getPassword());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void updateUser_shouldThrowEntityNotFoundException_whenNonExistingUserIdProvided() {
        // given
        UUID nonExistingUserId = UUID.randomUUID();
        User updatedUser = new User();
        updatedUser.setFirstName("Jane");

        when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());

        // when // then
        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(nonExistingUserId, updatedUser));
    }

    @Test
    void deleteUser_shouldDeleteUserSuccessfully_whenExistingUserIdProvided() {
        // given
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        userService.deleteUser(userId);

        // then
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUser_shouldThrowException_whenNonExistingUserIdProvided() {
        // given
        UUID nonExistingUserId = UUID.randomUUID();
        when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());

        // when // then
        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(nonExistingUserId));
        verify(userRepository, never()).delete(any(User.class));
    }
}
