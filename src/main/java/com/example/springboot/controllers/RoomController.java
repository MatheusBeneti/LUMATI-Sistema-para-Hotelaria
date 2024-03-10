package com.example.springboot.controllers;

import com.example.springboot.dtos.RoomRecordDto;
import com.example.springboot.factory.RoomFactory;
import com.example.springboot.factory.RoomSimpleFactory;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    private RoomSimpleFactory roomSimpleFactory;

    @PostMapping("/rooms/simple")
    public ResponseEntity<RoomModel> createSimpleRoom(@RequestBody @Valid RoomRecordDto roomRecordDto) {
        RoomModel roomModel = roomSimpleFactory.createRoom(roomRecordDto);

        // Assuming successful room creation
        if (roomModel != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(roomModel);
        } else {
            // Handle potential room creation failure (optional)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/rooms")
    public ResponseEntity<RoomModel> saveRoom(@RequestBody @Valid RoomRecordDto roomRecordDto) {
        var roomModel = new RoomModel();
        BeanUtils.copyProperties(roomRecordDto, roomModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomRepository.save(roomModel));
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomModel>> getAllRooms() {
        List<RoomModel> roomList = roomRepository.findAll();
        if(!roomList.isEmpty()){
            for(RoomModel room : roomList){
                UUID id = room.getIdRoom();
                room.add(linkTo(methodOn(RoomController.class).getOneRoom(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(roomList);
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Object> getOneRoom(@PathVariable(value = "id") UUID id) {
        Optional<RoomModel> room0 = roomRepository.findById(id);
        if (room0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }
        room0.get().add(linkTo(methodOn(RoomController.class).getAllRooms()).withRel("Rooms List."));
        return ResponseEntity.status(HttpStatus.OK).body(room0.get());
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Object> updateRoom(@PathVariable(value = "id") UUID id, @RequestBody @Valid RoomRecordDto roomRecordDto) {
        Optional<RoomModel> room0 = roomRepository.findById(id);
        if (room0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }
        var roomModel = room0.get();
        BeanUtils.copyProperties(roomRecordDto, roomModel);
        return ResponseEntity.status(HttpStatus.OK).body(roomRepository.save(roomModel));
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable(value = "id") UUID id) {
        Optional<RoomModel> room0 = roomRepository.findById(id);
        if (room0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }
        roomRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Room deleted successfully");
    }
}