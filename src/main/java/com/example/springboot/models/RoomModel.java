package com.example.springboot.models;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.UUID;

@Entity
@Table (name = "TB ROOMS")
public class RoomModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idRoom;
    private BigInteger numberRoom;
    private String clientName;
    private String inDate;
    private String outDate;

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

    public BigInteger getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(BigInteger numberRoom) {
        this.numberRoom = numberRoom;
    }
}
