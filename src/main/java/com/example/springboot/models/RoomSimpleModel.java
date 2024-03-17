package com.example.springboot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class RoomSimpleModel extends RoomModel {
    @Transient
    private String dtype;

    private Integer nightlyRate = 100;

    @Override
    public Integer getNightlyRate() {
        return nightlyRate;
    }

    @Override
    public void setNightlyRate(Integer nightlyRate) {
        this.nightlyRate = nightlyRate;
    }
}
