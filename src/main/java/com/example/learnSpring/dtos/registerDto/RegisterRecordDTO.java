package com.example.learnSpring.dtos.registerDto;

import com.example.learnSpring.models.users.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRecordDTO(@NotBlank String login, @NotBlank String password, @NotNull UserRole role) {
}
