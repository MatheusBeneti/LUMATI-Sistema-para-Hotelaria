package com.example.springboot.controllers;

import com.example.springboot.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @Autowired
    ClientRepository clientRepository;
}
