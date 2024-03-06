package com.example.springboot.controllers;

import com.example.springboot.dtos.RoomRecordDto;
import com.example.springboot.models.RoomModel;
import com.example.springboot.repositories.RoomRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@RestController
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @PostMapping("/rooms")
    public ResponseEntity<RoomModel> saveRoom(@RequestBody @Valid RoomRecordDto roomRecordDto) {
        var roomModel = new RoomModel();
        BeanUtils.copyProperties(roomRecordDto, roomModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomRepository.save(roomModel));
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomModel>> getAllRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(roomRepository.findAll());
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Object> getOneRoom(@PathVariable(value = "id") UUID id) {
        Optional<RoomModel> room0 = roomRepository.findById(id);
        if (room0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(room0.get());
    }

    @PutMapping("rooms/{id}")
    public ResponseEntity<Object> updateRoom(@PathVariable(value = "id") UUID id, @RequestBody @Valid RoomRecordDto roomRecordDto) {
        Optional<RoomModel> room0 = roomRepository.findById(id);
        if (room0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }
        var roomModel = room0.get();
        BeanUtils.copyProperties(roomRecordDto, roomModel);
        return ResponseEntity.status(HttpStatus.OK).body(roomRepository.save(roomModel));
    }

    @DeleteMapping("rooms/{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable(value = "id") UUID id) {
        Optional<RoomModel> room0 = roomRepository.findById(id);
        if (room0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }
        roomRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Room deleted successfully");
    }
}