package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;
import java.util.UUID;

public record RoomRecordDto(@NotBlank String clientName, @NotBlank String inDate, @NotBlank String outDate, @NotBlank
                            BigInteger numberRoom) {

}
