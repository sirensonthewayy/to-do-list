package com.example.webstrtest.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;
    private Date creationDate;
    private Date deadlineDate;
    private int priorityLevel;
    private String status;
    private Date remindingDate;
    private String fileLink;

}
