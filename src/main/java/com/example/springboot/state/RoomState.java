package com.example.springboot.state;

import com.example.springboot.models.RoomModel;

public interface RoomState {
    void handle(RoomModel room);
}
