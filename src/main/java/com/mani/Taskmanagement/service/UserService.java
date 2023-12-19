package com.mani.Taskmanagement.service;

import com.mani.Taskmanagement.model.User;
import com.mani.Taskmanagement.model.UserType;
import com.mani.Taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {


    private final Map<String, User> users = new HashMap<>();
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        // Assuming you might want to check if the username is already taken
        if (!users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
            userRepository.save(user);
            return user;
        } else {
            // You might want to throw an exception or handle the case where the username is already taken
            return null;
        }
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        String role = "ROLE_USER";
        if (UserType.ADMIN.equals(user.getUsertype())) {
            role = "ROLE_ADMIN";
        }
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
}

    public UserType getUserType(String currentUser) {
        User user = userRepository.findByUsername(currentUser);
        return user.getUsertype();
    }
}