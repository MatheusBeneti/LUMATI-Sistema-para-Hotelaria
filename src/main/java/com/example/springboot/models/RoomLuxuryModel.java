package com.example.springboot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class RoomLuxuryModel extends RoomModel {
    @Transient
    private String dtype;

    private Integer nightlyRate = 300;
    private Integer serviceFee = 20;

    @Override
    public Integer getNightlyRate() {
        return nightlyRate;
    }

    @Override
    public void setNightlyRate(Integer nightlyRate) {
        this.nightlyRate = nightlyRate;
    }

    @Override
    public Integer getServiceFee() {
        return serviceFee;
    }

    @Override
    public void setServiceFee(Integer serviceFee) {
        this.serviceFee = serviceFee;
    }
}
