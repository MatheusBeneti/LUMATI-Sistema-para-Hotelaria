package com.example.springboot.builder;

import com.example.springboot.dtos.ClientRecordDto;

public class ClientBuilderImpl implements ClientBuilder{
    private Integer clientBDay;
    private String clientName;
    private String clientCPF;

    // Setters para cada campo
    public ClientBuilderImpl setClientBDay(Integer clientBDay) {
        this.clientBDay = clientBDay;
        return this;
    }

    public ClientBuilderImpl setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public ClientBuilderImpl setClientCPF(String clientCPF) {
        this.clientCPF = clientCPF;
        return this;
    }

    // Método para construir o objeto ClientRecordDto
    public ClientRecordDto build() {
        return new ClientRecordDto(clientBDay, clientName, clientCPF);
    }
}