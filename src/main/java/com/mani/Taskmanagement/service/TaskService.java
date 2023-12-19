package com.mani.Taskmanagement.service;

import com.mani.Taskmanagement.model.Task;
import com.mani.Taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(String taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        return optionalTask.orElse(null);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(String taskId, Task updatedTask) {
        Optional<Task> optionalExistingTask = taskRepository.findById(taskId);
        if (optionalExistingTask.isPresent()) {
            Task existingTask = optionalExistingTask.get();
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setDueDate(updatedTask.getDueDate());
            existingTask.setStatus(updatedTask.getStatus());
            return taskRepository.save(existingTask);
        } else {
            return null; // Task not found
        }
    }

    public boolean deleteTask(String taskTitle) {
        if (taskRepository.existsByTitle(taskTitle)) {
            taskRepository.deleteByTitle(taskTitle);
            return true;
        } else {
            return false; // Task not found
        }
    }

    public List<Task> getTasksForUser(String currentUser) {
        return taskRepository.findByCreatedBy(currentUser);
    }
}
