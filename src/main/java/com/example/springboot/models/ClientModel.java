package com.example.springboot.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table (name = "TB_CLIENT")
public class ClientModel  extends RepresentationModel<ClientModel> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idClient;
    private Integer clientBDay;
    private String clientName;
    private String clientCPF;

    public UUID getIdClient() {
        return idClient;
    }

    public void setIdClient(UUID idClient) {
        this.idClient = idClient;
    }

    public Integer getClientBDay() {
        return clientBDay;
    }

    public void setClientBDay(Integer clientBDay) {
        this.clientBDay = clientBDay;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientCPF() {
        return clientCPF;
    }

    public void setClientCPF(String clientCPF) {
        this.clientCPF = clientCPF;
    }
}
