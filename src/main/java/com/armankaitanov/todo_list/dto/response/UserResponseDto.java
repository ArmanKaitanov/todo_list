package com.armankaitanov.todo_list.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private boolean isActive;
}
