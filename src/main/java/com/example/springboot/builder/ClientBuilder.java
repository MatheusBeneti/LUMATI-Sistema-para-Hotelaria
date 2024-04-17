package com.example.springboot.builder;

import com.example.springboot.dtos.ClientRecordDto;

public interface ClientBuilder {
    ClientBuilder setClientName(String clientName);

    ClientBuilder setClientCPF(String clientCPF);

    ClientBuilder setClientBDay(Integer clientBDay);

    ClientRecordDto build();
}
