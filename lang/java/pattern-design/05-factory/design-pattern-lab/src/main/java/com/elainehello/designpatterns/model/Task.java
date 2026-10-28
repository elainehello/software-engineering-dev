package com.elainehello.designpatterns.model;

import java.time.LocalDateTime;

public class Task {
    //attributes
    private final Long id;
    private final String title;
    private final String description;
    private TaskStatus status;
    private final LocalDateTime createdAt;

    //constructor
    public Task(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = TaskStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void updateStatus(TaskStatus status) {
        if  (status == null) {
            throw new IllegalArgumentException("Task status cannot be null");
        }
        this.status = status;
    }
}
