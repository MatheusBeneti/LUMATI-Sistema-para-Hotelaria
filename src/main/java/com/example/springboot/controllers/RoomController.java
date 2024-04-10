package com.example.springboot.controllers;

import com.example.springboot.decorator.CafeDaManhaDecorator;
import com.example.springboot.decorator.RoomDecorator;
import com.example.springboot.dtos.RoomRecordDto;
import com.example.springboot.factory.RoomCreateFactory;
import com.example.springboot.models.ClientModel;
import com.example.springboot.models.RoomModel;
import com.example.springboot.repositories.ClientRepository;
import com.example.springboot.repositories.RoomRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    private RoomCreateFactory roomCreateFactory;

    @Autowired
    ClientRepository clientRepository;

    @PostMapping("/rooms")
    public ResponseEntity<?> createRoom(@RequestBody @Valid RoomRecordDto roomRecordDto) {
        // Obtenha o nome do cliente e o CPF do RoomRecordDto
        String clientName = roomRecordDto.clientName();
        String clientCPF = roomRecordDto.clientCPF();
        boolean clienteQuerCafeDaManha = roomRecordDto.cafeDaManha(); // Aqui obtemos a informação sobre o café da manhã

        // Verifique se o cliente já está cadastrado no banco de dados
        Optional<ClientModel> client = clientRepository.findByClientNameAndClientCPF(clientName, clientCPF);
        if (client.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Ou outra resposta adequada para o cliente não encontrado
        }

        // Verifique se o quarto está ocupado
        Optional<RoomModel> occupiedRoom = roomRepository.findByNumberRoomAndIsOccupied(roomRecordDto.numberRoom(), true);
        if (occupiedRoom.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O quarto já está ocupado."); // Ou outra resposta adequada para o quarto ocupado
        }

        // Crie o objeto RoomModel e associe o cliente a ele
        RoomModel roomModel = roomCreateFactory.createRoom(roomRecordDto);
        roomModel.setClient(client.get());
        roomModel.setClientCPF(clientCPF);
        roomModel.setOccupied(true); // Marque o quarto como ocupado

        System.out.println("Cafe da manha: " + clienteQuerCafeDaManha);
        // Defina a preferência do café da manhã no modelo do quarto
        roomModel.setCafeDaManha(clienteQuerCafeDaManha);
        // Salve o quarto no banco de dados
        roomModel = roomRepository.save(roomModel);

        // Assumindo que o salvamento do quarto foi bem-sucedido
        if (roomModel != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(roomModel);
        } else {
            // Lide com possíveis falhas na criação do quarto (opcional)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/rooms/{roomNumber}")
    public ResponseEntity<?> assignClientToRoom(@PathVariable(value = "roomNumber") int roomNumber,
                                                @RequestBody @Valid RoomRecordDto roomRecordDto) {
        // Find the room to be updated
        Optional<RoomModel> roomToUpdate = roomRepository.findByNumberRoom(roomNumber);
        if (roomToUpdate.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }

        // Retrieve client information from the request body
        String clientName = roomRecordDto.clientName();
        String clientCPF = roomRecordDto.clientCPF();

        // Validate client existence (optional: you can choose to create the client if not found)
        Optional<ClientModel> client = clientRepository.findByClientNameAndClientCPF(clientName, clientCPF);
        if (client.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not found.");
        }

        // Associate the client with the room and set check-in/check-out dates
        RoomModel roomModel = roomToUpdate.get();
        roomModel.setClient(client.get());
        roomModel.setClientCPF(clientCPF);
        roomModel.setClientName(clientName);
        roomModel.setInDate(roomRecordDto.inDate());
        roomModel.setOutDate(roomRecordDto.outDate());
        roomModel.setOccupied(true);

        // Save the updated room
        roomRepository.save(roomModel);

        return ResponseEntity.status(HttpStatus.OK).body("Client assigned to room successfully.");
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomModel>> getAllRooms() {
        List<RoomModel> roomList = roomRepository.findAll();
        if(!roomList.isEmpty()){
            for(RoomModel room : roomList){
                UUID id = room.getIdRoom();
                room.add(linkTo(methodOn(RoomController.class).getOneRoom(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(roomList);
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Object> getOneRoom(@PathVariable(value = "id") UUID id) {
        Optional<RoomModel> room0 = roomRepository.findById(id);
        if (room0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }
        room0.get().add(linkTo(methodOn(RoomController.class).getAllRooms()).withRel("Rooms List."));
        return ResponseEntity.status(HttpStatus.OK).body(room0.get());
    }



    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable(value = "id") UUID id) {
        Optional<RoomModel> room0 = roomRepository.findById(id);
        if (room0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }
        roomRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Room deleted successfully");
    }


    @DeleteMapping("/rooms/vacate/{roomNumber}")
    public ResponseEntity<Object> vacateRoom(@PathVariable(value = "roomNumber") int roomNumber) {
        Optional<RoomModel> roomToVacate = roomRepository.findByNumberRoom(roomNumber);
        if (roomToVacate.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }

        RoomModel roomModel = roomToVacate.get();

        // Clear client association and mark room as unoccupied
        roomModel.setClient(null);
        roomModel.setOutDate(null);
        roomModel.setInDate(null);
        roomModel.setClientCPF(null);
        roomModel.setClientName(null);
        roomModel.setOccupied(false);

        // Update the room in the database
        roomRepository.save(roomModel);

        return ResponseEntity.status(HttpStatus.OK).body("Room " + roomNumber + " vacated successfully.");
    }



}