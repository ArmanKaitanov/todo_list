package com.armankaitanov.todo_list.mapper;

import com.armankaitanov.todo_list.dto.response.UserResponseDto;
import com.armankaitanov.todo_list.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto toUserResponseDto(User user) {
        if(user == null) {
            return null;
        }

        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isActive()
        );
    }
}
