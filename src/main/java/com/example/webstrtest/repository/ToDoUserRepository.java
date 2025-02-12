package com.example.webstrtest.repository;

import com.example.webstrtest.entity.ToDoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToDoUserRepository extends JpaRepository<ToDoUser, Long> {

    Optional<ToDoUser> findUserById(Long id);
    Optional<ToDoUser> findUserByUsername(String username);
    Optional<ToDoUser> findUserByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
