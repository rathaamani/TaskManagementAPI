package com.mani.Taskmanagement.controller;

import com.mani.Taskmanagement.VO.LoginRequestVO;
import com.mani.Taskmanagement.model.User;
import com.mani.Taskmanagement.repository.UserRepository;
import com.mani.Taskmanagement.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final HttpServletResponse response;

    public UserController(UserService userService, UserRepository userRepository, HttpServletResponse response) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.response = response;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users , HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestVO loginRequest) {
        // Perform authentication
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Simple authentication logic
        User user = userRepository.findByUsername(username);

        if(user == null){
            return new ResponseEntity<>("User Does not exist. Please check the username", HttpStatus.UNAUTHORIZED);
        }

        // Validate the user's credentials
        else{

            if (user != null && password.equals(user.getPassword())) {
                // Add username to a cookie
                Cookie cookie = new Cookie("username", username);
                cookie.setMaxAge(24 * 60 * 60); // Cookie valid for 1 day (in seconds)
                response.addCookie(cookie);

                return new ResponseEntity<>("Login successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
            }
        }
    }
}