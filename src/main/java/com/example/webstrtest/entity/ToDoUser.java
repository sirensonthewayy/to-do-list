package com.example.webstrtest.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ToDoUser {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String username;
    private String password;
    @OneToMany
    @JoinColumn(name = "to_do_user_id")
    private List<ToDoList> toDoLists;
}

