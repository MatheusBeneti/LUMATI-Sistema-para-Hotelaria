package com.example.springboot.dtos;

import com.example.springboot.repositories.RoomRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record RoomRecordDto(@NotBlank String clientName, @NotBlank String clientCPF, @NotBlank String inDate, @NotBlank String outDate, @NotNull
                            Integer numberRoom, @NotBlank String roomType, @NotNull boolean isOccupied
        ,@NotNull boolean cafeDaManha) {

}
