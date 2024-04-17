package com.example.springboot.decorator;

import com.example.springboot.models.RoomModel;

public class CafeDaManhaDecorator implements RoomDecorator {

    @Override
    public int getValorAdicional() {
        return 10;
    }

    @Override
    public RoomModel decorate(RoomModel quarto) {
        quarto.setNightlyRate(quarto.getNightlyRate() + getValorAdicional());

        return quarto;
    }
}