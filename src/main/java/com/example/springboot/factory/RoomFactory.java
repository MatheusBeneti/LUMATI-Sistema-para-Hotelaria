package com.example.springboot.factory;

import com.example.springboot.dtos.RoomRecordDto;
import com.example.springboot.models.RoomModel;

public interface RoomFactory {
    RoomModel createRoom(RoomRecordDto roomRecordDto);
}