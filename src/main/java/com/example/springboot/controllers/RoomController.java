package com.example.springboot.controllers;

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
    public ResponseEntity<?> createSimpleRoom(@RequestBody @Valid RoomRecordDto roomRecordDto) {
        // Obtenha o nome do cliente e o CPF do RoomRecordDto
        String clientName = roomRecordDto.clientName();
        String clientCPF = roomRecordDto.clientCPF();

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

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Object> updateRoom(@PathVariable(value = "id") UUID id, @RequestBody @Valid RoomRecordDto roomRecordDto) {
        Optional<RoomModel> room0 = roomRepository.findById(id);
        if (room0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }
        var roomModel = room0.get();
        BeanUtils.copyProperties(roomRecordDto, roomModel);
        return ResponseEntity.status(HttpStatus.OK).body(roomRepository.save(roomModel));
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
}