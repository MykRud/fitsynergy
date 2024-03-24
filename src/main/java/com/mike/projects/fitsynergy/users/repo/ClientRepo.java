package com.mike.projects.fitsynergy.users.repo;

import com.mike.projects.fitsynergy.users.model.Client;
import com.mike.projects.fitsynergy.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client, Integer> {

    Optional<Client> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "insert into client(client_id) values (?1)", nativeQuery = true)
    void addClient(Integer id);

    @Transactional
    @Modifying
    @Query(value = "delete from client where client_id=?1", nativeQuery = true)
    void deleteClient(Integer id);
}
