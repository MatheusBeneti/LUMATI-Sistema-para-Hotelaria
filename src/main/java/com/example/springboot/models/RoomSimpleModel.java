package com.example.springboot.models;

import java.util.Date;

public class RoomSimpleModel extends RoomModel {

    private Integer bedCount;
    private Boolean hasWindow;

    // Getters and setters for bedCount and hasWindow

    public Integer getBedCount() {
        return bedCount;
    }

    public void setBedCount(Integer bedCount) {
        this.bedCount = bedCount;
    }

    public Boolean getHasWindow() {
        return hasWindow;
    }

    public void setHasWindow(Boolean hasWindow) {
        this.hasWindow = hasWindow;
    }

    // Método para calcular o preço da estadia
//    public Double calculatePrice(Date startDate, Date endDate, Integer numGuests) {
//        // Lógica para calcular o preço com base em parâmetros
//        // ...
//        return price;
//    }

    // Método para verificar a disponibilidade do quarto
//    public Boolean checkAvailability(Date startDate, Date endDate) {
//
//        return isAvailable;
//    }


}
