package com.elainehello.designpatterns.service;

import com.elainehello.designpatterns.exception.TaskNotFoundException;
import com.elainehello.designpatterns.model.Task;
import com.elainehello.designpatterns.model.TaskStatus;
import com.elainehello.designpatterns.repository.ITaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final ITaskRepository taskRepository;

    public TaskService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String title, String description) {
        Task task = new Task(null, title, description);

        return taskRepository.save(task);
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task updateStatus(Long id, TaskStatus status) {
        Task task = getTask(id);

        task.updateStatus(status);
        return taskRepository.save(task);
    }
}
