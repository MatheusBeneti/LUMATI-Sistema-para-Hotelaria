package com.example.springboot.models;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.UUID;

@Entity
@Table (name = "TB_ROOMS")
public class RoomModel extends RepresentationModel<RoomModel> implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idRoom;
    private Integer numberRoom;
    private String clientName;
    private String roomType;
    private String inDate;
    private String outDate;
    private Integer nightlyRate = 0;
    private Integer serviceFee;
    private Integer penthouseFee;
    private String clientCPF;
    private ClientModel client;
    private boolean isOccupied = false;
    private boolean cafeDaManha = true;

    public Boolean getCafeDaManha() {
        return cafeDaManha;
    }
    public void setCafeDaManha(Boolean cafeDaManha) {
        this.cafeDaManha = cafeDaManha;
    }
    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    public String getClientCPF() {
        return clientCPF;
    }

    public void setClientCPF(String clientCPF) {
        this.clientCPF = clientCPF;
    }

    public Integer getNightlyRate() {
        return nightlyRate;
    }

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

    public UUID getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(UUID idRoom) {
        this.idRoom = idRoom;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public Integer getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(Integer numberRoom) {
        this.numberRoom = numberRoom;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}

