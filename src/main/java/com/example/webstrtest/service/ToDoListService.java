package com.example.webstrtest.service;

import com.example.webstrtest.dto.ToDoListRequest;
import com.example.webstrtest.entity.ToDoList;
import com.example.webstrtest.repository.ToDoListRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class ToDoListService {

    @Autowired
    private ToDoListRepository toDoListRepository;

    public ToDoList getToDoListById(Long listId, String email){
        ToDoList toDoList = toDoListRepository.findToDoListById(listId).orElseThrow(() -> new ObjectNotFoundException(listId, "List with this id not found"));
        if(toDoList.getToDoUser().getEmail().equals(email)){
            return toDoList;
        } else{
            throw new AccessDeniedException("This list is not belong to user");
        }
    }

    public void deleteToDoListById(Long listId, String email){
        ToDoList toDoList = toDoListRepository.findToDoListById(listId).orElseThrow(() -> new ObjectNotFoundException(listId, "List with this id not found"));
        if(toDoList.getToDoUser().getEmail().equals(email)){
            toDoListRepository.deleteById(listId);
        } else{
            throw new AccessDeniedException("This list is not belong to user");
        }
    }

    public void updateToDoListById(Long listId, String email, ToDoListRequest toDoListRequest){
        ToDoList toDoList = toDoListRepository.findToDoListById(listId).orElseThrow(() -> new ObjectNotFoundException(listId, "List with this id not found"));
        if(toDoList.getToDoUser().getEmail().equals(email)){
            toDoList.setName(toDoListRequest.getName());
            toDoList.setDescription(toDoListRequest.getDescription());
            toDoListRepository.save(toDoList);
        } else{
            throw new AccessDeniedException("This list is not belong to user");
        }
    }
}
