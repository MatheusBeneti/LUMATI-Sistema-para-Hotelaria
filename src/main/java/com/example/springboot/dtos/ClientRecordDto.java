package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientRecordDto(@NotNull Integer clientBDay, @NotBlank String clientName, @NotBlank String clientCPF) {
}
