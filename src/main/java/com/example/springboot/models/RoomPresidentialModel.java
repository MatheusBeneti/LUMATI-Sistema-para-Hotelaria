package com.example.springboot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class RoomPresidentialModel extends RoomModel {
    @Transient
    private String dtype;

}
