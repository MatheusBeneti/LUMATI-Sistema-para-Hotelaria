package com.example.springboot.state;

import com.example.springboot.models.RoomModel;

public class OccupiedState implements RoomState {
    @Override
    public void handle(RoomModel room) {
        room.setOccupied(true);
    }
}
