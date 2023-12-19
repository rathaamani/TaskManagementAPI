package com.mani.Taskmanagement.service;

import com.mani.Taskmanagement.VO.TaskRequest;
import com.mani.Taskmanagement.model.Task;
import com.mani.Taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<Task> getAllTasks(TaskRequest taskRequest) {
        Sort.Direction direction = Sort.Direction.fromString(taskRequest.getSortOrder());
        Sort sort = Sort.by(direction, taskRequest.getSortField());
        PageRequest pageRequest = PageRequest.of(taskRequest.getPage(), taskRequest.getSize(), sort);

        if (taskRequest.getStatus() != null) {
            // Fetch tasks with status filtering
            return taskRepository.findByStatus(taskRequest.getStatus(), pageRequest);
        } else {
            // Fetch all tasks
            return taskRepository.findAll(pageRequest);
        }
    }

    public Task getTaskById(String taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        return optionalTask.orElse(null);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(String title, Task updatedTask) {
        Optional<Task> optionalExistingTask = taskRepository.findByTitle(title);
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

    public Page<Task> getTasksForUser(String currentUser, TaskRequest taskRequest) {
        // Create a Pageable object for pagination and sorting
        Pageable pageable = PageRequest.of(
                taskRequest.getPage(),
                taskRequest.getSize(),
                Sort.by(Sort.Direction.fromString(taskRequest.getSortOrder()), taskRequest.getSortField())
        );

        // Perform filtering based on the status (if provided)
        if (taskRequest.getStatus() != null) {
            return taskRepository.findByCreatedByAndStatus(currentUser, taskRequest.getStatus(), pageable);
        } else {
            // Fetch tasks for a user with pagination and sorting
            return taskRepository.findByCreatedBy(currentUser, pageable);
        }
    }
}
