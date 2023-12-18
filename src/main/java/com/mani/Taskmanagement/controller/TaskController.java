package com.mani.Taskmanagement.controller;

import com.mani.Taskmanagement.model.Task;
import com.mani.Taskmanagement.service.TaskService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@CookieValue(value = "username", defaultValue = "") String currentUser) {
        if (currentUser.isEmpty()) {
            // Handle the case where the username cookie is not present or empty
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Use the username in your business logic (e.g., retrieve tasks associated with this username)
        List<Task> tasks = taskService.getTasksForUser(currentUser);

        // Return the tasks as a response
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable String taskId) {
        Task task = taskService.getTaskById(taskId);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, @CookieValue(value = "username", defaultValue = "") String currentUser, HttpServletResponse response) {
        if (currentUser.isEmpty()) {
            // Handle the case where the username cookie is not present or empty
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Set the createdBy field of the task with the username obtained from the cookie
        task.setCreatedBy(currentUser);

        // Use the taskService to create the task
        Task createdTask = taskService.createTask(task);

        // Return the created task as a response
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable String taskId, @RequestBody Task updatedTask) {
        Task task = taskService.updateTask(taskId, updatedTask);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable String taskId) {
        if (taskService.deleteTask(taskId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
