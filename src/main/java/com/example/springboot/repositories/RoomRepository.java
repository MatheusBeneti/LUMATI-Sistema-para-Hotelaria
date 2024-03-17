package com.example.springboot.repositories;

import com.example.springboot.models.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository <RoomModel, UUID>{
    Optional<RoomModel> findByNumberRoomAndIsOccupied(int numberRoom, boolean isOccupied);
}
