package com.example.springboot.decorator;

import com.example.springboot.models.RoomModel;

public interface RoomDecorator {
    int getValorAdicional();
    RoomModel decorate(RoomModel room);
}
