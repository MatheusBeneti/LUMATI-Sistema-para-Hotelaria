package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoomRecordDto(@NotBlank String clientName, @NotBlank String inDate, @NotBlank String outDate, @NotNull
                            Integer numberRoom, @NotBlank String roomType) {

}
