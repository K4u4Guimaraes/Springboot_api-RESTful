package com.example.learnSpring.dtos.authenticationtDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthenticationRecordDTO(@NotBlank String login,@NotBlank @NotNull String password) {
}
