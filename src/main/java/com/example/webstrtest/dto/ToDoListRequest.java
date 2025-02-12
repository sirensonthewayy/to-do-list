package com.example.webstrtest.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ToDoListRequest {

    private String name;
    private String description;

}
