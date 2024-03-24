package com.mike.projects.fitsynergy.users.repo;

import com.mike.projects.fitsynergy.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    @Query("select email from User where email=?1")
    Optional<String> findIfEmailPresent(String email);

    @Transactional
    @Modifying
    @Query("update system_user_details set role='TRAINER' where id=?1")
    void addTrainerRole(Integer userId);

    @Transactional
    @Modifying
    @Query("update system_user_details set role='CLIENT' where id=?1")
    void removeTrainerRole(Integer userId);
}
