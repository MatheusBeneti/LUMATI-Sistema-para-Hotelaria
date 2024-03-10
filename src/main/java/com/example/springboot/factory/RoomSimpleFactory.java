package com.example.springboot.factory;

import com.example.springboot.dtos.RoomRecordDto;
import com.example.springboot.factory.RoomFactory;
import com.example.springboot.models.RoomModel;
import com.example.springboot.models.RoomSimpleModel;
import com.example.springboot.repositories.RoomRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class RoomSimpleFactory implements RoomFactory {

    private final RoomRepository roomRepository;  // Suponha que você tenha um repositório para persistir os dados

    // Injeção de dependência do repositório
    public RoomSimpleFactory(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomModel createRoom(RoomRecordDto roomRecordDto) {
        var roomModel = new RoomModel();
        BeanUtils.copyProperties(roomRecordDto, roomModel);

        // Lógica de salvamento no banco de dados
        RoomModel savedRoom = roomRepository.save(roomModel);

        return savedRoom;
    }
}
