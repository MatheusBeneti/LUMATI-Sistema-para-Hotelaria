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

    private final RoomRepository roomRepository;  // Suponha que você tenha um repositório para persistir os dados

    // Injeção de dependência do repositório
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
                roomModel.setNightlyRate(100);
                roomModel.setServiceFee(0);
                roomModel.setPenthouseFee(0);
                BeanUtils.copyProperties(roomRecordDto, roomModel);
                break;
            case "luxury":
                roomModel = new RoomLuxuryModel();
                roomModel.setNightlyRate(300);
                roomModel.setServiceFee(20);
                roomModel.setPenthouseFee(0);
                BeanUtils.copyProperties(roomRecordDto, roomModel);
                break;
            case "presidential":
                roomModel = new RoomPresidentialModel();
                roomModel.setNightlyRate(5);
                roomModel.setServiceFee(20);
                roomModel.setPenthouseFee(200);
                BeanUtils.copyProperties(roomRecordDto, roomModel);
                break;
            default:
                System.out.println("Opção não reconhecida.");
        }


        // Lógica de salvamento no banco de dados
        RoomModel savedRoom = roomRepository.save(roomModel);

        return savedRoom;
    }
}
