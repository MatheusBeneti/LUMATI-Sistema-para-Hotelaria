package com.example.springboot.factory;

import com.example.springboot.dtos.RoomRecordDto;
import com.example.springboot.factory.RoomFactory;
import com.example.springboot.models.RoomLuxuryModel;
import com.example.springboot.models.RoomModel;
import com.example.springboot.models.RoomPresidentialModel;
import com.example.springboot.models.RoomSimpleModel;
import com.example.springboot.repositories.RoomRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class RoomCreateFactory implements RoomFactory {

    private final RoomRepository roomRepository;

    public RoomCreateFactory(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomModel createRoom(RoomRecordDto roomRecordDto) {
        String roomType = roomRecordDto.roomType();
        var roomModel = new RoomModel();

        switch (roomType){
            case "simple":
                roomModel = new RoomSimpleModel();
                BeanUtils.copyProperties(roomRecordDto, roomModel);
                break;
            case "luxury":
                roomModel = new RoomLuxuryModel();
                BeanUtils.copyProperties(roomRecordDto, roomModel);
                break;
            case "presidential":
                roomModel = new RoomPresidentialModel();
                BeanUtils.copyProperties(roomRecordDto, roomModel);
                break;
            default:
                System.out.println("Opção não reconhecida.");
        }

        RoomModel savedRoom = roomRepository.save(roomModel);
        return savedRoom;
    }
}
