package com.mani.Taskmanagement.repository;

import com.mani.Taskmanagement.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    // You can add custom query methods here if needed
    List<Task> findByCreatedBy(String createdBy);
    boolean existsByTitle(String title);
    void deleteByTitle(String title);
}
