package com.mike.projects.fitsynergy.users.repo;

import com.mike.projects.fitsynergy.users.model.Trainer;
import com.mike.projects.fitsynergy.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TrainerRepo extends JpaRepository<Trainer, Integer> {
    Optional<Trainer> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "insert into trainer(trainer_id) values (?1)", nativeQuery = true)
    void addTrainer(Integer id);

    @Transactional
    @Modifying
    @Query(value = "delete from trainer where trainer_id=?1", nativeQuery = true)
    void deleteTrainer(Integer id);
}
