package com.mani.Taskmanagement.service;

import com.mani.Taskmanagement.model.User;
import com.mani.Taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {


    private final Map<String, User> users = new HashMap<>();
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        // Assuming you might want to check if the username is already taken
        if (userRepository.findByUsername(user.getUsername()) == null) {
            // The username is not taken, save the user to the database
            return userRepository.save(user);
        } else {
            // Username is already taken, you might want to throw an exception or handle the case
            return null;
        }
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}