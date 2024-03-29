package com.example.springboot.controllers;

import com.example.springboot.builder.ClientBuilder;
import com.example.springboot.builder.ClientBuilderImpl;
import com.example.springboot.dtos.ClientRecordDto;
import com.example.springboot.models.ClientModel;
import com.example.springboot.repositories.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @PostMapping("/clients")
    public ResponseEntity<Object> saveClient(@RequestBody @Valid ClientRecordDto clientRecordDto, BindingResult bindingResult) {

        // Verificação de erros de validação
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getField() + ": " + error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        // Verificação de CPF já cadastrado
        if (clientRepository.existsByClientCPF(clientRecordDto.clientCPF())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF já cadastrado.");
        }

        // Criação do ClientRecordDto usando a interface builder
        ClientBuilder builder = new ClientBuilderImpl();
        ClientRecordDto clientRecordDtoFinal = builder.setClientName(clientRecordDto.clientName())
                .setClientCPF(clientRecordDto.clientCPF())
                .setClientBDay(clientRecordDto.clientBDay())
                .build();

        // Criar o ClientModel manualmente
        var clientModel = new ClientModel();
        clientModel.setClientBDay(clientRecordDtoFinal.clientBDay());
        clientModel.setClientName(clientRecordDtoFinal.clientName());
        clientModel.setClientCPF(clientRecordDtoFinal.clientCPF());

        // Salvamento do ClientModel
        return ResponseEntity.status(HttpStatus.CREATED).body(clientRepository.save(clientModel));
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientModel>> getAllClients() {
        List<ClientModel> clientList = clientRepository.findAll();
        if(!clientList.isEmpty()){
            for(ClientModel client : clientList){
                UUID id = client.getIdClient();
                client.add(linkTo(methodOn(ClientController.class).getOneClient(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientList);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Object> getOneClient(@PathVariable(value = "id") UUID id) {
        Optional<ClientModel> client0 = clientRepository.findById(id);
        if (client0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found.");
        }
        client0.get().add(linkTo(methodOn(ClientController.class).getAllClients()).withRel("Client List."));
        return ResponseEntity.status(HttpStatus.OK).body(client0.get());
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Object> updateClient(@PathVariable(value = "id") UUID id, @RequestBody @Valid ClientRecordDto clientRecordDto) {
        Optional<ClientModel> client0 = clientRepository.findById(id);
        if (client0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found.");
        }
        var clientModel = client0.get();
        BeanUtils.copyProperties(clientRecordDto, clientModel);
        return ResponseEntity.status(HttpStatus.OK).body(clientRepository.save(clientModel));
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable(value = "id") UUID id) {
        Optional<ClientModel> client0 = clientRepository.findById(id);
        if (client0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found.");
        }
        clientRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Client deleted successfully");
    }
}
