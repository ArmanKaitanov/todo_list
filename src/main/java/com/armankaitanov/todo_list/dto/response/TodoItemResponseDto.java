package com.armankaitanov.todo_list.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TodoItemResponseDto {

    private UUID id;

    private String title;

    private String description;

    private UUID userId;
}
