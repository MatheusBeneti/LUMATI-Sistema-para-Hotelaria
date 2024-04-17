package com.example.springboot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class RoomSimpleModel extends RoomModel {
    @Transient
    private String dtype;

    public RoomSimpleModel() {
        // Define os valores padrão para RoomSimpleModel
        this.nightlyRate = 100;
        this.serviceFee = 0;
        this.penthouseFee = 0;
    }

    private Integer nightlyRate;
    private Integer serviceFee;
    private Integer penthouseFee;

    @Override
    public Integer getNightlyRate() {
        return nightlyRate;
    }

    @Override
    public void setNightlyRate(Integer nightlyRate) {
        this.nightlyRate = nightlyRate;
    }

    public Integer getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Integer serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Integer getPenthouseFee() {
        return penthouseFee;
    }

    public void setPenthouseFee(Integer penthouseFee) {
        this.penthouseFee = penthouseFee;
    }
}
