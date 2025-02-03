package com.example.webstrtest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
    @OneToMany
    @JoinColumn(name = "to_do_list_id")
    private List<Task> tasks;

}
