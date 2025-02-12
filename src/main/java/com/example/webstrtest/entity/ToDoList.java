package com.example.webstrtest.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Entity
@Data
public class ToDoList {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Date creationDate;
    @ManyToOne
    private ToDoUser toDoUser;
    @OneToMany
    @JoinColumn(name = "to_do_list_id")
    private List<Task> tasks;

}
