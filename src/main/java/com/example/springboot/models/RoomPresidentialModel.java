package com.example.springboot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class RoomPresidentialModel extends RoomModel {
    @Transient
    private String dtype;

    private Integer nightlyRate = 500;
    private Integer serviceFee = 50;
    private Integer penthouseFee = 200;

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

    @Override
    public Integer getPenthouseFee() {
        return penthouseFee;
    }

    @Override
    public void setPenthouseFee(Integer penthouseFee) {
        this.penthouseFee = penthouseFee;
    }
}
