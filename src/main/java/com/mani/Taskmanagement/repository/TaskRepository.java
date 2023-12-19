package com.mani.Taskmanagement.repository;

import com.mani.Taskmanagement.model.Task;
import com.mani.Taskmanagement.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.net.ContentHandler;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String> {
    // You can add custom query methods here if needed
    boolean existsByTitle(String title);
    void deleteByTitle(String title);

    Page<Task> findByStatus(TaskStatus status, PageRequest pageRequest);

    Optional<Task> findByTitle(String title);

    Page<Task> findByCreatedByAndStatus(String currentUser, TaskStatus status, Pageable pageable);

    Page<Task> findByCreatedBy(String currentUser, Pageable pageable);
}
