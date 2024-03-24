package com.mike.projects.fitsynergy.users.service;

import com.mike.projects.fitsynergy.users.exception.UserNotFoundException;
import com.mike.projects.fitsynergy.users.mapper.ClientMapper;
import com.mike.projects.fitsynergy.users.model.Client;
import com.mike.projects.fitsynergy.users.repo.ClientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepo clientRepo;

    public List<Client> getAllClients(){
        return clientRepo.findAll();
    }

    public Client getClientById(Integer id){
        return clientRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Cannot find client with id " + id));
    }

    public Client getClientByEmail(String email){
        return clientRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Cannot find client with email " + email));
    }

    public Client saveClient(Client client){
        return clientRepo.save(client);
    }

    public Client updateClientData(Integer id, Client client){
        Client clientFromDb = clientRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Cannot find client with id " + id));
        ClientMapper.mapClient(client, clientFromDb);
        return clientRepo.save(clientFromDb);
    }

    public void deleteTrainer(Integer id){
        Client clientFromDb = clientRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Cannot find client with id " + id));
        clientRepo.delete(clientFromDb);
    }


}
