package com.example.springboot.controllers;

import com.example.springboot.dtos.RoomRecordDto;
import com.example.springboot.models.RoomModel;
import com.example.springboot.repositories.RoomRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
