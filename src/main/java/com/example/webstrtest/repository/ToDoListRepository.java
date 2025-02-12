package com.example.webstrtest.repository;

import com.example.webstrtest.entity.ToDoList;
import com.example.webstrtest.entity.ToDoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {

    List<ToDoList> findAllByToDoUser(ToDoUser toDoUser);
    Optional<ToDoList> findToDoListById(Long id);
    boolean existsByName(String name);

}
