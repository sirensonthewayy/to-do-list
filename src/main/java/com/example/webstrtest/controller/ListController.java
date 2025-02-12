package com.example.webstrtest.controller;

import com.example.webstrtest.dto.ToDoListRequest;
import com.example.webstrtest.entity.ToDoList;
import com.example.webstrtest.entity.ToDoUser;
import com.example.webstrtest.repository.ToDoListRepository;
import com.example.webstrtest.repository.ToDoUserRepository;
import com.example.webstrtest.service.ToDoListService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.AccessDeniedException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/list")
public class ListController {

    @Autowired
    private ToDoListRepository toDoListRepository;
    @Autowired
    private ToDoUserRepository toDoUserRepository;
    @Autowired
    private ToDoListService toDoListService;

    @GetMapping("/all")
    ResponseEntity<List<ToDoList>> showAllToDoLists(){
        ToDoUser toDoUser = toDoUserRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        return ResponseEntity.ok(toDoListRepository.findAllByToDoUser(toDoUser));
    }

    @PostMapping("/new")
    ResponseEntity<?> newToDoList(@RequestBody ToDoListRequest toDoListRequest){
        if(toDoListRepository.existsByName(toDoListRequest.getName())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different name");
        }
        ToDoList toDoList = new ToDoList();
        toDoList.setName(toDoListRequest.getName());
        toDoList.setDescription(toDoListRequest.getDescription());
        toDoList.setCreationDate(Date.valueOf(LocalDate.now()));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        toDoList.setToDoUser(toDoUserRepository.findUserByEmail(email).get());
        toDoListRepository.save(toDoList);
        return ResponseEntity.ok("List created");
    }


    @GetMapping("/{listId}")
    ResponseEntity<?> showToDoList(@PathVariable Long listId){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            ToDoList toDoList = toDoListService.getToDoListById(listId, email);
            return ResponseEntity.ok(toDoList);
        } catch(AccessDeniedException accessDeniedException){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This list belongs to other user");
        } catch(ObjectNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List not found");
        }
    }

    @DeleteMapping("/delete/{listId}")
    ResponseEntity<?> deleteToDoList(@PathVariable Long listId){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            toDoListService.deleteToDoListById(listId, email);
            return ResponseEntity.ok("List was deleted");
        } catch(AccessDeniedException accessDeniedException){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This list belongs to other user");
        }
    }

    @PatchMapping("/update/{listId}")
    ResponseEntity<?> updateToDoList(@PathVariable Long listId, @RequestBody ToDoListRequest toDoListRequest){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            toDoListService.updateToDoListById(listId, email, toDoListRequest);
            return ResponseEntity.ok("List was updated");
        } catch(AccessDeniedException accessDeniedException){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This list belongs to other user");
        } catch(ObjectNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List not found");
        }
    }
}
