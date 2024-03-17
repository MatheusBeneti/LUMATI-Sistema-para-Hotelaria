package com.example.springboot.repositories;

import com.example.springboot.models.ClientModel;
import com.example.springboot.models.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, UUID> {
    boolean existsByClientCPF(String clientCPF);
    Optional<ClientModel> findByClientNameAndClientCPF(String clientName, String clientCPF);
}
