package com.example.webstrtest.service;

import com.example.webstrtest.UserDetailsImpl;
import com.example.webstrtest.entity.ToDoUser;
import com.example.webstrtest.repository.ToDoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private ToDoUserRepository toDoUserRepository;

    @Autowired
    public void setUserRepository(ToDoUserRepository toDoUserRepository){
        this.toDoUserRepository = toDoUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ToDoUser toDoUser = toDoUserRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", username)
        ));
        return UserDetailsImpl.build(toDoUser);
    }
}
